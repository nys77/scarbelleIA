package Model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Model {

    private final TreeMap<Character, Integer> initialTileCounts;
    private final ArrayList<Character> tileBag;

    public Model() {
        initialTileCounts = new TreeMap<>();
        tileBag = new ArrayList<>();
        initializeTileDistribution();
    }

    public TreeMap<Character, Integer> getMap() {
        return initialTileCounts;
    }

    public void initializeTileDistribution() {
        addLetterToDistribution('a', 9);
        addLetterToDistribution('b', 2);
        addLetterToDistribution('c', 2);
        addLetterToDistribution('d', 3);
        addLetterToDistribution('e', 15);
        addLetterToDistribution('f', 2);
        addLetterToDistribution('g', 2);
        addLetterToDistribution('h', 2);
        addLetterToDistribution('i', 8);
        addLetterToDistribution('j', 1);
        addLetterToDistribution('k', 1);
        addLetterToDistribution('l', 5);
        addLetterToDistribution('m', 3);
        addLetterToDistribution('n', 6);
        addLetterToDistribution('o', 6);
        addLetterToDistribution('p', 2);
        addLetterToDistribution('q', 1);
        addLetterToDistribution('r', 6);
        addLetterToDistribution('s', 6);
        addLetterToDistribution('t', 6);
        addLetterToDistribution('u', 6);
        addLetterToDistribution('v', 2);
        addLetterToDistribution('w', 1);
        addLetterToDistribution('y', 1);
        addLetterToDistribution('z', 1);
        addLetterToDistribution('!', 2); // Joker / Blank tiles
    }

    private void addLetterToDistribution(Character letter, int count) {
        initialTileCounts.put(letter, count);
        set_rand(count, letter);
    }

    public void set_rand(int tileQuantity, Character letterToAdd) {
        for (int index = 0; index < tileQuantity; index++) {
            tileBag.add(letterToAdd);
        }
    }

    public void print_stock() {
        for (Map.Entry<Character, Integer> tileEntry : initialTileCounts.entrySet()) {
            System.out.println("| NB = " + tileEntry.getValue() + " of " + tileEntry.getKey() + " |");
        }
    }

    public ArrayList<Character> get_rand() {
        return tileBag;
    }
}
