package View;

import javax.swing.*;
import java.awt.*;

public class panel extends JPanel {
    public panel(Image img)
    {
        image = img;
    }
    private Image image;

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int height = this.getSize().height;
        int width = this.getSize().width;
        g.drawImage(image,10,10,width, height , this);
    }
}
