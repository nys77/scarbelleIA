package Model;

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

    public Dawg create_dawg(String FileName)
    {
        Dawg result = new Dawg();
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("ADOBE");
        tmp.add("RADOBE");
        tmp.add("ADOBERER");
        create_word(result,tmp.get(0),0);
       /* for(int i = 0; i < tmp.size(); i++)
        {
            int cmp = 0;
            create_word(result,tmp.get(i),cmp);
        }*/
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
            return create_word(graph,word,cmp );
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

}
