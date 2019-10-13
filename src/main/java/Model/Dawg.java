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
    public  boolean end_word;

    public  Dawg ()
    {
        next_ = null;
        child_ = null;
        letter_ = null;
        end_word = false;
    }

    public Dawg(Character letter)
    {
        next_ = null;
        child_ = null;
        letter_ = letter;
        end_word = false;
    }

    public Dawg create_dawg(String filename)
    {
        Dawg result = new Dawg();
        ArrayList<String> all_word = null;
        try {
            all_word = create_liste(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < all_word.size();i++)
        {
            create_word(result, all_word.get(i),0);
        }
        return result;
    }

    public Dawg create_word(Dawg graph,String word,int cmp)
    {
        if (cmp == word.length()) {
            graph.end_word = true;
            return graph;
        }
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
        if (graph.next_ != null && (graph.child_ == null || graph.letter_ != word.charAt(cmp)))
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
    public boolean word_existe(Dawg graph,String word,int cmp)
    {
        if (cmp == word.length() && graph.end_word == true)
            return true;
        else if (cmp == word.length() && graph.end_word == false)
            return false;
        if(graph.letter_ == null)
            return false;
        if(graph.child_ != null && word.charAt(cmp) == graph.letter_ )
        {
            return word_existe(graph.child_,word,cmp+ 1);
        }
        else if (word.charAt(cmp) == graph.letter_ && graph.child_ == null && cmp != word.length())
        {
            return false;
        }
        else if (word.charAt(cmp) != graph.letter_ && graph.next_ != null)
        {
            return word_existe(graph.next_,word,cmp);
        }
        else if (word.charAt(cmp) != graph.letter_ && graph.next_ == null)
        {
            return false;
        }


        return false;
    }

}
