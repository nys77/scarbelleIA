package Model.Dictionary;

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
        java.io.InputStream is = Dawg.class.getResourceAsStream(filename);
        if (is == null) {
            throw new IOException("Resource not found: " + filename);
        }
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8));
        String line = "";
        while ((line = br.readLine()) != null) {
            result.add(line);
        }
        br.close();
        return result;
    }
    public boolean word_existe(Dawg graph, String word, int cmp) {
        if (graph == null) return false;
        if (cmp == word.length() && graph.end_word) {
            return true;
        } else if (cmp == word.length() && !graph.end_word) {
            return false;
        }
        if (graph.letter_ == null) {
            return false;
        }

        char targetChar = word.charAt(cmp);
        boolean charMatches = (Character.toLowerCase(targetChar) == Character.toLowerCase(graph.letter_));

        if (graph.child_ != null && charMatches) {
            return word_existe(graph.child_, word, cmp + 1);
        } else if (charMatches && graph.child_ == null && cmp != word.length()) {
            return false;
        } else if (!charMatches && graph.next_ != null) {
            return word_existe(graph.next_, word, cmp);
        } else if (!charMatches && graph.next_ == null) {
            return false;
        }

        return false;
    }

}
