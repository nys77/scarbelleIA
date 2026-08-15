package modelTest;

import Model.Model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ModelTest {

    @Test
    public void testTileBagInitialization() {
        Model model = new Model();
        ArrayList<Character> bag = model.get_rand();
        assertNotNull(bag);
        assertFalse(bag.isEmpty());

        long countE = bag.stream().filter(c -> c == 'e').count();
        long countA = bag.stream().filter(c -> c == 'a').count();
        long countJoker = bag.stream().filter(c -> c == '!').count();

        assertEquals(15, countE, "Nombre de 'e' incorrect");
        assertEquals(9, countA, "Nombre de 'a' incorrect");
        assertEquals(2, countJoker, "Nombre de jokers '!' incorrect");
    }
}
