package game;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private double x, y;

    private double groundSpeed = 0;
    private double velocityY = 0;

    private boolean onGround = false;
    private double angle = 0;

    private final double ACCEL = 0.2;
    private final double FRICTION = 0.05;
    private final double GRAVITY = 0.5;
    private final double JUMP_FORCE = -10;
    private final double MAX_SPEED = 8;

    private boolean left, right;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(ArrayList<Surface> surfaces) {

        if (left) groundSpeed -= ACCEL;
        if (right) groundSpeed += ACCEL;

        if (groundSpeed > MAX_SPEED) groundSpeed = MAX_SPEED;
        if (groundSpeed < -MAX_SPEED) groundSpeed = -MAX_SPEED;

        if (!left && !right) {
            if (groundSpeed > 0) groundSpeed -= FRICTION;
            if (groundSpeed < 0) groundSpeed += FRICTION;
        }

        if (onGround) {
            x += groundSpeed * Math.cos(angle);
            y += groundSpeed * Math.sin(angle);
        } else {
            velocityY += GRAVITY;
            y += velocityY;
            x += groundSpeed;
        }

        onGround = false;

        for (Surface s : surfaces) {

            if (s.isUnderPlayer((int) x)) {

                int groundY = s.getYAt((int) x);

                if (y >= groundY - 40 && velocityY >= 0) {

                    y = groundY - 40;
                    velocityY = 0;
                    onGround = true;
                    angle = s.getAngle();
                }
            }
        }

        if (!onGround) {
            angle = 0;
        }

        // --- NEW: Screen boundaries ---
        double minX = 0;
        double maxX = 800 - 40;

        if (x < minX) {
            x = minX;
            groundSpeed = 0;
        }

        if (x > maxX) {
            x = maxX;
            groundSpeed = 0;
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = JUMP_FORCE;
            onGround = false;
        }
    }

    public void setLeft(boolean value) { left = value; }
    public void setRight(boolean value) { right = value; }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, 40, 40);
    }
}