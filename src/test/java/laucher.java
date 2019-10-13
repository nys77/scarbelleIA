import dawgTest.test;

public class laucher {

    public static void main(String[] args) {
        test.testDawg("tests/ressources/test/DawgTest");
        test.test_find_word("tests/ressources/test/DawgTest");
        System.out.println("Succes");
    }
}
