package View;

import Model.Model;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class Main {
    public ArrayList<JPanel> panel_main;
    public ArrayList<Character> charac_main;
    public Model model_;
    public JPanel player1;
    public boolean isplayer1;
    public JFrame fenetre;
    public Main(JFrame fenetre_, int i, Model model){
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
        if (i == 1) {
            fenetre_.add(player1, BorderLayout.SOUTH);
            isplayer1 = true;
        } else {
            fenetre_.add(player1, BorderLayout.NORTH);
            isplayer1 = false;
        }
        init_main();
    }


    public void remove_caracter(ArrayList<String> list)
    {
        for(int i = 0; i < list.size();i++)
        {
            boolean removed = false;
            for(int j = 0; j < charac_main.size();j++)
            {
                if(list.get(i).equalsIgnoreCase(Character.toString(charac_main.get(j))))
                {
                    charac_main.remove(j);
                    if (j < panel_main.size()) {
                        panel_main.get(j).removeAll();
                        panel_main.remove(j);
                    }
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                for (int j = 0; j < charac_main.size(); j++) {
                    if (charac_main.get(j) == '!') {
                        charac_main.remove(j);
                        if (j < panel_main.size()) {
                            panel_main.get(j).removeAll();
                            panel_main.remove(j);
                        }
                        break;
                    }
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
            Character update = Character.valueOf(to_add);

            while (!(can_take(update)))
            {
                rand = new Random();
                r = rand.nextInt(model_.get_rand().size() -1) ;
                to_add = model_.get_rand().get(r);
                update = Character.valueOf(to_add);
            }
            model_.get_rand().remove(r);
            update_model(update);
            charac_main.add(update);
        }
        player1.removeAll();
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
            if (player1.getParent() != null) {
                player1.getParent().revalidate();
                player1.getParent().repaint();
            } else {
                fenetre.add(player1, BorderLayout.NORTH);
            }
        }
        else
        {
            if (player1.getParent() != null) {
                player1.getParent().revalidate();
                player1.getParent().repaint();
            } else {
                fenetre.add(player1, BorderLayout.SOUTH);
            }
        }
        player1.updateUI();
    }

    public  void init_main()
    {
        for(int i = 0; i < panel_main.size(); i++)
        {
            Random rand = new Random();
            int r = rand.nextInt(model_.get_rand().size() -1) ;
            char to_add = model_.get_rand().get(r);
            Character update = Character.valueOf(to_add);

            while (!(can_take(update)))
            {
                rand = new Random();
                r = rand.nextInt(model_.get_rand().size() -1) ;
                to_add = model_.get_rand().get(r);
                update = Character.valueOf(to_add);
            }
            model_.get_rand().remove(r);
            update_model(update);
            charac_main.add(update);
            setPanel(panel_main.get(i),update);
        }
    }

    public static void setPanel(JPanel to_set, Character a) {
        if (a == null) return;
        to_set.removeAll();
        char letterChar = Character.toUpperCase(a);
        
        JPanel tile = new JPanel(new BorderLayout());
        tile.setBackground(new Color(254, 243, 199));
        tile.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(217, 119, 6), 2, true),
                BorderFactory.createEmptyBorder(1, 4, 1, 4)
        ));

        JLabel letterLabel = new JLabel(String.valueOf(letterChar), SwingConstants.CENTER);
        letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        letterLabel.setForeground(new Color(30, 41, 59));

        int val = View.Combineur.convert_cost(Character.toLowerCase(letterChar));
        JLabel valLabel = new JLabel(val > 0 ? String.valueOf(val) : "", SwingConstants.RIGHT);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
        valLabel.setForeground(new Color(120, 53, 15));

        tile.add(letterLabel, BorderLayout.CENTER);
        tile.add(valLabel, BorderLayout.SOUTH);

        to_set.add(tile, BorderLayout.CENTER);
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
