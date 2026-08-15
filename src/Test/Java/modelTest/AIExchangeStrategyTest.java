package modelTest;

import Model.AI.ScrabbleAI;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AIExchangeStrategyTest {

    @Test
    public void testExcessVowelsExchange() {
        List<Character> rack = Arrays.asList('A', 'E', 'I', 'O', 'U', 'E', 'T');
        List<Character> toExchange = ScrabbleAI.selectLettersToExchange(rack);

        assertFalse(toExchange.isEmpty());
        assertEquals(3, toExchange.size());
        assertTrue(toExchange.stream().allMatch(c -> "AEIOUYaeiouy".indexOf(c) >= 0));
    }

    @Test
    public void testExcessHardConsonantsExchange() {
        List<Character> rack = Arrays.asList('Q', 'W', 'Z', 'K', 'P', 'T', 'E');
        List<Character> toExchange = ScrabbleAI.selectLettersToExchange(rack);

        assertFalse(toExchange.isEmpty());
        assertTrue(toExchange.contains('Q') || toExchange.contains('q'));
        assertTrue(toExchange.contains('W') || toExchange.contains('w'));
    }

    @Test
    public void testJokersArePreserved() {
        List<Character> rack = Arrays.asList('!', 'A', 'E', 'I', 'O', 'U', 'Y');
        List<Character> toExchange = ScrabbleAI.selectLettersToExchange(rack);

        assertFalse(toExchange.contains('!'), "Le Joker ! ne doit jamais être réinjecté dans le sac lors d'un échange");
    }
}
