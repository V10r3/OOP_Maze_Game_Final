//package menu;
//
//import game.SmallLevel;
//import game.BigLevel;
//import javax.swing.*;
//
//public class LevelSelect extends JFrame {
//    private JButton bigLvl, smallLvl;
//    
//    public LevelSelect() {
//        setTitle("Select Level");
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(500, 500);
//        setLayout(null);
//        setResizable(false);
//
//        JPanel panel = new JPanel();
//        panel.setLayout(null);
//        panel.setBounds(0, 0, 400, 300);
//        add(panel);
//
//        smallLvl = new JButton("10 x 10");
//        smallLvl.setBounds(70, 10, 250, 140);
//        smallLvl.addActionListener(e -> startSmallLevel());
//        panel.add(smallLvl);
//
//        bigLvl = new JButton("20 x 20");
//        bigLvl.setBounds(70, 150, 250, 140);
//        bigLvl.addActionListener(e -> startBigLevel());
//        panel.add(bigLvl);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//
//    private void startSmallLevel() {
//        new SmallLevel().setVisible(true);
//        this.dispose();
//    }
//
//    private void startBigLevel() {
//        new BigLevel().setVisible(true);
//        this.dispose();
//    }
//
//    public static void main(String[] args) {
//        new LevelSelect();
//    }
//}