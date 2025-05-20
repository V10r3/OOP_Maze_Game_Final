package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChillModeSelection extends JFrame {
    public ChillModeSelection() {
        setTitle("Chill Mode – Select Maze Size");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        setResizable(false);
        // Panel for maze size options
        JPanel optionsPanel = new JPanel(new GridLayout(3, 1));
        JRadioButton smallBtn = new JRadioButton("Small Maze");
        JRadioButton mediumBtn = new JRadioButton("Medium Maze");
        JRadioButton largeBtn = new JRadioButton("Large Maze");

        ButtonGroup group = new ButtonGroup();
        group.add(smallBtn);
        group.add(mediumBtn);
        group.add(largeBtn);
        mediumBtn.setSelected(true);

        optionsPanel.add(smallBtn);
        optionsPanel.add(mediumBtn);
        optionsPanel.add(largeBtn);
        add(optionsPanel, BorderLayout.CENTER);

        // Panel for action buttons (Start and Back)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton startBtn = new JButton("Start Game");
        JButton backBtn = new JButton("Back");

        startBtn.addActionListener(e -> {
            int mazeSize;
            if (smallBtn.isSelected()) mazeSize = 21;
            else if (mediumBtn.isSelected()) mazeSize = 41;
            else mazeSize = 101;
            new ChillLevel(mazeSize);  // Launch chill mode with selected maze size
            dispose();
        });
        backBtn.addActionListener(e -> {
            new ModeSelection(); // Go back to ModeSelection
            dispose();
        });
        buttonPanel.add(startBtn);
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    
    // For testing ChillModeSelection independently.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ChillModeSelection());
    }
}