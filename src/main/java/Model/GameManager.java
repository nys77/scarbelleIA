package Model;

import Config.AssetsConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GameManager {

    public GameManager() {
    }

    public static List<String> convertCharacterListToStringList(List<Character> characterList) {
        List<String> resultList = new ArrayList<>();
        for (Character character : characterList) {
            if (character != null) {
                resultList.add(character.toString());
            }
        }
        return resultList;
    }

    public static String convertCharacterListToString(List<Character> characterList) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : characterList) {
            if (character != null) {
                stringBuilder.append(character);
            }
        }
        return stringBuilder.toString();
    }

    public static boolean checkWordExistenceInDictionary(String wordToValidate) {
        try (BufferedReader dictionaryReader = new BufferedReader(new InputStreamReader(
                GameManager.class.getResourceAsStream(AssetsConfig.DICO_PATH), StandardCharsets.UTF_8))) {
            return dictionaryReader.lines().anyMatch(dictionaryLine -> dictionaryLine.equalsIgnoreCase(wordToValidate));
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
