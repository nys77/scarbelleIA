package dawgTest;

import Model.Dawg;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class test {

    public static void testDawg(String filename)
    {
        ArrayList<String> list = null;
        try {
            list = create_liste(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Dawg to_be_tested = new Dawg();
        to_be_tested =  to_be_tested.create_dawg(filename);
        System.out.println("hello");

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
