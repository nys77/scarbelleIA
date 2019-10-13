package dawgTest;

import Model.Dawg;



public class test {

    public static void testDawg(String filename)
    {
        Dawg a = new Dawg();
        Dawg result = a.create_dawg(filename);

        Dawg letterB = result.next_;
        Dawg letterH = letterB.next_;
        Dawg letterN = letterH.next_;

        if ( !(result.letter_ == 'A' && result.child_.letter_ == 'N' && result.child_.child_.letter_ == 'A'))
            return;

        if(!(letterB.letter_ == 'B' && letterB.child_.letter_ == 'A' && letterB.child_.child_.letter_ == 'N'
                && letterB.child_.child_.child_.letter_ == 'A' && letterB.child_.child_.child_.child_.letter_ == 'N'
                && letterB.child_.child_.child_.child_.child_.letter_ == 'E')) {
            System.out.println("Erreur letterB");
            return;
        }
        if(!(letterB.child_.child_.child_.next_.letter_ == 'I' && letterB.child_.child_.child_.next_.child_.letter_ == 'E')) {
            System.out.println("Erreur letterB");
            return;
        }


        if(!(letterH.letter_ == 'H' && letterH.child_.letter_ == 'E' && letterH.child_.child_.letter_ == 'L' &&
                letterH.child_.child_.child_.letter_ == 'L' && letterH.child_.child_.child_.child_.letter_ == 'O'
                && letterH.child_.child_.child_.child_.child_.letter_ == 'W')) {
            System.out.println("Erreur letterH");
            return;
        }

        if(!(letterH.child_.next_.letter_ == 'A' && letterH.child_.next_.next_.letter_ == 'I' &&
                letterH.child_.next_.child_.letter_ == 'L' && letterH.child_.next_.child_.child_.letter_ == 'L' &&
                letterH.child_.next_.child_.child_.child_.letter_ == 'O' && letterH.child_.next_.child_.child_.child_.child_.letter_ == 'W'
                && letterH.child_.child_.next_.letter_ == 'O' && letterH.child_.child_.next_.next_.letter_ == 'A')) {
            System.out.println("Erreur letterH");
            return;
        }

        if (!(letterN.letter_ == 'N')) {
            System.out.println("Erreur letterN");
            return;
        }
    }

    public static void test_find_word(String filename)
    {
        Dawg a = new Dawg();
        Dawg result = a.create_dawg(filename);

        Dawg letterB = result.next_;
        Dawg letterH = letterB.next_;
        Dawg letterN = letterH.next_;

        if(result.word_existe(result,"BANANE",0) == false)
        {
            System.out.println("Banane Existe");
            return;
        }

        if (result.word_existe(letterB,"BANANE",0) == false)
        {
            System.out.println("Banane is in graph with letter B");
            return;
        }

        if (result.word_existe(letterB,"ANANE",0) != false)
        {
            System.out.println("Anane is in graph with letter B");
            return;
        }

    }
}
