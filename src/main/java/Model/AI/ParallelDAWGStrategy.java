package Model.AI;

import Model.Dictionary.Dawg;
import Model.Move;

import java.util.List;

public class ParallelDAWGStrategy implements AIStrategy {

    @Override
    public Move computeBestMove(char[][] board, List<Character> rack, Dawg dawg) {
        return ScrabbleAI.findBestMove(board, rack, dawg);
    }

    @Override
    public List<Character> selectLettersToExchange(List<Character> rack) {
        return ScrabbleAI.selectLettersToExchange(rack);
    }
}
