package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuPanel extends JPanel {
    private GameManager gm;
    private Image menuBackground;
    private Image playButtonImage;
    private Image settingsButtonImage;

    private Rectangle playButtonBounds;
    private Rectangle settingsButtonBounds;

    public MenuPanel(GameManager gm) {
        this.gm = gm;

        try {
            menuBackground = new ImageIcon(getClass().getResource("/mypackage/assets/kitchenmaster_logo.png")).getImage();
            playButtonImage = new ImageIcon(getClass().getResource("/mypackage/assets/play_btn.png")).getImage();
            settingsButtonImage = new ImageIcon(getClass().getResource("/mypackage/assets/settings.png")).getImage();
        } catch (Exception e) {
            System.out.println("⚠️ Image not found: " + e.getMessage());
        }

        // Mouse listener for clicks
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (playButtonBounds != null && playButtonBounds.contains(p)) {
                    System.out.println("▶️ Play button clicked!");
                    gm.startGame(); // ✅ switch to GamePanel
                } else if (settingsButtonBounds != null && settingsButtonBounds.contains(p)) {
                    System.out.println("⚙️ Settings button clicked!");
                    // gm.showSettings(); // You can add later
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Background (menu design)
        if (menuBackground != null) {
            g2.drawImage(menuBackground, 0, 0, panelWidth, panelHeight, null);
        }

     // Play button
        if (playButtonImage != null) {
            int playBtnWidth = 215;
            int playBtnHeight = 130;

            // Base position (centered)
            int playBtnX = (panelWidth - playBtnWidth) / 2;
            int playBtnY = (int) (panelHeight * 0.6);

            // ✅ Adjust position with offsets:
            playBtnX -= 30; // move left (increase to go further left)
            playBtnY += 40; // move down (increase to go further down)

            // Draw image
            g2.drawImage(playButtonImage, playBtnX, playBtnY, playBtnWidth, playBtnHeight, null);

            // Update clickable area
            playButtonBounds = new Rectangle(playBtnX, playBtnY, playBtnWidth, playBtnHeight);
        }


     // Settings button
        if (settingsButtonImage != null) {
            int settingsSize = 135;

            // Base position (top-right corner)
            int settingsX = panelWidth - settingsSize - 30;
            int settingsY = 30;

            // ✅ Adjust offsets here:
            settingsX -= -10; // move left (increase to go further left)
            settingsY += 5; // move down (increase to go further down)

            // Draw the button
            g2.drawImage(settingsButtonImage, settingsX, settingsY, settingsSize, settingsSize, null);

            // Update clickable bounds
            settingsButtonBounds = new Rectangle(settingsX, settingsY, settingsSize, settingsSize);
        }

    }
}
