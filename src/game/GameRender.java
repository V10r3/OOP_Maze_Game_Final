package game;

import javax.swing.*;
import java.awt.*;

public class GameRender extends JPanel {
    private Level level;
    private int[][] mazeStructure;
    public static final int GRID_SIZE = Level.GRID_SIZE;

    // Constructor accepts Level (to access the player) and the maze structure.
    public GameRender(Level level, int[][] mazeStructure) {
        this.level = level;
        this.mazeStructure = mazeStructure;
        setPreferredSize(new Dimension(mazeStructure[0].length * GRID_SIZE,
                                       mazeStructure.length * GRID_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Determine player's pixel position from cell coordinates.
        Player player = level.getPlayer();
        int playerCellX = player.getPlayerX();
        int playerCellY = player.getPlayerY();
        int playerPixelX = playerCellX * GRID_SIZE;
        int playerPixelY = playerCellY * GRID_SIZE;

        // Get the current viewport dimensions.
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        // Calculate offsets to center the player.
        int offsetX = playerPixelX - viewWidth / 2 + GRID_SIZE / 2;
        int offsetY = playerPixelY - viewHeight / 2 + GRID_SIZE / 2;

        // Clamp the offsets so the view stays within maze bounds.
        int mazePixelWidth = mazeStructure[0].length * GRID_SIZE;
        int mazePixelHeight = mazeStructure.length * GRID_SIZE;
        int maxOffsetX = mazePixelWidth - viewWidth;
        int maxOffsetY = mazePixelHeight - viewHeight;
        offsetX = Math.max(0, Math.min(offsetX, maxOffsetX));
        offsetY = Math.max(0, Math.min(offsetY, maxOffsetY));

        // Apply the translation to simulate the camera.
        g2d.translate(-offsetX, -offsetY);

        // Determine the range of cells that are visible on the screen.
        int startCol = offsetX / GRID_SIZE;
        int startRow = offsetY / GRID_SIZE;
        int endCol = Math.min(mazeStructure[0].length, (offsetX + viewWidth) / GRID_SIZE + 2);
        int endRow = Math.min(mazeStructure.length, (offsetY + viewHeight) / GRID_SIZE + 2);

        // Only render the cells that are in view.
        for (int i = startRow; i < endRow; i++) {
            for (int j = startCol; j < endCol; j++) {
                int cell = mazeStructure[i][j];
                Color cellColor;
                if (cell == 1) 
                    cellColor = Color.BLACK;
                else if (cell == 2)
                    cellColor = Color.BLUE;
                else if (cell == 3)
                    cellColor = Color.RED;
                else
                    cellColor = Color.WHITE;

                g2d.setColor(cellColor);
                g2d.fillRect(j * GRID_SIZE, i * GRID_SIZE, GRID_SIZE, GRID_SIZE);

                // Optional: draw grid lines for clarity.
                g2d.setColor(Color.GRAY);
                g2d.drawRect(j * GRID_SIZE, i * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            }
        }
        g2d.dispose();
    }
}