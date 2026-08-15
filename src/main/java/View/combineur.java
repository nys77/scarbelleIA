package View;

import Model.Dictionary.Dawg;
import Config.AssetsConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Combineur {

    public static ArrayList<String> parse(ArrayList<String> inputLetters) {
        if (inputLetters.size() == 1) {
            return inputLetters;
        }

        ArrayList<String> childPermutations;
        ArrayList<String> remainingLetters = new ArrayList<>();
        ArrayList<String> permutationResults = new ArrayList<>();
        LinkedList<String> processedRoots = new LinkedList<>();

        for (int index = 0; index < inputLetters.size(); index++) {
            String currentRootLetter = inputLetters.get(index);
            if (!processedRoots.contains(currentRootLetter)) {
                processedRoots.add(currentRootLetter);
                remainingLetters.clear();
                remainingLetters.addAll(inputLetters);
                try {
                    remainingLetters.remove(index);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage() + inputLetters.size());
                }

                int previousResultSize = permutationResults.size();
                childPermutations = parse(remainingLetters);
                permutationResults.addAll(childPermutations);

                for (int subIndex = previousResultSize; subIndex < permutationResults.size(); subIndex++) {
                    permutationResults.set(subIndex, currentRootLetter + permutationResults.get(subIndex));
                }
            }
        }
        return permutationResults;
    }

    public static ArrayList<String> to_create_combin(ArrayList<String> availableLetters) {
        ArrayList<String> combinationList = new ArrayList<>();
        int letterCount = availableLetters.size();
        if (letterCount == 0) {
            return combinationList;
        }

        int totalSubsets = 1 << letterCount;
        for (int mask = 1; mask < totalSubsets; mask++) {
            StringBuilder currentSubsetBuilder = new StringBuilder();
            for (int index = 0; index < letterCount; index++) {
                if ((mask & (1 << index)) != 0) {
                    currentSubsetBuilder.append(availableLetters.get(index));
                }
            }
            String subsetString = currentSubsetBuilder.toString();
            if (!combinationList.contains(subsetString)) {
                combinationList.add(subsetString);
            }
        }
        return combinationList;
    }

    public static String best_solution(ArrayList<String> rackLetters, Dawg dawgGraph) {
        int maximumScore = 0;
        ArrayList<String> rawCombinations = to_create_combin(rackLetters);
        ArrayList<String> formattedSolutions = new ArrayList<>();

        for (String combination : rawCombinations) {
            ArrayList<String> characterList = test(combination);
            ArrayList<String> wordPermutations = parse(characterList);
            ArrayList<String> validCandidateWords = new ArrayList<>();

            for (String wordCandidate : wordPermutations) {
                int wordScore = valuer_of_string(wordCandidate);
                if (wordScore <= maximumScore) {
                    continue;
                }
                if (dawgGraph.word_existe(dawgGraph, wordCandidate.toUpperCase(), 0)) {
                    validCandidateWords.add(wordCandidate);
                    maximumScore = wordScore;
                }
            }
            if (!validCandidateWords.isEmpty() && !formattedSolutions.contains(validCandidateWords.toString())) {
                formattedSolutions.add(validCandidateWords.toString());
            }
        }
        if (formattedSolutions.isEmpty()) {
            return "";
        }
        int lastIndex = formattedSolutions.size() - 1;
        return enleve_crochet(formattedSolutions.get(lastIndex));
    }

    public static String enleve_crochet(String formattedText) {
        if (formattedText.length() <= 2) return formattedText;
        return formattedText.substring(1, formattedText.length() - 1);
    }

    public static ArrayList<String> test(String inputString) {
        ArrayList<String> characterList = new ArrayList<>();
        for (int i = 0; i < inputString.length(); i++) {
            characterList.add(Character.toString(inputString.charAt(i)));
        }
        return characterList;
    }

    public static boolean existenceMot(String wordToValidate) {
        try (BufferedReader dictionaryReader = new BufferedReader(new InputStreamReader(
                Combineur.class.getResourceAsStream(AssetsConfig.DICO_PATH), StandardCharsets.UTF_8))) {
            return dictionaryReader.lines().anyMatch(line -> line.equalsIgnoreCase(wordToValidate));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static int valuer_of_string(String word) {
        int totalScore = 0;
        for (int index = 0; index < word.length(); index++) {
            totalScore += convert_cost(word.charAt(index));
        }
        return totalScore;
    }

    public static int convert_cost(char letter) {
        return switch (Character.toLowerCase(letter)) {
            case 'a', 'e', 'i', 'l', 'n', 'o', 'r', 's', 't', 'u' -> 1;
            case 'd', 'g', 'm' -> 2;
            case 'b', 'c', 'p' -> 3;
            case 'f', 'h', 'v' -> 4;
            case 'j', 'q' -> 8;
            case 'k', 'w', 'x', 'y', 'z' -> 10;
            default -> 0;
        };
    }

    public static ArrayList<String> combinaisons_valides(String rackString, Dawg dawgGraph) {
        ArrayList<String> rackLetters = test(rackString.toLowerCase());
        ArrayList<String> combinationList = to_create_combin(rackLetters);
        ArrayList<String> validWordsResult = new ArrayList<>();

        for (String combination : combinationList) {
            ArrayList<String> wordPermutations = parse(test(combination));
            for (String permutation : wordPermutations) {
                if (dawgGraph != null && dawgGraph.word_existe(dawgGraph, permutation.toUpperCase(), 0)) {
                    if (!validWordsResult.contains(permutation)) {
                        validWordsResult.add(permutation);
                    }
                }
            }
        }
        return validWordsResult;
    }
}
