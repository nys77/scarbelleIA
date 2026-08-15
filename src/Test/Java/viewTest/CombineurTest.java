package viewTest;

import Model.Dictionary.DictionarySingleton;
import View.Combineur;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CombineurTest {

    @Test
    public void testLetterCosts() {
        assertEquals(1, Combineur.convert_cost('A'));
        assertEquals(1, Combineur.convert_cost('E'));
        assertEquals(2, Combineur.convert_cost('D'));
        assertEquals(3, Combineur.convert_cost('B'));
        assertEquals(4, Combineur.convert_cost('F'));
        assertEquals(8, Combineur.convert_cost('J'));
        assertEquals(10, Combineur.convert_cost('Z'));
        assertEquals(10, Combineur.convert_cost('K'));
        assertEquals(0, Combineur.convert_cost('!'));
    }

    @Test
    public void testCalculateWordBaseScore() {
        int score = Combineur.valuer_of_string("SCRABBLE");
        assertEquals(14, score);
    }

    @Test
    public void testRackWithDuplicateLetters() {
        ArrayList<String> validWords = Combineur.combinaisons_valides("smiuene", DictionarySingleton.getInstance());
        assertFalse(validWords.isEmpty(), "Des mots valides doivent être trouvés pour le chevalet [s, m, i, u, e, n, e]");
        assertTrue(validWords.contains("menus") || validWords.contains("mines") || validWords.contains("unies"));
    }
}
