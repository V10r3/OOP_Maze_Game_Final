package game;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Level extends JFrame {
    protected int[][] mazeStructure;
    private JPanel mazePanel;
    private Player player;
    private int mazeSize = 20; // Change as needed

    public Level() {
        generateMaze(); // Generate a new maze dynamically
        System.out.println("Level initialized!"); // Debugging output

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((mazeSize * 25), (mazeSize * 25));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        mazePanel = new JPanel();
        mazePanel.setLayout(null);
        add(mazePanel, BorderLayout.CENTER);

        player = new Player(this);
        addKeyListener(player);
        setFocusable(true);

        renderMaze();
        setVisible(true); // Ensure Level appears
    }

    public int[][] getMazeStructure() {
        return mazeStructure;
    }

    private void generateMaze() {
        Random rand = new Random();
        
        do {
            mazeStructure = new int[mazeSize][mazeSize];

            for (int i = 0; i < mazeSize; i++) {
                for (int j = 0; j < mazeSize; j++) {
                    mazeStructure[i][j] = rand.nextInt(100) < 60 ? 1 : 0; // 60% walls for better playability
                }
            }

            // Set fixed player and goal positions
            mazeStructure[1][1] = 2; // Player start
            mazeStructure[mazeSize - 2][mazeSize - 2] = 3; // Goal

        } while (!isSolvable()); // ✅ Ensure there's a valid path!
    }

    private boolean isSolvable() {
        boolean[][] visited = new boolean[mazeSize][mazeSize];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{1, 1}); // Start position
        visited[1][1] = true;

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int x = pos[0], y = pos[1];

            if (mazeStructure[x][y] == 3) return true; // ✅ Goal reached!

            int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : directions) {
                int newX = x + dir[0], newY = y + dir[1];

                if (newX >= 0 && newX < mazeSize && newY >= 0 && newY < mazeSize &&
                    mazeStructure[newX][newY] != 1 && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        return false; // ❌ No path found to the goal
    }

    public void renderMaze() {
        mazePanel.removeAll();
        int gridSize = 25;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                JLabel cell = new JLabel();
                cell.setOpaque(true);
                cell.setBounds(j * gridSize, i * gridSize, gridSize, gridSize);

                if (mazeStructure[i][j] == 1) {
                    cell.setBackground(Color.BLACK);
                } else if (mazeStructure[i][j] == 2) {
                    cell.setBackground(Color.BLUE);
                } else if (mazeStructure[i][j] == 3) {
                    cell.setBackground(Color.RED);
                } else {
                    cell.setBackground(Color.WHITE);
                }

                mazePanel.add(cell);
            }
        }

        mazePanel.revalidate();
        mazePanel.repaint();
    }

    public void notifyUI() {
        renderMaze();
    }
}