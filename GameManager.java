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
        menuPanel = new MenuPanel(this);
        window.setContentPane(menuPanel);
        window.pack();
        window.setSize(1280, 720);
    }

    public void startGame() {
        gamePanel = new GamePanel(this); // Pass GameManager to GamePanel
        window.setContentPane(gamePanel);
        window.pack();
        window.setSize(1280, 720);

        gamePanel.requestFocusInWindow();
        gamePanel.startGameThread();
    }

    public void showDayScreen() {
        DayPanel dayPanel = new DayPanel(this);
        window.setContentPane(dayPanel);
        window.pack();
        window.setSize(1280, 720);
    }

    public void showSettings() {
        SettingsPanel settingsPanel = new SettingsPanel(this);
        window.setContentPane(settingsPanel);
        window.pack();
        window.setSize(1280, 720);
    }
}
