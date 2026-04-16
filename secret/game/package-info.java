package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    // Screen dimensions and target FPS
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int FPS = 60;

    private Thread gameThread;
    private boolean running = false; // controls game loop

    private Player player;
    private ArrayList<Surface> surfaces;

    public GamePanel() {

        // Define size of the drawing area
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Background color of the game
        setBackground(Color.BLACK);

        // Enables smoother rendering (reduces flickering)
        setDoubleBuffered(true);

        // Allows this panel to receive keyboard input
        setFocusable(true);

        initGame();
        setupKeyBindings();
    }

    private void initGame() {

        // Create player at starting position
        player = new Player(100, 300);

        surfaces = new ArrayList<>();

        // Flat ground
        surfaces.add(new Surface(0, 500, 800, 500));

        // Slopes for testing physics
        surfaces.add(new Surface(200, 500, 400, 400));
        surfaces.add(new Surface(400, 400, 600, 500));
    }

    private void setupKeyBindings() {

        // Key bindings work even if focus shifts slightly (better than KeyListener)
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // LEFT key press/release
        im.put(KeyStroke.getKeyStroke("pressed LEFT"), "leftPress");
        im.put(KeyStroke.getKeyStroke("released LEFT"), "leftRelease");

        am.put("leftPress", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setLeft(true); // start accelerating left
            }
        });

        am.put("leftRelease", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setLeft(false); // stop accelerating left
            }
        });

        // RIGHT key press/release
        im.put(KeyStroke.getKeyStroke("pressed RIGHT"), "rightPress");
        im.put(KeyStroke.getKeyStroke("released RIGHT"), "rightRelease");

        am.put("rightPress", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setRight(true); // start accelerating right
            }
        });

        am.put("rightRelease", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.setRight(false); // stop accelerating right
            }
        });

        // Jump key
        im.put(KeyStroke.getKeyStroke("pressed SPACE"), "jump");

        am.put("jump", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                player.jump(); // trigger jump
            }
        });
    }

    public void startGame() {
        running = true;

        // Start game loop on a separate thread
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        // Time per frame (in nanoseconds)
        double drawInterval = 1_000_000_000.0 / FPS;

        double delta = 0;
        long lastTime = System.nanoTime();

        while (running) {

            long currentTime = System.nanoTime();

            // Track how much time has passed relative to frame target
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            // Only update when enough time has passed (locks to FPS)
            if (delta >= 1) {
                update();
                repaint(); // triggers paintComponent
                delta--;
            }
        }
    }

    private void update() {
        // Update player physics and collisions
        player.update(surfaces);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Draw player
        player.draw(g2);

        // Draw all terrain surfaces
        for (Surface s : surfaces) {
            s.draw(g2);
        }

        // Clean up graphics context
        g2.dispose();
    }
}


