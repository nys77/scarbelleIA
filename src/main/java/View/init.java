package View;

import Model.Letter;
import Model.Player;
import Model.model;


import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public class init {

    map map_;
    ArrayList<Letter> letters_player1;
    ArrayList<Letter> letters_player2;
    Player player1;
    Player player2;
    public init(model model)
    {
        map_ = new map();
        JFrame t = new JFrame();
        t.setSize(new Dimension(800,800));
        JPanel pan = new JPanel (new GridLayout (15,15));
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        for(int i = 0; i< 15;i++){
            for (int j = 0; j < 15 ; j++) {
                JLabel lab = new JLabel(new ImageIcon(map_.list_cell[i][j].url_));
                JPanel ptest = new JPanel();
                ptest.setLayout(new BorderLayout());
                ptest.setBackground(Color.YELLOW);
                ptest.add(lab, BorderLayout.CENTER);
                ptest.setBorder(blackline);
                pan.add(ptest);
            }
        }
        pan.setBackground(Color.YELLOW);
        player1 = new Player(t,1,model);
        player2 = new Player(t,2,model);
        player1.getMain().print_main();
        player2.getMain().print_main();
        t.add(pan,BorderLayout.CENTER);
        t.setVisible(true);
        t.pack();
    }

}
