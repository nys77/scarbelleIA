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


    public void print_grid()
    {
        System.out.println(" ----------------------------------------------");
        for (int i = 0; i < 15; i++)
        {
            System.out.print("| ");
            for (int j = 0; j < 15;j++)
            {
                cell a = list_cell[i][j];
                if (a.type_cell == cell.Type.mot_double)
                {
                    System.out.print(" X ");
                }
                if (a.type_cell == cell.Type.mot_triple)
                {
                    System.out.print(" W ");
                }
                if (a.type_cell == cell.Type.lettre_double)
                {
                    System.out.print(" U ");
                }
                if (a.type_cell == cell.Type.letre_triple)
                {
                    System.out.print(" V ");
                }
                if (a.type_cell == cell.Type.nothing)
                {
                    System.out.print(" - ");
                }
            }
            System.out.println("|");
        }
        System.out.println(" ----------------------------------------------");
    }


}
