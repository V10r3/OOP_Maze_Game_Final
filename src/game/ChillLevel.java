package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import menu.Menu;
public class ChillLevel extends Level {
    private JLabel timerLabel;
    private Timer stopwatch;
    private long startTime;

    // The constructor now takes a mazeSize parameter (e.g., 21 for small, 41 for medium, 101 for large)
    public ChillLevel(int mazeSize) {
        super(mazeSize);
        setTitle("Chill Mode – Maze Game");

        // Initialize the timer label.
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setOpaque(true);
        // Optional for visual debugging: timerLabel.setBackground(Color.CYAN);

        // Set initial text for the stopwatch.
        timerLabel.setText("Elapsed Time: 0 seconds");

        // Add the menu bar with options.
        addMenu();
    }

    @Override
    public JComponent getNorthComponent() {
        return timerLabel;
    }

    // Call this method once the maze is generated to start the stopwatch.
    public void startStopwatch() {
        startTime = System.currentTimeMillis();
        stopwatch = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = (System.currentTimeMillis() - startTime) / 1000;
                System.out.println("Elapsed Time: " + elapsed + " seconds");
                timerLabel.setText("Elapsed Time: " + elapsed + " seconds");
            }
        });
        stopwatch.start();
    }

    // Called when the player wins the maze.
    public void playerWon() {
        if (stopwatch != null) {
            stopwatch.stop();
        }
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        JOptionPane.showMessageDialog(this, "You Win! Your time: " + elapsed + " seconds");
        new ModeSelection();
        dispose();
    }

    // Helper method to add a Swing menu bar with options.
    private void addMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");
        JMenuItem mainMenuItem = new JMenuItem("Return to Main Menu");
        JMenuItem quitItem = new JMenuItem("Quit");

        mainMenuItem.addActionListener(e -> {
            if (stopwatch != null && stopwatch.isRunning()) {
                stopwatch.stop();
            }

            new Menu().setVisible(true);  // Return to the main menu.
            dispose();
        });

        quitItem.addActionListener(e -> System.exit(0));

        optionsMenu.add(mainMenuItem);
        optionsMenu.add(quitItem);
        menuBar.add(optionsMenu);
        setJMenuBar(menuBar);
    }

    // Optional main for testing ChillLevel independently.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChillLevel cl = new ChillLevel(41);
            new Timer(500, e -> {
                cl.startStopwatch();
                ((Timer)e.getSource()).stop();
            }).start();
        });
    }
}