package dawgTest;

import Model.Dictionary.Dawg;
import Model.Move;
import Model.AI.ScrabbleAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScrabbleAITest {

    private char[][] emptyBoard;
    private Dawg dawg;

    @BeforeEach
    public void setUp() throws Exception {
        emptyBoard = new char[15][15];
        for (int i = 0; i < 15; i++) {
            Arrays.fill(emptyBoard[i], '_');
        }
        dawg = new Dawg();
        dawg = dawg.create_dawg("/test/DawgTest");
    }

    @Test
    public void testFindAnchorsEmptyBoard() {
        List<ScrabbleAI.Anchor> anchors = ScrabbleAI.findAnchors(emptyBoard);
        assertEquals(1, anchors.size(), "Sur un plateau vide, le seul ancrage doit être la case centrale (7,7)");
        assertEquals(7, anchors.get(0).row());
        assertEquals(7, anchors.get(0).col());
    }

    @Test
    public void testFindAnchorsNonEmptyBoard() {
        emptyBoard[7][7] = 'A';
        List<ScrabbleAI.Anchor> anchors = ScrabbleAI.findAnchors(emptyBoard);
        assertTrue(anchors.size() > 0, "Les cases adjacentes doivent être identifiées comme ancrages");
        assertTrue(anchors.stream().anyMatch(a -> a.row() == 6 && a.col() == 7), "La case du haut (row 6, col 7) doit être un ancrage");
        assertTrue(anchors.stream().anyMatch(a -> a.row() == 8 && a.col() == 7), "La case du bas (row 8, col 7) doit être un ancrage");
    }

    @Test
    public void testCalculateScoreWithBonus() {
        // Un mot de 6 lettres n'a pas le bonus de 50 points
        int score6Letters = ScrabbleAI.calculateScore(emptyBoard, 1, 1, true, "BANANE", 6);
        // Un mot de 7 lettres posé entièrement reçoit le bonus de 50 points
        int score7Letters = ScrabbleAI.calculateScore(emptyBoard, 1, 1, true, "BANANES", 7);
        
        assertTrue(score7Letters >= score6Letters + 50, "Poser 7 lettres doit attribuer le bonus de 50 points de Scrabble");
    }

    @Test
    public void testFindBestMoveParallel() {
        List<Character> rack = Arrays.asList('B', 'A', 'N', 'A', 'N', 'E');
        Move bestMove = ScrabbleAI.findBestMove(emptyBoard, rack, dawg);
        assertNotNull(bestMove, "L'IA doit trouver un coup valide avec ce chevalet et ce dictionnaire");
        assertTrue(bestMove.score() > 0, "Le score du coup doit être supérieur à zéro");
    }

    @Test
    public void testInvalidCrossWordRejected() {
        emptyBoard[6][6] = 'E';
        List<Character> rack = Arrays.asList('F', 'A', 'Q');
        boolean isValid = ScrabbleAI.isValidPlacement(emptyBoard, 7, 6, false, "FAQ", rack, dawg);
        assertFalse(isValid, "Un mot croisé ou une extension de mot invalide (ex: EFAQ) doit être impérativement rejeté.");
    }
}
