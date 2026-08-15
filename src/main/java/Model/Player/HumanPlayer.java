package Model.Player;

import Model.Dictionary.Dawg;
import Model.Model;
import Model.Move;

public class HumanPlayer extends Player {

    public HumanPlayer(Object frame, int id, Model model, String name) {
        super(frame, id, model, name);
    }

    public HumanPlayer(Object frame, int id, Model model) {
        super(frame, id, model);
    }

    @Override
    public Move playTurn(char[][] board, Dawg dawg) {
        return null;
    }
}
