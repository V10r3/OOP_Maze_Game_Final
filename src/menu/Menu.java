package menu;
import game.*;
import javax.swing.*;

public class Menu extends JFrame {
    private JButton playBtn, quitBtn;
    
    public Menu() {
        setTitle("Maze Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null);
        setResizable(false);
        
        JLabel mazeTitle = new JLabel("Maze Game", SwingConstants.CENTER);
        mazeTitle.setBounds(150, 50, 100, 30);
        add(mazeTitle);

        playBtn = new JButton("Play");
        playBtn.setBounds(110, 240, 80, 30);
        playBtn.addActionListener(e -> openLevelSelect());
        add(playBtn);

        quitBtn = new JButton("Quit");
        quitBtn.setBounds(210, 240, 80, 30);
        quitBtn.addActionListener(e -> System.exit(0));
        add(quitBtn);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openLevelSelect() {
    Level level = new Level(); // ✅ Creates the new Level instance
    level.setVisible(true);    // ✅ Ensures Level appears
    this.dispose();            // ✅ Closes the menu
}

    public static void main(String[] args) {
        new Menu(); // Start the menu
    }
}