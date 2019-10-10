package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Dawg {

    public Dawg next_;
    public Dawg child_;
    public Character letter_;

    public  Dawg ()
    {
        next_ = null;
        child_ = null;
        letter_ = null;
    }

    public Dawg(Character letter)
    {
        next_ = null;
        child_ = null;
        letter_ = letter;
    }

    public Dawg create_dawg(String filename)
    {
        Dawg result = new Dawg();
        ArrayList<String> all_word = new ArrayList<String>();
        all_word.add("ANNIBALLE");
        all_word.add("PIPI");
        /*try {
            all_word = create_liste(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        for(int i = 0; i < all_word.size();i++)
        {
            create_word(result, all_word.get(i),0);
        }
        return result;
    }

    public Dawg create_word(Dawg graph,String word,int cmp)
    {
        if (cmp == word.length())
            return graph;
        if(graph.letter_ == null)
        {
            graph.letter_ = word.charAt(cmp);
            graph.child_ = new Dawg();
            return create_word(graph,word,cmp);
        }
        if(graph.next_ == null && graph.child_ == null && graph.letter_ != null)
        {
            graph.child_ = new Dawg();
        }
        else if(graph.next_ == null && graph.child_ != null && graph.letter_ != null)
        {
            graph.next_ = new Dawg();
        }
        if (graph.next_ != null && graph.child_ == null)
        {
            return create_word(graph.next_,word,cmp);
        }
        else if (graph.letter_ != null && graph.letter_ == word.charAt(cmp))
        {
            return create_word(graph.child_,word,cmp + 1);
        }
        return graph;
    }


    public static ArrayList<String> create_liste(String filename) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = "";
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        br.close();


        return result;
    }

}
