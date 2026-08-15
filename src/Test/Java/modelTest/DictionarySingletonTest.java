package modelTest;

import Model.Dictionary.Dawg;
import Model.Dictionary.DictionarySingleton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DictionarySingletonTest {

    @Test
    public void testSingletonUniqueness() {
        Dawg instance1 = DictionarySingleton.getInstance();
        Dawg instance2 = DictionarySingleton.getInstance();

        assertNotNull(instance1, "L'instance du dictionnaire ne doit pas être nulle");
        assertSame(instance1, instance2, "Les deux appels doivent retourner exactement la même instance (Singleton)");
    }
}
