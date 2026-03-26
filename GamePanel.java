import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Thread gameThread;
    Player player;
    ArrayList<Platform> platforms;

    public GamePanel() {
        player = new Player(100, 300);

        platforms = new ArrayList<>();
        platforms.add(new Platform(0, 500, 800, 50));
        platforms.add(new Platform(200, 400, 100, 20));
        platforms.add(new Platform(400, 350, 100, 20));
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {
        while (true) {
            update();
            repaint();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update(platforms);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        player.draw(g);

        for (Platform p : platforms) {
            p.draw(g);
        }
    }
}