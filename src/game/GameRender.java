package game;

import javax.swing.*;
import java.awt.*;

public class GameRender extends JPanel {
    private int[][] mazeStructure;
    private static final int GRID_SIZE = 25;

    public GameRender(int[][] mazeStructure) {
        this.mazeStructure = mazeStructure;
        setPreferredSize(new Dimension(mazeStructure[0].length * GRID_SIZE, mazeStructure.length * GRID_SIZE));
        setLayout(null);
        renderMaze();
    }

    private void renderMaze() {
        for (int i = 0; i < mazeStructure.length; i++) {
            for (int j = 0; j < mazeStructure[i].length; j++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                cell.setBounds(j * GRID_SIZE, i * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                
                if (mazeStructure[i][j] == 1) {
                    cell.setBackground(Color.BLACK);
                } else if (mazeStructure[i][j] == 'P') {
                    cell.setBackground(Color.BLUE);
                } else if (mazeStructure[i][j] == 'G') {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.WHITE);
                }
                
                add(cell);
            }
        }
    }
}