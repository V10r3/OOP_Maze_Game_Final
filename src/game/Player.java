package game;
import menu.Menu;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player implements KeyListener {
    private int playerX, playerY;
    private int goalX, goalY;
    private int[][] mazeStructure;
    private Level level;

    public Player(Level level) {
        this.level = level;
        this.mazeStructure = level.getMazeStructure();
        findPositions();
    }

    private void findPositions() {
        for (int i = 0; i < mazeStructure.length; i++) {
            for (int j = 0; j < mazeStructure[i].length; j++) {
                if (mazeStructure[i][j] == 2) { // Player identifier
                    playerX = j;
                    playerY = i;
                } else if (mazeStructure[i][j] == 3) { // Goal identifier
                    goalX = j;
                    goalY = i;
                }
            }
        }
    }

    private void playerKeyPressed(KeyEvent evt) {
        int key = evt.getKeyCode();

        if (key == KeyEvent.VK_LEFT) movePlayer(-1, 0);
        if (key == KeyEvent.VK_RIGHT) movePlayer(1, 0);
        if (key == KeyEvent.VK_UP) movePlayer(0, -1);
        if (key == KeyEvent.VK_DOWN) movePlayer(0, 1);
    }

    public void movePlayer(int dx, int dy) {
        int newX = playerX + dx;
        int newY = playerY + dy;

        if (newX >= 0 && newX < mazeStructure[0].length &&
            newY >= 0 && newY < mazeStructure.length &&
            mazeStructure[newY][newX] != 1) { // Prevent moving into walls

            mazeStructure[playerY][playerX] = 0; // Clear old position
            mazeStructure[newY][newX] = 2;       // Update new position
            playerX = newX;
            playerY = newY;

            level.notifyUI(); // Refresh UI after movement
            checkWinCondition(); // Check if player reached the goal
        }
    }

    private void checkWinCondition() {
        if (playerX == goalX && playerY == goalY) { // Check goal position
            JOptionPane.showMessageDialog(level, "You Win!");
            Menu lvl = new Menu();
            lvl.setVisible(true);
            level.dispose(); // Close the game once won
        }
    }

    // Implement KeyListener methods
    @Override
    public void keyPressed(KeyEvent evt) {
        playerKeyPressed(evt);
    }

    @Override
    public void keyReleased(KeyEvent evt) {}

    @Override
    public void keyTyped(KeyEvent evt) {}
}