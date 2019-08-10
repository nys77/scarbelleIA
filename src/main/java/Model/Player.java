package Model;

import View.Main;

import javax.swing.*;

public class Player {

    Main main_;
    Integer point_;
    boolean can_play;
    public Player(JFrame frame, int i, model model)
    {
        main_ = new Main(frame, i, model);
        point_ = 0;
        if (i == 1)
            can_play = true;
        else
            can_play = false;
    }
    public Main getMain()
    {
        return main_;
    }
}
