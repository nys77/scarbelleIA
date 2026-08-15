package modelTest;

import Model.Player.AIPlayer;
import Model.Player.HumanPlayer;
import Model.Player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testHumanPlayerCreationAndScore() {
        Player player = new HumanPlayer(null, 1, null, "Alice");
        assertEquals("Alice", player.getName());
        assertEquals(1, player.getId());
        assertEquals(0, player.getPoint());
        assertTrue(player.canPlay());

        player.addPoints(25);
        assertEquals(25, player.getPoint());

        player.deductPoints(10);
        assertEquals(15, player.getPoint());

        player.deductPoints(50);
        assertEquals(0, player.getPoint());
    }

    @Test
    public void testAIPlayerCreationAndScore() {
        Player aiPlayer = new AIPlayer(null, 2, null, "IA Scarbelle", null);
        assertEquals("IA Scarbelle", aiPlayer.getName());
        assertEquals(2, aiPlayer.getId());
        assertFalse(aiPlayer.canPlay());

        aiPlayer.addPoints(50);
        assertEquals(50, aiPlayer.getPoint());
    }
}
