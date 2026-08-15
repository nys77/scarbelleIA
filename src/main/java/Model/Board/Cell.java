package Model.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import Config.AssetsConfig;

public class Cell {


    public enum Type {
        letre_triple,
        lettre_double,
        mot_double,
        mot_triple,
        nothing
    }


    int x_;
    int y_;
    public Image img_;
    String url_;
    public Type type_cell;
    public Cell(int x, int y)
    {
        x_ = x;
        y_= y;
        load_img_type(x,y);
    }

    public void load_img_type(int x, int y)
    {
        if (is_center(x,y))
        {
            url_ = AssetsConfig.CELL_CENTRAL;
            type_cell = Type.mot_double;

        }
        else if (is_word_triple(x,y))
        {
            url_ = AssetsConfig.MOT_TRIPLE;
            type_cell = Type.mot_triple;
        }
        else if (is_word_double(x,y))
        {
            url_ = AssetsConfig.MOT_DOUBLE;
            type_cell = Type.mot_double;
        }
        else if (is_letter_double(x,y))
        {
            url_ = AssetsConfig.LETTRE_DOUBLE;
            type_cell = Type.lettre_double;
        }
        else if (is_letter_triple(x,y))
        {
            url_ = AssetsConfig.LETTRE_TRIPLE;
            type_cell = Type.letre_triple;
        }
        else
        {
            url_ = AssetsConfig.CELL_NORMAL;
            type_cell = Type.nothing;
        }

        try {
            img_ = ImageIO.read(getClass().getResource(url_));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean is_center(int x,int y)
    {
        return (y == 7 && x == 7);
    }

    public boolean is_letter_triple(int x,int y)
    {
        if (x == 5 && y == 1)
            return true;
        if (x == 9 && y == 1)
            return true;
        if (x == 1 && y == 5)
            return true;
        if (x == 5 && y == 5)
            return true;
        if (x == 9 && y == 5)
            return true;
        if (x == 13 && y == 5)
            return true;
        if (x == 1 && y == 9)
            return true;
        if (x == 5 && y == 9)
            return true;
        if (x == 9 && y == 9)
            return true;
        if (x == 13 && y == 9)
            return true;
        if (x == 5 && y == 13)
            return true;
        if (x == 9 && y == 13)
            return true;
        return false;
    }

    public boolean is_letter_double(int x,int y)
    {
        if (x == 3 && y == 0)
            return true;
        if (x == 11 && y == 0)
            return true;
        if (x == 6 && y == 2)
            return true;
        if (x == 8 && y == 2)
            return true;
        if (x == 0  && y == 3)
            return true;
        if (x == 7 && y == 3)
            return true;
        if (x == 14 && y == 3)
            return true;
        if (x == 2 && y == 6)
            return true;
        if (x == 6 && y == 6)
            return true;
        if (x == 8 && y == 6)
            return true;
        if (x == 12 && y == 6)
            return true;
        if (x == 3 && y == 7)
            return true;
        if (x == 11 && y == 7)
            return true;
        if (x == 2 && y == 8)
            return true;
        if (x == 6 && y == 8)
            return true;
        if (x == 8 && y == 8)
            return true;
        if (x == 12 && y == 8)
            return true;
        if (x == 0 && y == 11)
            return true;
        if (x == 7 && y == 11)
            return true;
        if (x == 14 && y == 11)
            return true;
        if (x == 6 && y == 12)
            return true;
        if (x == 8 && y == 12)
            return true;
        if (x == 3 && y == 14)
            return true;
        if (x == 11 && y == 14)
            return true;
        return false;
    }

    public boolean is_word_double(int x,int y)
    {
        if (y == 1 &&  x == 1)
            return true;
        if (y == 2 && x == 2)
            return true;
        if (y == 3 && x == 3)
            return true;
        if (y == 4 && x == 4)
            return true;
        if (y == 7 && x == 7)
            return true;
        if (y == 10 && x == 10)
            return true;
        if (y == 11 && x == 11)
            return true;
        if (y == 12 && x == 12)
            return true;
        if (y == 13 && x == 13)
            return true;
        if (y == 1 && x == 13)
            return true;
        if (y == 2 && x == 12)
            return true;
        if (y == 3 && x == 11)
            return true;
        if (y == 4 && x == 10)
            return true;
        if (y == 10 && x == 4)
            return true;
        if (y == 11 && x == 3)
            return true;
        if (y == 12 && x == 2)
            return true;
        if (y == 13 && x == 1)
            return true;
        return false;
    }

    public boolean is_word_triple(int x,int y)
    {
        if (x == 0 && y == 0)
            return true;
        if (x == 7 && y == 0)
            return true;
        if (x == 14 && y == 0)
            return true;

        if (x == 0 && y == 7)
            return true;
        if (x == 14 && y == 7)
            return true;

        if (x == 0 && y == 14)
            return true;
        if (x == 7 && y == 14)
            return true;
        if (x == 14 && y  == 14)
            return true;

        return false;
    }
}
