package Model.Dictionary;

import Config.AssetsConfig;

public class DictionarySingleton {

    private static volatile Dawg instance;

    private DictionarySingleton() {
    }

    public static Dawg getInstance() {
        if (instance == null) {
            synchronized (DictionarySingleton.class) {
                if (instance == null) {
                    Dawg loader = new Dawg();
                    try {
                        instance = loader.create_dawg(AssetsConfig.DICO_PATH);
                    } catch (Exception e) {
                        e.printStackTrace();
                        instance = loader;
                    }
                }
            }
        }
        return instance;
    }
}
