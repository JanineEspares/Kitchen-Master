package mypackage;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {

    // SCREEN SETTINGS
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = 700;

    // GAME LOOP
    Thread gameThread;
    boolean running = false;
    final int FPS = 60;

    // BACKGROUNDS
    private BufferedImage frontBg;
    private BufferedImage kitchenBg;
    private BufferedImage insideKitchenBg;

    // Station state
    private enum Station {
        FRONT,
        KITCHEN,
        INSIDE_KITCHEN
    }
    private Station currentStation = Station.FRONT;

    // Pause functionality
    private boolean paused = false;
    private GameManager gm;

    public GamePanel(GameManager gm) {
        this.gm = gm;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.addKeyListener(this);
        this.addMouseListener(this);

        // Load Background Images
        try {
            frontBg = ImageIO.read(getClass().getResourceAsStream("/mypackage/asset/restaurant_front.jpg"));
            kitchenBg = ImageIO.read(getClass().getResourceAsStream("/mypackage/asset/restaurant_back.jpg"));
            insideKitchenBg = ImageIO.read(getClass().getResourceAsStream("/mypackage/asset/kitchen_inside.jpg"));
            System.out.println("✅ Backgrounds loaded successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error loading backgrounds:");
            e.printStackTrace();
        }
    }

    public void startGameThread() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frameCount = 0;

        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                if (!paused) {
                    update();
                }
                repaint();
                delta--;
                frameCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + frameCount + " | Station: " + currentStation + (paused ? " (Paused)" : ""));
                frameCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        // Game logic only runs when not paused
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Draw background depending on station
        switch (currentStation) {
            case FRONT:
                if (frontBg != null) g2.drawImage(frontBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
                break;
            case KITCHEN:
                if (kitchenBg != null) g2.drawImage(kitchenBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
                break;
            case INSIDE_KITCHEN:
                if (insideKitchenBg != null)
                    g2.drawImage(insideKitchenBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
                else {
                    g2.setColor(Color.GRAY);
                    g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Trebuchet MS", Font.BOLD, 48));
                    g2.drawString("Inside Kitchen View", 400, 350);
                }
                break;
        }

        // Overlay when paused
        if (paused) {
            g2.setColor(new Color(0, 0, 0, 160));
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
            g2.drawString("⏸ GAME PAUSED", SCREEN_WIDTH / 2 - 180, SCREEN_HEIGHT / 2 - 20);

            g2.setFont(new Font("Trebuchet MS", Font.PLAIN, 24));
            g2.drawString("Press P to Resume", SCREEN_WIDTH / 2 - 110, SCREEN_HEIGHT / 2 + 30);
            g2.drawString("Press H to Return to Menu", SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2 + 70);
        }

        g2.dispose();
    }

    // --- INPUT HANDLERS ---
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // ✅ Press P to toggle pause/resume
        if (code == KeyEvent.VK_P) {
            paused = !paused;
            System.out.println(paused ? "Game Paused" : "Game Resumed");
            repaint();
            return;
        }

        // ✅ Press H to return to menu (only works when paused)
        if (paused && code == KeyEvent.VK_H) {
            System.out.println("Returning to Main Menu...");
            paused = false;
            running = false; // stop the game loop
            gm.showMenu();   // go back to menu screen
            return;
        }

        // Ignore other input when paused
        if (paused) return;

        // Station switching logic
        if (code == KeyEvent.VK_SPACE) {
            if (currentStation == Station.FRONT)
                currentStation = Station.KITCHEN;
            else if (currentStation == Station.KITCHEN)
                currentStation = Station.FRONT;
            System.out.println("Switched to " + currentStation + " view.");
        } else if (code == KeyEvent.VK_ENTER) {
            if (currentStation == Station.KITCHEN) {
                currentStation = Station.INSIDE_KITCHEN;
                System.out.println("Entered INSIDE_KITCHEN view.");
            }
        } else if (code == KeyEvent.VK_BACK_SPACE) {
            if (currentStation == Station.INSIDE_KITCHEN) {
                currentStation = Station.KITCHEN;
                System.out.println("Returned to KITCHEN view.");
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if (paused) return; // disable mouse actions when paused
        int x = e.getX();
        int y = e.getY();
        System.out.println("Mouse clicked at: " + x + ", " + y);
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
