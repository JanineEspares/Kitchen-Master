package mypackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class StoryboardPanel extends JPanel {
    private List<Story> stories;
    private int currentIndex = 0;
    private Image characterImage;
    private GameManager gm;
    private JButton nextBtn;

    public StoryboardPanel(GameManager gm, List<Story> stories) {
        this.gm = gm;
        this.stories = stories;
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));

        loadCharacterImage();

        // Next button
        nextBtn = new JButton("Next");
        nextBtn.setBounds(1080, 620, 150, 50);
        add(nextBtn);

        nextBtn.addActionListener(e -> nextStory());
    }

    private void loadCharacterImage() {
        try {
            characterImage = new ImageIcon(
                getClass().getResource(stories.get(currentIndex).getImagePath())
            ).getImage();
        } catch (Exception e) {
            characterImage = null;
            System.out.println("⚠️ Character image not found!");
        }
    }

    private void nextStory() {
        currentIndex++;
        if (currentIndex >= stories.size()) {
            gm.showDayPanel(); // Switch to DayPanel
        } else {
            loadCharacterImage();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Background
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw character image
        if (characterImage != null) {
            g2.drawImage(characterImage, 50, 150, 400, 500, null);
        }

        // Draw dialogue box
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(500, 500, 700, 150, 20, 20);

        // Draw text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
        Story current = stories.get(currentIndex);
        g2.drawString(current.getCharacterName() + ":", 520, 540);
        g2.drawString(current.getText(), 520, 580);
    }
}
