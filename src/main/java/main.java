import Model.Dawg;
import Model.model;
import View.init;

public class main {
    public static void main(String[] args) {
        Dawg a = new Dawg();
        Dawg result = a.create_dawg("tests/ressources/dico/dico.txt");
        System.out.println("Hello");
        model mode = new model();
        new init(mode,result);
    }
}
