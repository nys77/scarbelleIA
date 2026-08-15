package Model.AI;

import Model.Dictionary.Dawg;
import Model.Move;

import java.util.List;

public interface AIStrategy {
    Move computeBestMove(char[][] board, List<Character> rack, Dawg dawg);
    List<Character> selectLettersToExchange(List<Character> rack);
}
