package game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameRender extends JPanel {
    private Level level;
    private int[][] mazeStructure;
    public static final int GRID_SIZE = Level.GRID_SIZE;
    private Image playerImage; // The player graphic

    public GameRender(Level level, int[][] mazeStructure) {
        this.level = level;
        this.mazeStructure = mazeStructure;
        setPreferredSize(new Dimension(
                mazeStructure[0].length * GRID_SIZE,
                mazeStructure.length * GRID_SIZE));
        
        // Load the player image from the "res" folder.
        // Ensure that the "res" folder is on your classpath so that getResource works.
        try {
            // Using getResource ensures portability.
            playerImage = ImageIO.read(getClass().getResource("/res/player.png"));
        } catch (IOException e) {
            System.err.println("Error loading player image: " + e.getMessage());
        } catch (IllegalArgumentException iae) {
            // getResource returns null if the file is not found.
            System.err.println("Player image not found. Please ensure '/res/player.png' exists on your classpath.");
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Get the player's current cell position
        Player player = level.getPlayer();
        int playerCellX = player.getPlayerX();
        int playerCellY = player.getPlayerY();
        int playerPixelX = playerCellX * GRID_SIZE;
        int playerPixelY = playerCellY * GRID_SIZE;
        
        // Get viewport dimensions (the size of this panel)
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        
        // Calculate offsets to center the player in the view
        int offsetX = playerPixelX - viewWidth / 2 + GRID_SIZE / 2;
        int offsetY = playerPixelY - viewHeight / 2 + GRID_SIZE / 2;
        
        // Clamp the offsets so that the view remains within maze bounds.
        int mazePixelWidth = mazeStructure[0].length * GRID_SIZE;
        int mazePixelHeight = mazeStructure.length * GRID_SIZE;
        int maxOffsetX = mazePixelWidth - viewWidth;
        int maxOffsetY = mazePixelHeight - viewHeight;
        offsetX = Math.max(0, Math.min(offsetX, maxOffsetX));
        offsetY = Math.max(0, Math.min(offsetY, maxOffsetY));
        
        // Translate graphics for camera effect.
        g2d.translate(-offsetX, -offsetY);
        
        // Determine visible cells.
        int startCol = offsetX / GRID_SIZE;
        int startRow = offsetY / GRID_SIZE;
        int endCol = Math.min(mazeStructure[0].length, (offsetX + viewWidth) / GRID_SIZE + 2);
        int endRow = Math.min(mazeStructure.length, (offsetY + viewHeight) / GRID_SIZE + 2);
        
        // Render the maze cells.
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                int cell = mazeStructure[row][col];
                Color cellColor;
                
                if (cell == 1)
                    cellColor = Color.BLACK;
                else if (cell == 2)
                    cellColor = Color.BLUE;  // Typically the start position
                else if (cell == 3)
                    cellColor = Color.RED;   // Typically the goal
                else
                    cellColor = Color.WHITE;
                
                g2d.setColor(cellColor);
                g2d.fillRect(col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                
                // Draw grid lines for clarity (optional)
                g2d.setColor(Color.GRAY);
                g2d.drawRect(col * GRID_SIZE, row * GRID_SIZE, GRID_SIZE, GRID_SIZE);
            }
        }
        
        // Draw the player.
        // If the player image successfully loaded, draw it scaled to the grid cell.
        if (playerImage != null) {
            g2d.drawImage(playerImage, playerPixelX, playerPixelY, GRID_SIZE, GRID_SIZE, this);
        } else {
            // Fallback: draw a blue oval if the image is missing.
            g2d.setColor(Color.BLUE);
            g2d.fillOval(playerPixelX, playerPixelY, GRID_SIZE, GRID_SIZE);
        }
        
        g2d.dispose();
    }
}