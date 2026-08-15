package Model.Player;

import Model.AI.AIStrategy;
import Model.AI.ParallelDAWGStrategy;
import Model.Dictionary.Dawg;
import Model.Model;
import Model.Move;

import java.util.Collections;
import java.util.List;

public class AIPlayer extends Player {

    private final AIStrategy strategy_;

    public AIPlayer(Object frame, int id, Model model, String name, AIStrategy strategy) {
        super(frame, id, model, name);
        this.strategy_ = strategy != null ? strategy : new ParallelDAWGStrategy();
    }

    public AIPlayer(Object frame, int id, Model model) {
        this(frame, id, model, "IA Scarbelle", new ParallelDAWGStrategy());
    }

    @Override
    public Move playTurn(char[][] board, Dawg dawg) {
        if (getMain() == null) return null;
        List<Character> rack = getMain().charac_main;
        return strategy_.computeBestMove(board, rack, dawg);
    }

    public List<Character> chooseLettersToExchange() {
        if (getMain() == null || getMain().charac_main == null) {
            return Collections.emptyList();
        }
        return strategy_.selectLettersToExchange(getMain().charac_main);
    }
}
