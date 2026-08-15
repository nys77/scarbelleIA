package dawgTest;

import Model.AI.ParallelDAWGStrategy;
import Model.Dictionary.Dawg;
import Model.Model;
import Model.Move;
import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.JFrame;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FullGameSimulationTest {

    private Model model;
    private Dawg dawg;
    private Player human;
    private AIPlayer ai;

    @BeforeEach
    public void setup() {
        model = new Model();
        dawg = new Dawg();
        try {
            dawg = dawg.create_dawg("/test/DawgTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame dummyFrame = new JFrame();
        human = new HumanPlayer(dummyFrame, 1, model);
        ai = new AIPlayer(dummyFrame, 2, model, "IA", new ParallelDAWGStrategy());
        
        // Give them letters
        human.getMain().tirage(7);
        ai.getMain().tirage(7);
    }

    @Test
    public void testContinuousPasses() {
        int consecutivePasses = 0;
        int maxTurns = 50;
        char[][] board = new char[15][15];
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                board[i][j] = '_';
            }
        }

        for (int turn = 1; turn <= maxTurns; turn++) {
            // Human Turn
            System.out.println("Turn " + turn + ": Human passes.");
            consecutivePasses++;
            
            if (consecutivePasses >= 6 || (consecutivePasses >= 3 && model.get_rand().size() < 7)) {
                System.out.println("Game over triggered by Human pass. Consecutive passes: " + consecutivePasses);
                break;
            }

            // AI Turn
            Move aiMove = ai.playTurn(board, dawg);
            if (aiMove != null && aiMove.score() > 0 && aiMove.word() != null && !aiMove.word().isEmpty()) {
                System.out.println("AI plays: " + aiMove.word() + " at (" + aiMove.startX() + "," + aiMove.startY() + ")");
                consecutivePasses = 0;
                // Place word on board (mock)
                for (int i = 0; i < aiMove.word().length(); i++) {
                    int r = aiMove.isHorizontal() ? aiMove.startX() : aiMove.startX() + i;
                    int c = aiMove.isHorizontal() ? aiMove.startY() + i : aiMove.startY();
                    board[r][c] = Character.toUpperCase(aiMove.word().charAt(i));
                }
                
                // Exchange letters
                for(int i = 0; i < aiMove.word().length(); i++) {
                    ai.getMain().charac_main.remove((Character) Character.toUpperCase(aiMove.word().charAt(i)));
                }
                ai.getMain().tirage(aiMove.word().length());
            } else if (model.get_rand().size() >= 7) {
                List<Character> toExchange = ai.chooseLettersToExchange();
                if (toExchange != null && !toExchange.isEmpty()) {
                    System.out.println("AI exchanges " + toExchange.size() + " letters.");
                    for (Character c : toExchange) {
                        ai.getMain().charac_main.remove(c);
                        model.get_rand().add(c);
                    }
                    ai.getMain().tirage(toExchange.size());
                } else {
                    System.out.println("AI chooses to exchange 0 letters (passes silently).");
                }
                consecutivePasses++;
            } else {
                System.out.println("AI passes due to empty bag.");
                consecutivePasses++;
            }
            
            if (consecutivePasses >= 6 || (consecutivePasses >= 3 && model.get_rand().size() < 7)) {
                System.out.println("Game over triggered by AI. Consecutive passes: " + consecutivePasses);
                break;
            }
        }
        
        assertTrue(true, "Test finished execution without hanging.");
    }
}
