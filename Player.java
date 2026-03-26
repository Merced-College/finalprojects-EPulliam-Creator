import java.awt.*;
import java.util.ArrayList;

public class Player {
    int x, y;
    int width = 40, height = 40;

    double velocityY = 0;
    boolean onGround = false;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update1(ArrayList<Platform> platforms) {
        velocityY += 0.5; // gravity
        y += velocityY;

        onGround = false;

        for (Platform p : platforms) {
            if (getBounds().intersects(p.getBounds())) {
                y = p.y - height;
                velocityY = 0;
                onGround = true;
            }
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = -10;
        }
    }

    public void moveLeft() {
        x -= 5;
    }

    public void moveRight() {
        x += 5;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }

	public void update(ArrayList<Platform> platforms) {
		// TODO Auto-generated method stub
		
	}
}