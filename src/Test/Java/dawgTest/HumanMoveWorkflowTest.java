package dawgTest;

import Model.AI.ScrabbleAI;
import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Dictionary.DictionarySingleton;
import Model.Model;
import Model.Move;
import Model.Player.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HumanMoveWorkflowTest {

    private Model model;
    private Dawg dawg;
    private Map map;
    private HumanPlayer human;

    @BeforeEach
    public void setup() {
        model = new Model();
        map = new Map();
        dawg = DictionarySingleton.getInstance();
        JFrame dummyFrame = new JFrame();
        human = new HumanPlayer(dummyFrame, 1, model, "Joueur Humain");
        human.getMain().charac_main.clear();
    }

    @Test
    public void testHumanValidPlacementAndMoveExecution() {
        // 1. Give human tiles: M, O, T, S, X, Y, Z
        human.getMain().charac_main.addAll(Arrays.asList('M', 'O', 'T', 'S', 'X', 'Y', 'Z'));

        // 2. Validate placement covering center (7, 7) horizontally for word "MOTS"
        boolean isValid = ScrabbleAI.isValidPlacement(map.get_matrix(), 7, 7, true, "MOTS", human.getMain().charac_main, dawg);
        assertTrue(isValid, "Le placement de MOTS au centre doit être valide");

        // 3. Execute move
        Move move = new Move(7, 7, true, "MOTS", 12);
        List<String> used = human.executeMove(move, map);

        // 4. Assertions
        assertEquals(4, used.size(), "4 lettres consommées");
        assertEquals(7, human.getMain().charac_main.size(), "Chevalet réapprovisionné à 7 lettres");
        assertEquals(12, human.getPoint(), "Score du joueur humain mis à jour à 12 points");
        assertEquals('M', map.getLetter(7, 7), "La lettre M est posée à (7, 7)");
        assertEquals('S', map.getLetter(7, 10), "La lettre S est posée à (7, 10)");
    }
}
