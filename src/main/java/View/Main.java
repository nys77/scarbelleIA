package View;

import Model.model;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Main {
    public ArrayList<JPanel> panel_main;
    public ArrayList<Character> charac_main;
    public model model_;
    public JPanel player1;
    public boolean isplayer1;
    public JFrame fenetre;
    public Main(JFrame fenetre_, int i, model model){
        panel_main = new ArrayList<JPanel>();
        fenetre = fenetre_;
        charac_main = new ArrayList<Character>();
        model_ = model;
        player1 = new JPanel(new GridLayout(1,7));
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        player1.setPreferredSize(new Dimension((int) fenetre_.getWidth() ,(int) (0.1 * fenetre_.getHeight())));
        for (int j = 0; j < 7; j++)
        {
            JPanel ptest = new JPanel(new BorderLayout());
            ptest.setBorder(blackline);
            player1.add(ptest);
            panel_main.add(ptest);
        }
        if (i == 1)
        {
            fenetre_.add(player1, BorderLayout.NORTH);
            isplayer1 = true;
        }
        else
        {
            fenetre_.add(player1,BorderLayout.SOUTH);
            isplayer1 = false;
        }
        init_main();
    }


    public void remove_caracter(ArrayList<String> list)
    {
        for(int i = 0; i < list.size();i++)
        {
            for(int j = 0; j < charac_main.size();j++)
            {
                if(list.get(i).equals(Character.toString(charac_main.get(j))))
                {
                    charac_main.remove(j);
                    panel_main.get(j).removeAll();
                    break;
                }
            }
        }
    }

    public  void tirage(int n)
    {
        for(int i = 0; i < n;i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(model_.get_rand().size() -1) ;
            char to_add = model_.get_rand().get(r);
            Character update = new Character(to_add);

            while (!(can_take(update)))
            {
                rand = new Random();
                r = rand.nextInt(model_.get_rand().size() -1) ;
                to_add = model_.get_rand().get(r);
                update = new Character(to_add);
            }
            model_.get_rand().remove(r);
            update_model(update);
            charac_main.add(update);
           // setPanel(panel_main.get(i),update);
        }
        player1.removeAll();
       // player1 = new JPanel(new GridLayout(1,7));
        player1.setPreferredSize(new Dimension((int) fenetre.getWidth() ,(int) (0.1 * fenetre.getHeight())));
        panel_main = new ArrayList<JPanel>();
        Border blackline = BorderFactory.createLineBorder(Color.black,1);
        for(int i = 0; i < charac_main.size();i++)
        {
            JPanel ptest = new JPanel(new BorderLayout());
            ptest.setBorder(blackline);
            player1.add(ptest);
            panel_main.add(ptest);
            setPanel(ptest,charac_main.get(i));
        }
        if (isplayer1)
        {
            fenetre.add(player1, BorderLayout.NORTH);

        }
        else
        {
            fenetre.add(player1,BorderLayout.SOUTH);
        }
        player1.updateUI();
       // fenetre.pack();
    }

    public  void init_main()
    {
        for(int i = 0; i < panel_main.size(); i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(model_.get_rand().size() -1) ;
            char to_add = model_.get_rand().get(r);
            Character update = new Character(to_add);

            while (!(can_take(update)))
            {
                rand = new Random();
                r = rand.nextInt(model_.get_rand().size() -1) ;
                to_add = model_.get_rand().get(r);
                update = new Character(to_add);
            }
            model_.get_rand().remove(r);
            update_model(update);
            charac_main.add(update);
            setPanel(panel_main.get(i),update);
        }
    }

    public static void setPanel(JPanel to_set,Character a) {
        JLabel label;
        JPanel tmp = new JPanel(new BorderLayout());
        if (a == 'a') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/A.png"));
        } else if (a == 'b') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/B.png"));

        } else if (a == 'c') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/C.png"));

        } else if (a == 'd') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/D.png"));

        } else if (a == 'e') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/E.png"));

        } else if (a == 'f') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/F.png"));

        } else if (a == 'g') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/G.png"));

        } else if (a == 'h') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/H.png"));

        } else if (a == 'i') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/I.png"));

        } else if (a == 'j') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/J.png"));

        } else if (a == 'k') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/K.png"));

        } else if (a == 'l') {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/L.png"));

        } else if (a == 'm')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/M.png"));
        }
        else if (a == 'n')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/N.png"));
        }
        else if (a == 'o')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/O.png"));
        }
        else if (a == 'p')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/P.png"));
        }
        else if (a == 'q')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/Q.png"));
        }
        else if (a == 'r')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/R.png"));
        }
        else if (a =='s')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/S.png"));
        }
        else if (a == 't')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/T.png"));
        }
        else if (a == 'u')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/U.png"));
        }
        else if (a == 'v')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/V.png"));
        }
        else if (a == 'w')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/W.png"));
        }
        else if (a == 'x')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/X.png"));
        }
        else if (a == 'y')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/Y.png"));
        }
        else if (a == '!')
        {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/!.png"));
        }
        else {
            label = new JLabel(new ImageIcon("tests/ressources/lettre/Z.png"));
        }
        tmp.add(label,BorderLayout.CENTER);
        to_set.add(tmp, BorderLayout.CENTER);
    }



    public boolean can_take(Character update)
    {
        for (Map.Entry<Character, Integer>  i : model_.getMap().entrySet() )
        {
            if (i.getKey().equals(update) )
            {
               return i.getValue() > 0;
            }
        }
        return false;
    }

    public void update_model(Character update)
    {
        for (Map.Entry<Character, Integer>  i : model_.getMap().entrySet() )
        {
            if (i.getKey().equals(update))
            {
                i.setValue(i.getValue() - 1);
                break;
            }
        }
    }

    public void print_main()
    {
        System.out.print("| ");
        for (int i = 0; i < charac_main.size();i++)
        {
            System.out.print(charac_main.get(i) + " | ");
        }
        System.out.println("");
    }
}
