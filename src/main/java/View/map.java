package View;

import java.util.ArrayList;

public class map {
    cell[][] list_cell;
    public map()
    {
        list_cell = new cell[15][15];
        for (int i = 0; i < 15; i++)
        {
            for (int j = 0; j < 15;j++)
            {
                list_cell[i][j] = new cell(i,j);
            }
        }
    }
}
