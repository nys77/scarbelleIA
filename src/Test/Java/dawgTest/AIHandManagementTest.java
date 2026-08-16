package dawgTest;

import Model.AI.ParallelDAWGStrategy;
import Model.Board.Map;
import Model.Model;
import Model.Move;
import Model.Player.AIPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AIHandManagementTest {
    private Model model;
    private AIPlayer ai;
    private Map map;

    @BeforeEach
    public void setup() {
        model = new Model();
        map = new Map();
        JFrame dummyFrame = new JFrame();
        ai = new AIPlayer(dummyFrame, 2, model, "IA", new ParallelDAWGStrategy());
        ai.getMain().charac_main.clear();
    }

    @Test
    public void testAIExecuteMoveRemovesOnlyUsedTilesAndReplenishesHand() {
        // 1. Position 'A' on the map at (7, 7)
        map.setLetter(7, 7, 'A');

        // 2. Clear rack and give AI letters: M, I, S, O, N, X, Y (7 letters)
        ai.getMain().charac_main.addAll(Arrays.asList('M', 'I', 'S', 'O', 'N', 'X', 'Y'));
        assertEquals(7, ai.getMain().charac_main.size(), "Main initiale de 7 lettres");

        // 3. Move: MAISON horizontally starting at (7, 6).
        // (7, 6) -> M
        // (7, 7) -> A (already on board)
        // (7, 8) -> I
        // (7, 9) -> S
        // (7, 10)-> O
        // (7, 11)-> N
        Move aiMove = new Move(7, 6, true, "MAISON", 54);

        // 4. Execute move via Player domain method
        List<String> used = ai.executeMove(aiMove, map);

        // 5. Verify 5 letters were taken from rack (M, I, S, O, N)
        assertEquals(5, used.size(), "Doit avoir consommé 5 lettres du chevalet");
        assertFalse(used.contains("A"), "Ne doit pas consommer le A déjà présent sur le plateau");

        // 6. Verify total rack size is replenished back to 7 (or size of remaining tiles)
        assertEquals(7, ai.getMain().charac_main.size(), "Le chevalet doit comporter 7 lettres après tirage");

        // 7. Verify X and Y were retained from the original hand
        assertTrue(ai.getMain().charac_main.contains('X') || ai.getMain().charac_main.contains('x'), "Le X non utilisé doit rester dans le chevalet");
        assertTrue(ai.getMain().charac_main.contains('Y') || ai.getMain().charac_main.contains('y'), "Le Y non utilisé doit rester dans le chevalet");
    }

    @Test
    public void testAIExecuteMoveWithLowercaseRack() {
        // Test case reported by user: rack has lowercase ['b', 'n', 'o', 'r', 'r', 'l', 'q']
        ai.getMain().charac_main.addAll(Arrays.asList('b', 'n', 'o', 'r', 'r', 'l', 'q'));
        assertEquals(7, ai.getMain().charac_main.size());

        Move firstMove = new Move(7, 7, false, "BROL", 12);
        List<String> used = ai.executeMove(firstMove, map);

        assertEquals(4, used.size(), "4 lettres utilisées (b, r, o, l)");
        assertEquals(7, ai.getMain().charac_main.size(), "Le chevalet doit contenir 7 lettres après la pioche");
        assertTrue(ai.getMain().charac_main.contains('n'));
        assertTrue(ai.getMain().charac_main.contains('r'));
        assertTrue(ai.getMain().charac_main.contains('q'));
    }

    @Test
    public void testTirageWithOneOrZeroRemainingTiles() {
        // Test draw when bag has only 1 tile
        model.get_rand().clear();
        model.get_rand().add('Z');
        assertDoesNotThrow(() -> ai.getMain().tirage(4), "Ne doit pas lever d'exception avec 1 seule lettre dans le sac");
        assertEquals(1, ai.getMain().charac_main.size());

        // Test draw when bag is empty
        model.get_rand().clear();
        assertDoesNotThrow(() -> ai.getMain().tirage(3), "Ne doit pas lever d'exception quand le sac est vide");
    }
}
