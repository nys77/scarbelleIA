package modelTest;

import Model.Move;
import Model.MoveBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveBuilderTest {

    @Test
    public void testMoveBuilderHorizontal() {
        Move move = new MoveBuilder()
                .atPosition(7, 7)
                .horizontally()
                .withWord("scrabble")
                .withScore(50)
                .build();

        assertEquals(7, move.startX());
        assertEquals(7, move.startY());
        assertTrue(move.isHorizontal());
        assertEquals("SCRABBLE", move.word());
        assertEquals(50, move.score());
    }

    @Test
    public void testMoveBuilderVertical() {
        Move move = new MoveBuilder()
                .atPosition(0, 3)
                .vertically()
                .withWord("joueur")
                .withScore(14)
                .build();

        assertEquals(0, move.startX());
        assertEquals(3, move.startY());
        assertFalse(move.isHorizontal());
        assertEquals("JOUEUR", move.word());
        assertEquals(14, move.score());
    }
}
