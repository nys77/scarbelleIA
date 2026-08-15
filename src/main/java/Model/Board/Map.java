package Model.Board;

import Config.AssetsConfig;
import javax.imageio.ImageIO;

public class Map {
    public Cell[][] list_cell;
    private final char[][] placedGrid;

    public Map() {
        list_cell = new Cell[AssetsConfig.BOARD_SIZE][AssetsConfig.BOARD_SIZE];
        placedGrid = new char[AssetsConfig.BOARD_SIZE][AssetsConfig.BOARD_SIZE];

        for (int rowIndex = 0; rowIndex < AssetsConfig.BOARD_SIZE; rowIndex++) {
            for (int columnIndex = 0; columnIndex < AssetsConfig.BOARD_SIZE; columnIndex++) {
                list_cell[rowIndex][columnIndex] = new Cell(rowIndex, columnIndex);
                placedGrid[rowIndex][columnIndex] = '_';
            }
        }
    }

    public void setLetter(int row, int col, char letter) {
        if (row >= 0 && row < AssetsConfig.BOARD_SIZE && col >= 0 && col < AssetsConfig.BOARD_SIZE) {
            placedGrid[row][col] = Character.toUpperCase(letter);
            String url = AssetsConfig.getLetterImagePath(String.valueOf(letter));
            try {
                list_cell[row][col].img_ = ImageIO.read(getClass().getResource(url));
            } catch (Exception ignored) {
            }
        }
    }

    public char getLetter(int row, int col) {
        if (row >= 0 && row < AssetsConfig.BOARD_SIZE && col >= 0 && col < AssetsConfig.BOARD_SIZE) {
            return placedGrid[row][col];
        }
        return '_';
    }

    public char[][] get_matrix() {
        return placedGrid;
    }

    public void print_grid() {
        System.out.println(" ----------------------------------------------");
        for (int rowIndex = 0; rowIndex < AssetsConfig.BOARD_SIZE; rowIndex++) {
            System.out.print("| ");
            for (int columnIndex = 0; columnIndex < AssetsConfig.BOARD_SIZE; columnIndex++) {
                char letter = placedGrid[rowIndex][columnIndex];
                if (letter != '_') {
                    System.out.print(" " + letter + " ");
                } else {
                    Cell targetCell = list_cell[rowIndex][columnIndex];
                    if (targetCell.type_cell == Cell.Type.mot_double) {
                        System.out.print(" X ");
                    } else if (targetCell.type_cell == Cell.Type.mot_triple) {
                        System.out.print(" W ");
                    } else if (targetCell.type_cell == Cell.Type.lettre_double) {
                        System.out.print(" U ");
                    } else if (targetCell.type_cell == Cell.Type.letre_triple) {
                        System.out.print(" V ");
                    } else {
                        System.out.print(" - ");
                    }
                }
            }
            System.out.println("|");
        }
        System.out.println(" ----------------------------------------------");
    }
}
