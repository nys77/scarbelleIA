package Model.Player;

import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Model;
import Model.Move;
import View.Main;

public abstract class Player {

    protected Main main_;
    protected int point_;
    protected boolean can_play;
    protected int id_;
    protected String name_;

    public Player(Object frame, int i, Model model, String name) {
        if (frame instanceof javax.swing.JFrame jframe) {
            main_ = new Main(jframe, i, model);
        }
        point_ = 0;
        can_play = (i == 1);
        id_ = i;
        name_ = name;
    }

    public Player(Object frame, int i, Model model) {
        this(frame, i, model, "Joueur " + i);
    }

    public Main getMain() { return main_; }
    public int getPoint() { return point_; }
    public void addPoints(int pts) { this.point_ += pts; }
    public void deductPoints(int pts) { this.point_ = Math.max(0, this.point_ - pts); }
    public boolean canPlay() { return can_play; }
    public void setCanPlay(boolean canPlay) { this.can_play = canPlay; }
    public String getName() { return name_; }
    public int getId() { return id_; }

    public java.util.List<String> executeMove(Move move, Map map) {
        if (move == null || move.word() == null || move.word().isEmpty() || main_ == null) {
            return java.util.Collections.emptyList();
        }

        java.util.ArrayList<String> lettersUsedFromRack = new java.util.ArrayList<>();
        for (int i = 0; i < move.word().length(); i++) {
            int r = move.isHorizontal() ? move.startX() : move.startX() + i;
            int c = move.isHorizontal() ? move.startY() + i : move.startY();
            if (map.getLetter(r, c) == '_') {
                lettersUsedFromRack.add(String.valueOf(move.word().charAt(i)));
            }
        }

        addPoints(move.score());
        main_.remove_caracter(lettersUsedFromRack);
        main_.tirage(lettersUsedFromRack.size());

        for (int i = 0; i < move.word().length(); i++) {
            int r = move.isHorizontal() ? move.startX() : move.startX() + i;
            int c = move.isHorizontal() ? move.startY() + i : move.startY();
            map.setLetter(r, c, move.word().charAt(i));
        }

        return lettersUsedFromRack;
    }

    public abstract Move playTurn(char[][] board, Dawg dawg);
}
