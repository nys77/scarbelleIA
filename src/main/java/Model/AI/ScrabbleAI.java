package Model.AI;

import Model.Dictionary.Dawg;
import Model.Move;
import Model.MoveBuilder;

import View.Combineur;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ScrabbleAI {

    public static record Anchor(int row, int col) {}

    /**
     * Trouve le meilleur coup pour l'IA sans jamais tricher (utilise uniquement sa main et le plateau actuel).
     */
    public static Move findBestMove(char[][] board, List<Character> rack, Dawg dawg) {
        List<Anchor> anchors = findAnchors(board);
        if (anchors.isEmpty()) {
            return null;
        }

        ConcurrentLinkedQueue<Move> validMoves = new ConcurrentLinkedQueue<>();

        // Traitement parallélisé des ancrages grâce aux Parallel Streams de Java 21
        anchors.parallelStream().forEach(anchor -> {
            searchMovesForAnchor(board, rack, dawg, anchor.row(), anchor.col(), true, validMoves);
            searchMovesForAnchor(board, rack, dawg, anchor.row(), anchor.col(), false, validMoves);
        });

        // Retourne le coup avec le score maximal (ou null si aucun mot trouvé)
        return validMoves.stream()
                .max(Comparator.comparingInt(Move::score))
                .orElse(null);
    }

    /**
     * Identifie les cases d'ancrage sur la grille 15x15.
     */
    public static List<Anchor> findAnchors(char[][] board) {
        List<Anchor> anchors = new ArrayList<>();
        boolean isEmptyBoard = true;

        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                if (board[r][c] != '\0' && board[r][c] != ' ' && board[r][c] != '_') {
                    isEmptyBoard = false;
                    break;
                }
            }
        }

        if (isEmptyBoard) {
            anchors.add(new Anchor(7, 7));
            return anchors;
        }

        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                if (isEmptyCell(board, c, r) && hasAdjacentLetter(board, c, r)) {
                    anchors.add(new Anchor(r, c));
                }
            }
        }
        return anchors;
    }

    private static boolean isEmptyCell(char[][] board, int col, int row) {
        return board[row][col] == '\0' || board[row][col] == ' ' || board[row][col] == '_';
    }

    private static boolean hasAdjacentLetter(char[][] board, int col, int row) {
        int[] dc = {-1, 1, 0, 0};
        int[] dr = {0, 0, -1, 1};

        for (int i = 0; i < 4; i++) {
            int nc = col + dc[i];
            int nr = row + dr[i];
            if (nc >= 0 && nc < 15 && nr >= 0 && nr < 15) {
                if (!isEmptyCell(board, nc, nr)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void searchMovesForAnchor(char[][] board, List<Character> rack, Dawg dawg,
                                             int anchorRow, int anchorCol, boolean isHorizontal,
                                             ConcurrentLinkedQueue<Move> validMoves) {
        List<Character> extendedRack = new ArrayList<>(rack);
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        for (int d = 0; d < 4; d++) {
            int nr = anchorRow + dr[d];
            int nc = anchorCol + dc[d];
            if (nr >= 0 && nr < 15 && nc >= 0 && nc < 15 && !isEmptyCell(board, nc, nr)) {
                extendedRack.add(Character.toUpperCase(board[nr][nc]));
            }
        }

        String rackStr = convertRackToString(extendedRack);
        List<String> validWords = Combineur.combinaisons_valides(rackStr, dawg);

        for (String word : validWords) {
            if (word == null || word.isEmpty()) continue;

            for (int offset = 0; offset < word.length(); offset++) {
                int startRow = isHorizontal ? anchorRow : anchorRow - offset;
                int startCol = isHorizontal ? anchorCol - offset : anchorCol;

                if (startRow < 0 || startCol < 0) continue;
                if (isHorizontal && startCol + word.length() > 15) continue;
                if (!isHorizontal && startRow + word.length() > 15) continue;

                if (isValidPlacement(board, startRow, startCol, isHorizontal, word, rack, dawg)) {
                    int score = calculateScore(board, startRow, startCol, isHorizontal, word, rack.size());
                    validMoves.add(new MoveBuilder()
                            .atPosition(startRow, startCol)
                            .direction(isHorizontal)
                            .withWord(word)
                            .withScore(score)
                            .build());
                }
            }
        }
    }

    public static boolean isValidPlacement(char[][] board, int startX, int startY, boolean isHorizontal, String word, List<Character> rack) {
        return isValidPlacement(board, startX, startY, isHorizontal, word, rack, null);
    }

    public static boolean isValidPlacement(char[][] board, int startX, int startY, boolean isHorizontal, String word, List<Character> rack, Dawg dawg) {
        int wordLen = word.length();
        if (isHorizontal) {
            if (startY + wordLen > 15) return false;
        } else {
            if (startX + wordLen > 15) return false;
        }

        boolean isBoardEmpty = isBoardEmpty(board);
        boolean overlapsExistingTile = false;
        boolean touchesAdjacentTile = false;
        int tilesNeededFromRack = 0;

        List<Character> tempRack = new ArrayList<>(rack);

        for (int i = 0; i < wordLen; i++) {
            int r = isHorizontal ? startX : startX + i;
            int c = isHorizontal ? startY + i : startY;

            char existing = board[r][c];
            char needed = word.charAt(i);

            if (existing != '_' && existing != ' ' && existing != '\0') {
                if (Character.toUpperCase(existing) != Character.toUpperCase(needed)) {
                    return false; // STRICTEMENT INTERDIT d'écraser une lettre différente déjà posée !
                }
                overlapsExistingTile = true;
            } else {
                boolean foundInRack = false;
                char neededUpper = Character.toUpperCase(needed);
                for (int k = 0; k < tempRack.size(); k++) {
                    char rackChar = Character.toUpperCase(tempRack.get(k));
                    if (rackChar == neededUpper || rackChar == '!') {
                        tempRack.remove(k);
                        foundInRack = true;
                        tilesNeededFromRack++;
                        break;
                    }
                }
                if (!foundInRack) {
                    return false;
                }

                if (hasAdjacentLetter(board, c, r)) {
                    touchesAdjacentTile = true;
                }
            }
        }

        if (isBoardEmpty) {
            boolean coversCenter = false;
            for (int i = 0; i < wordLen; i++) {
                int r = isHorizontal ? startX : startX + i;
                int c = isHorizontal ? startY + i : startY;
                if (r == 7 && c == 7) {
                    coversCenter = true;
                    break;
                }
            }
            if (!coversCenter) return false;
        } else {
            if (!overlapsExistingTile && !touchesAdjacentTile) {
                return false;
            }
        }

        if (tilesNeededFromRack == 0) return false;

        // Validation stricte des mots croisés perpendiculaires et extensions de mots existants
        return validateCrossWordsAndExtensions(board, startX, startY, isHorizontal, word, dawg);
    }

    private static boolean validateCrossWordsAndExtensions(char[][] board, int startX, int startY, boolean isHorizontal, String word, Dawg dawg) {
        if (dawg == null) return true;

        char[][] tempBoard = new char[15][15];
        for (int r = 0; r < 15; r++) {
            System.arraycopy(board[r], 0, tempBoard[r], 0, 15);
        }
        for (int i = 0; i < word.length(); i++) {
            int r = isHorizontal ? startX : startX + i;
            int c = isHorizontal ? startY + i : startY;
            tempBoard[r][c] = Character.toUpperCase(word.charAt(i));
        }

        // A. Vérifier la séquence complète du mot principal (incluant préfixes/suffixes touchés)
        String fullMainWord = getFullWordAt(tempBoard, startX, startY, isHorizontal);
        if (fullMainWord.length() > word.length()) {
            if (!dawg.word_existe(dawg, fullMainWord, 0)) {
                return false; // L'extension du mot principal est invalide (ex: EFAQ au lieu de FAQ)
            }
        }

        // B. Vérifier chaque mot croisé perpendiculaire formé par les nouvelles tuiles
        for (int i = 0; i < word.length(); i++) {
            int r = isHorizontal ? startX : startX + i;
            int c = isHorizontal ? startY + i : startY;

            if (isEmptyCell(board, c, r)) {
                String crossWord = getFullWordAt(tempBoard, r, c, !isHorizontal);
                if (crossWord.length() > 1) {
                    if (!dawg.word_existe(dawg, crossWord, 0)) {
                        return false; // Mot croisé invalide formé perpendiculairement !
                    }
                }
            }
        }

        return true;
    }

    private static String getFullWordAt(char[][] board, int r, int c, boolean isHorizontal) {
        if (isHorizontal) {
            int startC = c;
            while (startC > 0 && !isEmptyCell(board, startC - 1, r)) {
                startC--;
            }
            int endC = c;
            while (endC < 14 && !isEmptyCell(board, endC + 1, r)) {
                endC++;
            }
            StringBuilder sb = new StringBuilder();
            for (int col = startC; col <= endC; col++) {
                sb.append(board[r][col]);
            }
            return sb.toString();
        } else {
            int startR = r;
            while (startR > 0 && !isEmptyCell(board, c, startR - 1)) {
                startR--;
            }
            int endR = r;
            while (endR < 14 && !isEmptyCell(board, c, endR + 1)) {
                endR++;
            }
            StringBuilder sb = new StringBuilder();
            for (int row = startR; row <= endR; row++) {
                sb.append(board[row][c]);
            }
            return sb.toString();
        }
    }

    private static boolean isBoardEmpty(char[][] board) {
        for (int r = 0; r < 15; r++) {
            for (int c = 0; c < 15; c++) {
                if (board[r][c] != '_' && board[r][c] != ' ' && board[r][c] != '\0') {
                    return false;
                }
            }
        }
        return true;
    }

    public static int calculateScore(char[][] board, int startRow, int startCol, boolean isHorizontal, String word, int rackSize) {
        int baseScore = 0;
        int wordMultiplier = 1;
        int usedLettersFromRack = 0;

        for (int i = 0; i < word.length(); i++) {
            int r = isHorizontal ? startRow : startRow + i;
            int c = isHorizontal ? startCol + i : startCol;

            if (r < 0 || r >= 15 || c < 0 || c >= 15) return 0;

            char letter = Character.toUpperCase(word.charAt(i));
            int letterValue = getLetterValue(letter);

            // Vérifie si la case nécessite une lettre de la main
            if (isEmptyCell(board, c, r)) {
                usedLettersFromRack++;
                if (isLetterTriple(c, r)) letterValue *= 3;
                else if (isLetterDouble(c, r)) letterValue *= 2;

                if (isWordTriple(c, r)) wordMultiplier *= 3;
                else if (isWordDouble(c, r)) wordMultiplier *= 2;
            }

            baseScore += letterValue;
        }

        int totalScore = baseScore * wordMultiplier;

        // Bonus Scrabble : 50 points si les 7 lettres du chevalet ont été utilisées
        if (usedLettersFromRack == 7) {
            totalScore += 50;
        }

        return totalScore;
    }

    private static int getLetterValue(char c) {
        return switch (Character.toUpperCase(c)) {
            case 'A', 'E', 'I', 'L', 'N', 'O', 'R', 'S', 'T', 'U' -> 1;
            case 'D', 'G', 'M' -> 2;
            case 'B', 'C', 'P' -> 3;
            case 'F', 'H', 'V' -> 4;
            case 'J', 'Q' -> 8;
            case 'K', 'W', 'X', 'Y', 'Z' -> 10;
            default -> 0;
        };
    }

    private static boolean isWordTriple(int x, int y) {
        return (x == 0 || x == 7 || x == 14) && (y == 0 || y == 7 || y == 14) && !(x == 7 && y == 7);
    }

    private static boolean isWordDouble(int x, int y) {
        return (x == y || x + y == 14) && (x >= 1 && x <= 4 || x >= 10 && x <= 13 || x == 7);
    }

    private static boolean isLetterTriple(int x, int y) {
        return (x == 5 || x == 9) && (y == 1 || y == 5 || y == 9 || y == 13) ||
               (x == 1 || x == 13) && (y == 5 || y == 9);
    }

    private static boolean isLetterDouble(int x, int y) {
        return (x == 3 || x == 11) && (y == 0 || y == 7 || y == 14) ||
               (x == 0 || x == 7 || x == 14) && (y == 3 || y == 11);
    }

    private static String convertRackToString(List<Character> rack) {
        StringBuilder sb = new StringBuilder();
        for (Character c : rack) {
            if (c != null) sb.append(c);
        }
        return sb.toString();
    }

    public static List<Character> selectLettersToExchange(List<Character> rack) {
        if (rack == null || rack.isEmpty()) {
            return new ArrayList<>();
        }

        List<Character> vowels = new ArrayList<>();
        List<Character> consonants = new ArrayList<>();
        List<Character> jokers = new ArrayList<>();

        for (Character c : rack) {
            if (c == null) continue;
            char upper = Character.toUpperCase(c);
            if (upper == '!') {
                jokers.add(c);
            } else if (upper == 'A' || upper == 'E' || upper == 'I' || upper == 'O' || upper == 'U' || upper == 'Y') {
                vowels.add(c);
            } else {
                consonants.add(c);
            }
        }

        List<Character> toExchange = new ArrayList<>();
        int targetVowelCount = 3;
        int targetConsonantCount = 4;

        if (vowels.size() > targetVowelCount) {
            int numVowelsToExchange = vowels.size() - targetVowelCount;
            Map<Character, Integer> freqMap = new HashMap<>();
            for (Character v : vowels) {
                freqMap.put(Character.toUpperCase(v), freqMap.getOrDefault(Character.toUpperCase(v), 0) + 1);
            }
            vowels.sort((v1, v2) -> Integer.compare(freqMap.get(Character.toUpperCase(v2)), freqMap.get(Character.toUpperCase(v1))));
            for (int i = 0; i < Math.min(numVowelsToExchange, vowels.size()); i++) {
                toExchange.add(vowels.get(i));
            }
        } else if (consonants.size() > targetConsonantCount) {
            int numConsonantsToExchange = consonants.size() - targetConsonantCount;
            List<Character> hardConsonants = Arrays.asList('Q', 'W', 'X', 'Z', 'K', 'V');
            consonants.sort((c1, c2) -> {
                boolean isC1Hard = hardConsonants.contains(Character.toUpperCase(c1));
                boolean isC2Hard = hardConsonants.contains(Character.toUpperCase(c2));
                if (isC1Hard && !isC2Hard) return -1;
                if (!isC1Hard && isC2Hard) return 1;
                return 0;
            });
            for (int i = 0; i < Math.min(numConsonantsToExchange, consonants.size()); i++) {
                toExchange.add(consonants.get(i));
            }
        } else if (!consonants.isEmpty()) {
            toExchange.add(consonants.get(0));
        }

        return toExchange;
    }
}
