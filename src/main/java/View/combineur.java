package View;

import Model.Dawg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;

public class combineur {
    public static ArrayList<String> parse(ArrayList<String> t) {
        //Instanciation des variables
        String racine;
        ArrayList<String> ch = new ArrayList<String>();
        ArrayList<String> h = new ArrayList<String>();
        ArrayList<String> result = new ArrayList<String>();
        LinkedList<String> ret = new LinkedList<String>();

        int i, a;
        //Dans le cas ou le mot est composé d'un seul caractère
        if (t.size() == 1) {
            return t;
        } else {
            //On boucle sur toute les lettres
            for (i = 0; i < t.size(); i++) {
                //on récupére la lettre courante
                racine = t.get(i);
                if (ret.indexOf(racine) >= 0) {
                } else {
                    ret.add(racine);
                    h.clear();
                    h.addAll(t);
                    try {
                        //on supprime les doublons
                        h.remove(i);
                    } catch (Exception e) {
                        System.out.println(e.getMessage() + t.size());
                    }
                    a = result.size();
                    ch = parse(h);
                    result.addAll(ch);
                    for (int j = a; j < result.size(); j++) {
                        result.set(j, racine + result.get(j));
                    }
                }
            }
            return result;
        }

    }


    public static ArrayList<String> to_create_combin(ArrayList<String> letter)
    {
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < letter.size();i++)
        {
            String add = letter.get(i);
            for(int j = 0; j < letter.size();j++)
            {
                if(!res.contains(add))
                {
                    res.add(add);
                }
                if (!add.contains(letter.get(j)))
                    add += letter.get(j);
            }
            if (!res.contains(add))
                res.add(add);
        }
        return res;
    }

    public static String best_solution(ArrayList<String> letter, Dawg graph)
    {
        int max = 0;
        ArrayList<String> tmp = to_create_combin(letter);
        ArrayList<String> res1 = new ArrayList<String>();
        for(int i = 0; i < tmp.size();i++)
        {
            ArrayList<String> res = new ArrayList<String>();
            ArrayList<String> ab = test(tmp.get(i));
            res = parse(ab);
            ArrayList<String> test = new ArrayList<String>();
            for(int j = 0; j < res.size();j++)
            {
                int cost = valuer_of_string(res.get(j));
                if (max >= cost)
                    continue;
                else {
                        if (graph.word_existe(graph,res.get(j),0)) {
                            test.add(res.get(j));
                            max = cost;
                        }
                }
            }
            if (test.size() > 0 && !(res1.contains(test)))
                res1.add(test.toString());
        }
        int pos = res1.size() - 1;
        return enleve_crochet(res1.get(pos));

    }

    public static String enleve_crochet(String tmp)
    {
        String result = "";
        for(int i = 1; i < tmp.length() - 1; i++)
        {
            result += tmp.charAt(i);
        }
        return result;

    }


    public static ArrayList<String> test(String letter)
    {
        ArrayList<String > res = new ArrayList<String>();
        for(int i = 0; i < letter.length();i++)
        {
            char t = letter.charAt(i);
            res.add(Character.toString(t));
        }
        return res;
    }
    public static boolean existenceMot(String str) throws IOException {
        return Files.readAllLines(new File("tests/ressources/dico/dico.txt").toPath()).contains(str);
    }

    public static int valuer_of_string(String a)
    {
        int res = 0;
        for(int i = 0; i < a.length();i++)
        {
            res += convert_cost(a.charAt(i));
        }
        return res;
    }

    public static int convert_cost(char a) {
        if (a == 'a' || a == 'e' || a == 'n' || a == 's' || a == 'i' || a == 'r' || a == 't' || a == 'u' || a == 'd' || a == 'l') {
            return 1;
        }
        if (a == 'd' || a == 'm' || a == 'g') {
            return 2;
        }
        if (a == 'b' || a == 'c' || a == 'p') {
            return 3;
        }
        if (a == 'f' || a == 'h' || a == 'v') {
            return 4;
        }
        if (a == 'j' || a == 'q' ) {
            return 8;
        }
        if (a == 'k' || a == 'w' || a == 'x' || a == 'y' || a == 'z') {
            return 10;
        }
        return 0;

    }
}
