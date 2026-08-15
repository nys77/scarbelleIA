import Model.Dictionary.Dawg;
import Model.Model;
import View.Screens.HomeView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        Dawg dawgLoader = new Dawg();
        Dawg dawgGraph = dawgLoader.create_dawg(Config.AssetsConfig.DICO_PATH);
        Model gameModel = new Model();

        SwingUtilities.invokeLater(() -> {
            HomeView homeView = new HomeView(gameModel, dawgGraph);
            homeView.setVisible(true);
        });
    }
}
