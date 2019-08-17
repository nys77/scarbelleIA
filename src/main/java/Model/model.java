package Model;

import java.io.CharArrayReader;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class model {
    TreeMap<Character, Integer> map;
    ArrayList<Character> for_rand;
    public model()
    {
        map = new TreeMap <Character, Integer> ();
        for_rand = new ArrayList<Character>();
        init_map();

    }

    public TreeMap<Character, Integer> getMap() {
        return map;
    }

    public void init_map()
    {
        map.put('a',9);
        set_rand(9,'a');
        map.put('b',6);
        set_rand(6,'b');
        map.put('c',2);
        set_rand(2,'c');
        map.put('d',3);
        set_rand(3,'d');
        map.put('e',15);
        set_rand(15,'e');
        map.put('f',2);
        set_rand(2,'f');
        map.put('g',2);
        set_rand(2,'g');
        map.put('h',2);
        set_rand(2,'h');
        map.put('i',8);
        set_rand(8,'i');
        map.put('j',1);
        set_rand(1,'j');
        map.put('k',1);
        set_rand(1,'k');
        map.put('l',5);
        set_rand(5,'l');
        map.put('m',5);
        set_rand(5,'m');
        map.put('n',6);
        set_rand(6,'n');
        map.put('o',6);
        set_rand(6,'o');
        map.put('p',2);
        set_rand(2,'p');
        map.put('q',1);
        set_rand(1,'q');
        map.put('r',6);
        set_rand(6,'r');
        map.put('s',6);
        set_rand(6,'s');
        map.put('t',6);
        set_rand(6,'t');
        map.put('u',6);
        set_rand(6,'u');
        map.put('v',2);
        set_rand(2,'v');
        map.put('w',1);
        set_rand(1,'w');
        map.put('y',1);
        set_rand(1,'y');
        map.put('z',1);
        set_rand(1,'z');
        map.put('!',2);
        set_rand(2,'!');
    }

    public void set_rand(int nb, Character to_add)
    {
        for (int i = 0; i < nb; i++)
        {
            for_rand.add(to_add);
        }
    }

    public void print_stock() {

        for (Map.Entry<Character, Integer>  i : map.entrySet() )
        {
           System.out.println("| NB = " + i.getValue() + " of " + i.getKey() + " |");
        }
    }

    public void print_rand()
    {
        Character tmp = '&';
        int cmp = 1;
        for(int i = 0; i < for_rand.size();i++)
        {
            if (tmp.equals(for_rand.get(i)))
            {
                cmp++;
            }
            else
            {
                if (i == 0)
                {
                    tmp = for_rand.get(i);
                }
                else {
                    System.out.println("| NB = " + cmp + " of " + tmp + " |");
                    tmp = for_rand.get(i);
                }
            }
        }
    }

    public ArrayList<Character> get_rand()
    {
        return for_rand;
    }

}
