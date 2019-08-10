package Model;

import java.util.TreeMap;

public class model {
    TreeMap<Character, Integer> map;

    public model()
    {
        map = new TreeMap <Character, Integer> ();
        init_map();

    }

    public TreeMap<Character, Integer> getMap() {
        return map;
    }

    public void init_map()
    {
        map.put('a',9);
        map.put('b',6);
        map.put('c',2);
        map.put('d',3);
        map.put('e',15);
        map.put('f',2);
        map.put('g',2);
        map.put('h',2);
        map.put('i',8);
        map.put('j',1);
        map.put('k',1);
        map.put('l',5);
        map.put('m',5);
        map.put('n',6);
        map.put('o',6);
        map.put('p',2);
        map.put('q',1);
        map.put('r',6);
        map.put('s',6);
        map.put('t',6);
        map.put('u',6);
        map.put('v',2);
        map.put('w',1);
        map.put('y',1);
        map.put('z',1);
        map.put('!',2);
    }
}
