package menu;

import game.*;
import javax.swing.*;
import java.awt.*;

public class Menu extends JFrame {
    private JButton playBtn, quitBtn;
    
    public Menu() {
        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null); // Using absolute positioning
        setResizable(false);
        
        // Title Label: dynamically determine preferred size and center it
        JLabel mazeTitle = new JLabel("Maze Game", SwingConstants.CENTER);
        mazeTitle.setFont(new Font("Arial", Font.BOLD, 18));
        Dimension titleSize = mazeTitle.getPreferredSize();
        int titleX = (400 - titleSize.width) / 2;
        int titleY = 50;
        mazeTitle.setBounds(titleX, titleY, titleSize.width, titleSize.height);
        add(mazeTitle);
        
        // Buttons: define dimensions and spacing
        int btnWidth = 100, btnHeight = 40;
        int spacing = 20; 
        int totalWidth = (btnWidth * 2) + spacing; 
        int buttonsX = (400 - totalWidth) / 2;  // center the two buttons together
        int buttonsY = 200;
        
        // Play Button positioned at the calculated starting X
        playBtn = new JButton("Play");
        playBtn.setBounds(buttonsX, buttonsY, btnWidth, btnHeight);
        playBtn.addActionListener(e -> openLevelSelect());
        add(playBtn);
        
        // Quit Button placed immediately after Play Button with specified spacing
        quitBtn = new JButton("Quit");
        quitBtn.setBounds(buttonsX + btnWidth + spacing, buttonsY, btnWidth, btnHeight);
        quitBtn.addActionListener(e -> System.exit(0));
        add(quitBtn);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void openLevelSelect() {
        ModeSelection level = new ModeSelection();
        level.setVisible(true);
        this.dispose();
    }
    
    public static void main(String[] args) {
        new Menu();
    }
}