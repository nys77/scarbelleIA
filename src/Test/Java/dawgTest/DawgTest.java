package dawgTest;

import Model.Dictionary.Dawg;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DawgTest {

    @Test
    public void testDawg() {
        String filename = "/test/DawgTest";
        Dawg a = new Dawg();
        Dawg result = a.create_dawg(filename);

        Dawg letterB = result.next_;
        Dawg letterH = letterB.next_;
        Dawg letterN = letterH.next_;

        assertTrue(result.letter_ == 'A' && result.child_.letter_ == 'N' && result.child_.child_.letter_ == 'A', "Erreur racine A");

        assertTrue(letterB.letter_ == 'B' && letterB.child_.letter_ == 'A' && letterB.child_.child_.letter_ == 'N'
                && letterB.child_.child_.child_.letter_ == 'A' && letterB.child_.child_.child_.child_.letter_ == 'N'
                && letterB.child_.child_.child_.child_.child_.letter_ == 'E', "Erreur letterB chemin BANANE");

        assertTrue(letterB.child_.child_.child_.next_.letter_ == 'I' && letterB.child_.child_.child_.next_.child_.letter_ == 'E', "Erreur letterB chemin I E");

        assertTrue(letterH.letter_ == 'H' && letterH.child_.letter_ == 'E' && letterH.child_.child_.letter_ == 'L' &&
                letterH.child_.child_.child_.letter_ == 'L' && letterH.child_.child_.child_.child_.letter_ == 'O'
                && letterH.child_.child_.child_.child_.child_.letter_ == 'W', "Erreur letterH chemin HELLOW");

        assertTrue(letterH.child_.next_.letter_ == 'A' && letterH.child_.next_.next_.letter_ == 'I' &&
                letterH.child_.next_.child_.letter_ == 'L' && letterH.child_.next_.child_.child_.letter_ == 'L' &&
                letterH.child_.next_.child_.child_.child_.letter_ == 'O' && letterH.child_.next_.child_.child_.child_.child_.letter_ == 'W'
                && letterH.child_.child_.next_.letter_ == 'O' && letterH.child_.child_.next_.next_.letter_ == 'A', "Erreur letterH chemins annexes");

        assertTrue(letterN.letter_ == 'N', "Erreur letterN");
    }

    @Test
    public void test_find_word() {
        String filename = "/test/DawgTest";
        Dawg a = new Dawg();
        Dawg result = a.create_dawg(filename);

        Dawg letterB = result.next_;

        assertTrue(result.word_existe(result, "BANANE", 0), "BANANE devrait exister depuis la racine");
        assertTrue(result.word_existe(letterB, "BANANE", 0), "BANANE devrait être trouvé depuis le noeud B");
        assertFalse(result.word_existe(letterB, "ANANE", 0), "ANANE ne devrait pas être trouvé depuis le noeud B");
    }
}
