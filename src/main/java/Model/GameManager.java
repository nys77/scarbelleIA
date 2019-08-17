package Model;

import View.Main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Map;

public class GameManager {

    Main main_;

    public GameManager(Player player1) {
        main_ = player1.getMain();
        ArrayList<String> a = convert(main_.charac_main);
        int nb_car = 6;
       /* ArrayList<String> res = a;
        for (int i =0 ;i < nb_car; i++){
            res = combinaisons(res,a);
        }*/
        char[][] z = Combinaisons(convert_array_to_string(main_.charac_main));
        for (char[] i : z) {
            System.out.println(i);
        }
        System.out.println(sommes(7));


        ArrayList<String> tmp2 = new ArrayList<String>();
        tmp2.add("a");
        tmp2.add("b");
        tmp2.add("c");
        ArrayList<ArrayList<String>>  tmp1 = combinaison(tmp2);
        System.out.println(tmp1.size());
        for (int i = 0; i < tmp1.size();i++)
        {
            System.out.println(tmp1.get(i).toString());
        }
        /*for (int j = 0; j < res.size();j++)
        {
            System.out.println(res.get(j));
        }*/

    }


    public ArrayList<ArrayList<String>> combinaison(ArrayList<String> letter) {
        String stop = letter.toString();
        String tmp = "";
        ArrayList<ArrayList<String>> res = new ArrayList<ArrayList<String>>();
        int nb_combinaison = sommes(3);
        int cmp = 0;
        int n = 3;
        int k = 1;
        while (cmp < nb_combinaison)
        {
            ArrayList<String> list = new ArrayList<String>();
            ArrayList<String> pro = letter;
            for(int i = 0; i < letter.size() - 1;i++)
            {
                String tmp1 = pro.get(i);
                pro.set(i,letter.get(i + 1));
                pro.set(i + 1, tmp1);
                String tmp2 = "";
                for(int j = 0; j < letter.size() - i; j++)
                {
                    tmp2 += pro.get(j);
                }
                list.add(tmp2);
                cmp++;
            }
            res.add(list);
        }
        return res;
    }

    public ArrayList<String> combinaisons(ArrayList<String> to_complete, ArrayList<String> letter) {
        ArrayList<String> tmp = new ArrayList<String>();
        int cmp = 0;
        for (int i = 0; i < to_complete.size(); i++) {
            for (int j = 0; j < letter.size(); j++) {
                String re = to_complete.get(i).concat(letter.get(j));
                tmp.add(re);
              /*  try {
                    if (existenceMot(re))
                        tmp.add(re);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                cmp++;
            }
        }
        return tmp;
    }


    static char[][] Combinaisons(String mot) {
        int longueur = mot.length();
        int nbr = (int) Math.pow(2, longueur);
        char[][] comb = new char[nbr][longueur];
        int k = 0;
        for (int i = 0; i < nbr; i++) {
            k = i;
            for (int j = longueur - 1; j >= 0; j--) {
                if (k % 2 == 0) {
                    k /= 2;
                } else {
                    comb[i][j] = mot.charAt(j);
                    k /= 2;
                }
            }
        }
        return comb;
    }


    public ArrayList<String> convert(ArrayList<Character> list) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            res.add(list.get(i).toString());
        }
        return res;
    }

    public String convert_array_to_string(ArrayList<Character> list) {
        String res = "";
        for (int i = 0; i < list.size(); i++) {
            res += list.get(i).toString();
        }
        return res;
    }

    public static boolean existenceMot(String str) throws IOException {
        return Files.readAllLines(new File("tests/ressources/dico/dico.txt").toPath()).contains(str);
    }


    public int factorielle(int n)
    {
        int res = 1;
        for (int i = 1; i <= n; i++)
        {
            res *= i;
        }
        return res;
    }

    public int sommes(int n)
    {
        int res = 0;
        for(int i = 1; i <= n;i++)
        {
            res += factorielle(n)/(factorielle(n - i));
        }
        return res;
    }


}
