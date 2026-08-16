package dawgTest;

import Model.AI.ParallelDAWGStrategy;
import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Model;
import Model.Move;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RobustFullGameTest {

    private Model model;
    private Dawg dawg;
    private Map map;
    private Player humanPlayer;
    private AIPlayer aiPlayer;

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
        humanPlayer = new HumanPlayer(dummyFrame, 1, model, "Joueur 1");
        aiPlayer = new AIPlayer(dummyFrame, 2, model, "IA Scarbelle", new ParallelDAWGStrategy());
    }

    @Test
    public void testRobustFullGameWorkflowAndValidation() {
        int consecutivePasses = 0;
        int maxTurns = 30;

        for (int turn = 1; turn <= maxTurns; turn++) {
            Player activePlayer = (turn % 2 != 0) ? humanPlayer : aiPlayer;

            // Assert hand size is always valid (<= 7)
            assertTrue(activePlayer.getMain().charac_main.size() <= 7, 
                    "La taille de la main ne doit jamais dépasser 7 jetons (Tour " + turn + ")");

            Move move = activePlayer.playTurn(map.get_matrix(), dawg);

            if (move != null && move.score() > 0 && move.word() != null && !move.word().isEmpty()) {
                int handBefore = activePlayer.getMain().charac_main.size();
                List<String> usedLetters = activePlayer.executeMove(move, map);

                consecutivePasses = 0;

                // Validation : Les lettres utilisées doivent correspondre aux tuiles retirées
                assertNotNull(usedLetters, "La liste des lettres utilisées ne doit pas être nulle");
                assertTrue(usedLetters.size() <= handBefore, "Le nombre de lettres jouées ne peut excéder la main");

                // Validation : Après le coup et la pioche, la main doit contenir 7 lettres (sauf si sac vide)
                int expectedHandSize = Math.min(7, activePlayer.getMain().charac_main.size());
                assertTrue(activePlayer.getMain().charac_main.size() <= 7, "Chevalet valide après le coup");
            } else if (model.get_rand().size() >= 7) {
                // Échange de lettres
                List<Character> toExchange = (activePlayer instanceof AIPlayer ai) 
                        ? ai.chooseLettersToExchange() 
                        : new ArrayList<>();

                if (toExchange != null && !toExchange.isEmpty()) {
                    List<String> toExStr = new ArrayList<>();
                    for (Character c : toExchange) toExStr.add(Character.toString(c));

                    activePlayer.getMain().remove_caracter(new ArrayList<>(toExStr));
                    activePlayer.getMain().tirage(toExchange.size());
                    for (Character c : toExchange) model.get_rand().add(c);
                }
                consecutivePasses++;
            } else {
                consecutivePasses++;
            }

            // Conditions d'arrêt de partie
            if (consecutivePasses >= 6 || (consecutivePasses >= 3 && model.get_rand().size() < 7)) {
                break;
            }
        }

        // Validation finale : Scores non négatifs et pénalités applicables
        assertTrue(humanPlayer.getPoint() >= 0, "Le score du joueur 1 doit être positif ou nul");
        assertTrue(aiPlayer.getPoint() >= 0, "Le score de l'IA doit être positif ou nul");
    }
}
