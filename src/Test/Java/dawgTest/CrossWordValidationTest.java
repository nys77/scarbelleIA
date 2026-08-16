package dawgTest;

import Model.AI.ScrabbleAI;
import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Dictionary.DictionarySingleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CrossWordValidationTest {

    private Map map;
    private Dawg dawg;

    @BeforeEach
    public void setup() {
        map = new Map();
        dawg = DictionarySingleton.getInstance();
    }

    @Test
    public void testCrossWordAndExtensionValidation() {
        // Place AQUEUSE vertically at col 3 (row 6 to 12)
        String aqueuse = "AQUEUSE";
        for (int i = 0; i < aqueuse.length(); i++) {
            map.setLetter(6 + i, 3, aqueuse.charAt(i));
        }

        // Place HELLO horizontally at row 5 (col 0 to 4)
        // Col 3 of HELLO is 'L', placed directly above 'A' at (6, 3), creating LAQUEUSE vertically.
        List<Character> rack = Arrays.asList('H', 'E', 'L', 'L', 'O');
        boolean isValid = ScrabbleAI.isValidPlacement(map.get_matrix(), 5, 0, true, "HELLO", rack, dawg);

        // LAQUEUSE is a valid French word, so HELLO should be valid.
        assertTrue(isValid, "HELLO formant LAQUEUSE perpendiculairement doit être valide dans le DAWG");
    }

    @Test
    public void testInvalidCrossWordRejected() {
        // Place AQUEUSE vertically at col 3 (row 6 to 12)
        String aqueuse = "AQUEUSE";
        for (int i = 0; i < aqueuse.length(); i++) {
            map.setLetter(6 + i, 3, aqueuse.charAt(i));
        }

        // Try placing XYZ horizontally at row 5 (col 1 to 3) where Z is above A -> ZAQUEUSE (invalid word)
        List<Character> rack = Arrays.asList('X', 'Y', 'Z');
        boolean isValid = ScrabbleAI.isValidPlacement(map.get_matrix(), 5, 1, true, "XYZ", rack, dawg);

        assertFalse(isValid, "XYZ formant ZAQUEUSE (mot invalide) doit être strictement rejeté");
    }
}
