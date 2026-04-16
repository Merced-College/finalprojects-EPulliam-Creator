package game;

import java.awt.*;

public class Surface {

    private int x1, y1, x2, y2; // endpoints of the line

    public Surface(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public double getAngle() {
        // Calculates slope angle (used for movement direction)
        return Math.atan2(y2 - y1, x2 - x1);
    }

    public int getYAt(int x) {
        // Linear interpolation to find surface height at a given X
        double t = (double)(x - x1) / (x2 - x1);
        return (int)(y1 + t * (y2 - y1));
    }

    public boolean isUnderPlayer(int px) {
        // Checks if player is horizontally within this surface
        return px >= Math.min(x1, x2) && px <= Math.max(x1, x2);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.drawLine(x1, y1, x2, y2);
    }
}
