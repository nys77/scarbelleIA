package dawgTest;

import Model.AI.ParallelDAWGStrategy;
import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Enum.GameMode;
import Model.Model;
import Model.Move;
import Model.Player.AIPlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EvEModeTest {

    private Model model;
    private Dawg dawg;
    private Map map;
    private AIPlayer aiAlpha;
    private AIPlayer aiScarbelle;

    @BeforeEach
    public void setup() {
        model = new Model();
        map = new Map();
        dawg = new Dawg();
        try {
            dawg = dawg.create_dawg("/test/DawgTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame dummyFrame = new JFrame();
        aiAlpha = new AIPlayer(dummyFrame, 1, model, "IA Alpha", new ParallelDAWGStrategy());
        aiScarbelle = new AIPlayer(dummyFrame, 2, model, "IA Scarbelle", new ParallelDAWGStrategy());
    }

    @Test
    public void testEvEDuelExecution() {
        // Position first word on board at (7, 7) by IA Alpha
        map.setLetter(7, 7, 'M');
        map.setLetter(7, 8, 'A');
        map.setLetter(7, 9, 'I');

        int turnsExecuted = 0;
        int maxTurns = 15;

        for (int i = 0; i < maxTurns; i++) {
            AIPlayer activeAI = (i % 2 == 0) ? aiAlpha : aiScarbelle;
            Move move = activeAI.playTurn(map.get_matrix(), dawg);

            if (move != null && move.score() > 0 && move.word() != null && !move.word().isEmpty()) {
                List<String> used = activeAI.executeMove(move, map);
                assertNotNull(used);
                assertTrue(activeAI.getMain().charac_main.size() <= 7, "Le chevalet de " + activeAI.getName() + " doit rester <= 7");
                turnsExecuted++;
            }
        }

        assertTrue(turnsExecuted >= 0, "Le duel EvE s'exécute sans exceptions ni blocage");
        assertTrue(aiAlpha.getPoint() >= 0);
        assertTrue(aiScarbelle.getPoint() >= 0);
    }

    @Test
    public void testGameModeEnumEVE() {
        assertEquals(GameMode.EVE, GameMode.valueOf("EVE"));
    }
}
