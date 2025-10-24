package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

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
            menuBackground = new ImageIcon(getClass().getResource("/mypackage/asset/logo.jpg")).getImage();
            playButtonImage = new ImageIcon(getClass().getResource("/mypackage/asset/play_btn.jpg")).getImage();
            settingsButtonImage = new ImageIcon(getClass().getResource("/mypackage/asset/settings.jpg")).getImage();
        } catch (Exception e) {
            System.out.println("⚠️ Image not found: " + e.getMessage());
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                if (playButtonBounds != null && playButtonBounds.contains(p)) {
                    System.out.println("▶️ Play button clicked!");

                    // ✅ New: Show storyboard before DayPanel
                    List<Story> story = List.of(
                        new Story("Owner", "Hi! I’m going on a short trip...", "/asset/owner.png"),
                        new Story("Owner", "I’m leaving the restaurant in your hands.", "/asset/owner.png"),
                        new Story("Owner", "Make sure to cook and serve customers well!", "/asset/owner.png")
                    );

                    gm.showStoryboard(story); // Goes to StoryboardPanel
                } else if (settingsButtonBounds != null && settingsButtonBounds.contains(p)) {
                    System.out.println("⚙️ Settings button clicked!");
                    showSettingsPopup();
                }
            }
        });
    }

    // ✅ Popup Settings Window (unchanged)
    private void showSettingsPopup() {
        JDialog settingsDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "⚙️ Settings", true);
        settingsDialog.setSize(400, 400);
        settingsDialog.setLayout(null);
        settingsDialog.setResizable(false);
        settingsDialog.setLocationRelativeTo(this);

        JLabel volumeLabel = new JLabel("Volume:");
        volumeLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 18));
        volumeLabel.setBounds(50, 40, 150, 40);
        settingsDialog.add(volumeLabel);

        JSlider volumeSlider = new JSlider(0, 100, 50);
        volumeSlider.setBounds(150, 40, 200, 40);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        settingsDialog.add(volumeSlider);

        JButton howToPlayBtn = new JButton("How to Play");
        howToPlayBtn.setBounds(100, 120, 200, 40);
        howToPlayBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        settingsDialog.add(howToPlayBtn);

        JButton aboutBtn = new JButton("About Us");
        aboutBtn.setBounds(100, 180, 200, 40);
        aboutBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        settingsDialog.add(aboutBtn);

        JButton exitBtn = new JButton("Close");
        exitBtn.setBounds(100, 260, 200, 40);
        exitBtn.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
        exitBtn.setBackground(new Color(255, 200, 150));
        settingsDialog.add(exitBtn);

        howToPlayBtn.addActionListener(e -> JOptionPane.showMessageDialog(
            settingsDialog,
            """
            🕹️ HOW TO PLAY:

            1. Serve customers quickly to earn money.
            2. Switch between the front and kitchen using SPACEBAR.
            3. Click on cooking stations to prepare food.
            4. Deliver orders when food is ready!
            """,
            "How to Play",
            JOptionPane.INFORMATION_MESSAGE
        ));

        aboutBtn.addActionListener(e -> JOptionPane.showMessageDialog(
            settingsDialog,
            """
            👨‍🍳 ABOUT US:

            Kitchen Master is a restaurant tycoon game
            developed by Gadget (#JavaGameDeveloper).

            Build your kitchen empire and become the ultimate chef!
            """,
            "About Us",
            JOptionPane.INFORMATION_MESSAGE
        ));

        exitBtn.addActionListener(e -> settingsDialog.dispose());
        settingsDialog.getContentPane().setBackground(new Color(255, 240, 210));
        settingsDialog.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (menuBackground != null) {
            g2.drawImage(menuBackground, 0, 0, panelWidth, panelHeight, null);
        }

        // Play button
        if (playButtonImage != null) {
            int playBtnWidth = 215;
            int playBtnHeight = 130;
            int playBtnX = (panelWidth - playBtnWidth) / 2 - 30;
            int playBtnY = (int) (panelHeight * 0.6) + 40;
            g2.drawImage(playButtonImage, playBtnX, playBtnY, playBtnWidth, playBtnHeight, null);
            playButtonBounds = new Rectangle(playBtnX, playBtnY, playBtnWidth, playBtnHeight);
        }

        // Settings button
        if (settingsButtonImage != null) {
            int settingsSize = 135;
            int settingsX = panelWidth - settingsSize - 30;
            int settingsY = 35;
            g2.drawImage(settingsButtonImage, settingsX, settingsY, settingsSize, settingsSize, null);
            settingsButtonBounds = new Rectangle(settingsX, settingsY, settingsSize, settingsSize);
        }
    }
}
