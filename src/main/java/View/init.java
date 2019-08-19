package View;

import Model.GameManager;
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
    JPanel[][] grid_panel_;
    public init(model model)
    {
        map_ = new map();
        JFrame t = new JFrame();
        grid_panel_ = new JPanel[15][15];
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
                grid_panel_[i][j] = ptest;

            }
        }
        pan.setBackground(Color.YELLOW);
        player1 = new Player(t,1,model);
        player2 = new Player(t,2,model);
        player1.getMain().print_main();
        player2.getMain().print_main();
        //GameManager g = new GameManager(player1);
        t.add(pan,BorderLayout.CENTER);
        t.setVisible(true);
        t.pack();
        String best_option = combineur.best_solution(conv(player1.getMain().charac_main));
        System.out.println(best_option);
        to_conv_panel(7,7,best_option);
        player1.getMain().remove_caracter(convert_to_array(best_option));
        player1.getMain().tirage(best_option.length());
        player1.getMain().print_main();

    }

    public ArrayList<String> convert_to_array(String s)
    {
        ArrayList<String> a = new ArrayList<String>();
        for(int i = 0; i < s.length();i++)
        {
            a.add(Character.toString(s.charAt(i)));
        }
        return a;
    }

    public void to_conv_panel(int x, int y, String word)
    {
        for(int i = 0;i < word.length();i++)
        {
            char to_add = word.charAt(i);
            grid_panel_[x + i][y].removeAll();
            Main.setPanel(grid_panel_[x + i][y], to_add);
            grid_panel_[x + i][y].updateUI();
        }
    }

    public ArrayList<String> conv(ArrayList<Character> l)
    {
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < l.size();i++)
        {
            res.add(Character.toString(l.get(i)));
        }
        return res;
    }

}
