package game;

import javax.swing.*;
import java.awt.*;
import java.awt.Point;
import java.util.*;
import java.util.Stack;

public class Level extends JFrame {
    protected int[][] mazeStructure;
    private GameRender gameRender;
    private Player player;
    protected int mazeSize;   // Now customizable from outside
    public static final int GRID_SIZE = 25;

    public Level(int mazeSize) {
        this.mazeSize = mazeSize;
        setTitle("Maze Game Level");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Show a loading message while the maze is generated.
        JLabel loadingLabel = new JLabel("Generating maze, please wait...", SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
        setVisible(true);

        new SwingWorker<int[][], Void>() {
            @Override
            protected int[][] doInBackground() throws Exception {
                return generatePerfectMaze(mazeSize);
            }

            
            @Override
            protected void done() {
                try {
                    mazeStructure = get();
                    // Set fixed positions: player start at (1,1) and goal at bottom-right.
                    mazeStructure[1][1] = 2;
                    mazeStructure[mazeSize - 2][mazeSize - 2] = 3;

                    // Remove all components.
                    Container content = getContentPane();
                    content.removeAll();

                    // Add the extra UI component (e.g., timer label) from the subclass.
                    JComponent northComp = getNorthComponent();
                    if (northComp != null) {
                        content.add(northComp, BorderLayout.NORTH);
                    }

                    // Then add the game render panel.
                    gameRender = new GameRender(Level.this, mazeStructure);
                    gameRender.setFocusable(true);
                    content.add(gameRender, BorderLayout.CENTER);

                    // Create the player and add its KeyListener.
                    player = new Player(Level.this);
                    gameRender.addKeyListener(player);
                    gameRender.requestFocusInWindow();

                    revalidate();
                    repaint();

                    // **Automatically start the timer or stopwatch based on the mode.**
                    if (Level.this instanceof TimedLevel) {
                        ((TimedLevel) Level.this).startTimer();
                    } else if (Level.this instanceof ChillLevel) {
                        ((ChillLevel) Level.this).startStopwatch();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    // This method can be overridden by subclasses (e.g., TimedLevel, ChillLevel) to provide a north component.
    protected JComponent getNorthComponent() {
        return null;
    }

    public int[][] getMazeStructure() {
        return mazeStructure;
    }
    public Player getPlayer() {
        return player;
    }
    public void notifyUI() {
        if (gameRender != null) {
            gameRender.repaint();
        }
    }
    
    /**
     * Generates a perfect maze using an iterative DFS algorithm.
     * Fills the maze array with 1’s (walls) and carves passages (0’s).
     *
     * @param size should be odd – if even, it is decremented.
     * @return the generated maze.
     */
    private int[][] generatePerfectMaze(int size) {
        if (size % 2 == 0) {
            size--;
        }
        int[][] maze = new int[size][size];
        for (int i = 0; i < size; i++) {
            Arrays.fill(maze[i], 1);
        }

        Stack<Point> stack = new Stack<>();
        Point start = new Point(1, 1);
        maze[1][1] = 0;
        stack.push(start);
        Random rand = new Random();

        while (!stack.isEmpty()) {
            Point current = stack.peek();
            java.util.List<Point> neighbors = new java.util.ArrayList<>();
            int[][] directions = { {2, 0}, {-2, 0}, {0, 2}, {0, -2} };

            for (int[] d : directions) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];
                if (nx > 0 && ny > 0 && nx < size && ny < size && maze[ny][nx] == 1) {
                    neighbors.add(new Point(nx, ny));
                }
            }
            if (!neighbors.isEmpty()) {
                Collections.shuffle(neighbors, rand);
                Point chosen = neighbors.get(0);
                int wallX = current.x + (chosen.x - current.x) / 2;
                int wallY = current.y + (chosen.y - current.y) / 2;
                maze[wallY][wallX] = 0;
                maze[chosen.y][chosen.x] = 0;
                stack.push(chosen);
            } else {
                stack.pop();
            }
        }
        return maze;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For testing, launch a Level without extra UI – timer will not be visible.
            new Level(41);
        });
    }
}