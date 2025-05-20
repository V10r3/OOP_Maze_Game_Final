package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import menu.Menu;
public class TimedLevel extends Level {
    private JLabel timerLabel;
    private Timer countdownTimer;
    private int timeRemaining; // in seconds
    private long startTime;    // records the starting time in milliseconds

    public TimedLevel() {
        // Use a medium maze size (e.g., 41).
        super(41);
        setTitle("Timed Mode – Maze Game");

        // Initialize the timer label.
        timerLabel = new JLabel("", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setOpaque(true);
        // Optional for debugging: timerLabel.setBackground(Color.YELLOW);

        // Get the starting time limit from our dynamic timer logic (or default).
        timeRemaining = DynamicTimer.getTimeLimit();
        updateTimerLabel();

        // Add the menu bar with options.
        addMenu();

        // (The maze generation and timer starting will be handled via the Level class’s SwingWorker.)
    }

    @Override
    protected JComponent getNorthComponent() {
        return timerLabel;
    }

    private void updateTimerLabel() {
        timerLabel.setText("Time Remaining: " + timeRemaining + " seconds");
    }

    // Call this method once the maze is displayed.
    public void startTimer() {
        startTime = System.currentTimeMillis();
        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Time Remaining: " + timeRemaining);
                timeRemaining--;
                updateTimerLabel();
                if (timeRemaining <= 0) {
                    countdownTimer.stop();
                    gameOver();
                }
            }
        });
        countdownTimer.start();
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Time’s up! You lost.");
        new ModeSelection();
        dispose();
    }

    // Called when the player wins the maze.
    public void playerWon() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        long solveTime = System.currentTimeMillis() - startTime;
        DynamicTimer.updateAverage(solveTime);
        JOptionPane.showMessageDialog(this, "You Win! Your time: " + (solveTime / 1000) + " seconds");
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
            if (countdownTimer != null && countdownTimer.isRunning()) {
                countdownTimer.stop();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TimedLevel tl = new TimedLevel();
            // Optionally, start the timer (if not automatically started in Level’s done() method).
            new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tl.startTimer();
                    ((Timer)e.getSource()).stop();
                }
            }).start();
        });
    }
}