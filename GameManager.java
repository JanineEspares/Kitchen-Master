package mypackage;

import javax.swing.*;

public class GameManager {
    private JFrame window;
    private MenuPanel menuPanel;
    private GamePanel gamePanel;

    public GameManager() {
        window = new JFrame("Kitchen Master");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
 
        showMenu();

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public void showMenu() {
        menuPanel = new MenuPanel(this); // ✅ Pass GameManager to MenuPanel
        window.setContentPane(menuPanel);
        window.pack();
        window.setSize(1280, 720); // you can adjust this
    }

    public void startGame() {
        gamePanel = new GamePanel();
        window.setContentPane(gamePanel);
        window.pack();
        window.setSize(1280, 720);
        gamePanel.startGameThread();
    }
}
