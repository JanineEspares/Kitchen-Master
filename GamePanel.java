	package mypackage;
	
	import javax.swing.JPanel;
	import javax.imageio.ImageIO;
	import java.awt.*;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
	import java.awt.event.MouseEvent;
	import java.awt.event.MouseListener;
	import java.awt.image.BufferedImage;
	import java.io.IOException;
	
	public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
	
	    // SCREEN SETTINGS
	    final int SCREEN_WIDTH = 1260;
	    final int SCREEN_HEIGHT = 600;
	
	    // GAME LOOP
	    Thread gameThread;
	    boolean running = false;
	    final int FPS = 60;
	
	    // BACKGROUNDS
	    private BufferedImage frontBg;
	    private BufferedImage kitchenBg;
	
	    // Station state
	    private enum Station { FRONT, KITCHEN }
	    private Station currentStation = Station.FRONT;
	
	    public GamePanel() {
	        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
	        this.setBackground(Color.BLACK);
	        this.setDoubleBuffered(true);
	        this.setFocusable(true);
	        this.addKeyListener(this);
	        this.addMouseListener(this);
	
	        // Load Background Images
	        try {
	            frontBg = ImageIO.read(getClass().getResourceAsStream("/mypackage/assets/restaurant_front.png"));
	            kitchenBg = ImageIO.read(getClass().getResourceAsStream("/mypackage/assets/restaurant_back.png"));
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
	                update();
	                repaint();
	                delta--;
	                frameCount++;
	            }
	
	            if (timer >= 1000000000) {
	                System.out.println("FPS: " + frameCount + " | Station: " + currentStation);
	                frameCount = 0;
	                timer = 0;
	            }
	        }
	    }
	
	    public void update() {
	        // Game logic (timers, orders, cooking progress, etc.)
	        // We’ll fill this in once we add customers and cooking stations
	    }
	
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	
	        // Draw background depending on station
	        if (currentStation == Station.FRONT) {
	            if (frontBg != null)
	                g2.drawImage(frontBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
	        } else {
	            if (kitchenBg != null)
	                g2.drawImage(kitchenBg, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, null);
	        }
	
	        // You can later draw UI buttons, cooking tools, or customers here
	
	        g2.dispose();
	    }
	
	    // --- INPUT HANDLERS ---
	    @Override
	    public void keyPressed(KeyEvent e) {
	        int code = e.getKeyCode();
	
	        // Switch station using spacebar
	        if (code == KeyEvent.VK_SPACE) {
	            if (currentStation == Station.FRONT)
	                currentStation = Station.KITCHEN;
	            else
	                currentStation = Station.FRONT;
	
	            System.out.println("Switched to " + currentStation + " view.");
	        }
	    }
	
	    @Override
	    public void keyReleased(KeyEvent e) {}
	
	    @Override
	    public void keyTyped(KeyEvent e) {}
	
	    // --- MOUSE HANDLING ---
	    @Override
	    public void mouseClicked(MouseEvent e) {
	        int x = e.getX();
	        int y = e.getY();
	
	        System.out.println("Mouse clicked at: " + x + ", " + y);
	
	        // Example: later we can check if player clicked a grill, soda machine, etc.
	        // if (currentStation == Station.KITCHEN && grillArea.contains(x, y)) { startCookingBurger(); }
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
