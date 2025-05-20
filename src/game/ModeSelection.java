package game;

import menu.Menu;  // Importing the Menu from its package
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModeSelection extends JFrame {
    public ModeSelection() {
        setTitle("Select Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        // Use a 3-row GridLayout: Timed Mode, Chill Mode, and Back.
        setLayout(new GridLayout(3, 1, 10, 10));
        setResizable(false);
        JButton timedButton = new JButton("Timed Mode");
        JButton chillButton = new JButton("Chill Mode");
        JButton backButton = new JButton("Back");

        timedButton.addActionListener(e -> {
            new TimedLevel(); // Launch timed mode
            dispose();
        });
        chillButton.addActionListener(e -> {
            new ChillModeSelection(); // Launch maze size selection for chill mode
            dispose();
        });
        backButton.addActionListener(e -> {
            new Menu();  // Return to main menu
            dispose();
        });

        add(timedButton);
        add(chillButton);
        add(backButton);
        
        setVisible(true);
    }
    
    // For testing mode selection independently.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ModeSelection());
    }
}