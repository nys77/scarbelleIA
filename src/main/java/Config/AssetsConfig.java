package Config;

public class AssetsConfig {
    public static final int BOARD_SIZE = 15;
    public static final int RACK_SIZE = 7;
    public static final String DICO_PATH = "/dico/dico.txt";
    
    // Cell images
    public static final String CELL_CENTRAL = "/cell/cell_central.png";
    public static final String MOT_TRIPLE = "/cell/mot_triple.png";
    public static final String MOT_DOUBLE = "/cell/mot_double.png";
    public static final String LETTRE_DOUBLE = "/cell/lettre_double.png";
    public static final String LETTRE_TRIPLE = "/cell/lettre_triple.png";
    public static final String CELL_NORMAL = "/cell/cell_normal.png";

    // App Icon
    public static final String APP_ICON = "/icon/app_icon.png";

    // Helper for letter images
    public static String getLetterImagePath(String letter) {
        return "/lettre/" + letter.toUpperCase() + ".png";
    }

    public static void applyAppIcon(java.awt.Window window) {
        try {
            java.net.URL iconUrl = AssetsConfig.class.getResource(APP_ICON);
            if (iconUrl != null) {
                java.awt.Image icon = javax.imageio.ImageIO.read(iconUrl);
                if (window instanceof javax.swing.JFrame frame) {
                    frame.setIconImage(icon);
                }
                if (java.awt.Taskbar.isTaskbarSupported()) {
                    java.awt.Taskbar taskbar = java.awt.Taskbar.getTaskbar();
                    if (taskbar.isSupported(java.awt.Taskbar.Feature.ICON_IMAGE)) {
                        taskbar.setIconImage(icon);
                    }
                }
            }
        } catch (Exception ignored) {
        }
    }
}
