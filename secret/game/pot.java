package game;

import java.awt.*;
import java.util.ArrayList;

public class Player {

    private double x, y; // position

    private double groundSpeed = 0; // speed along ground
    private double velocityY = 0;   // vertical velocity (air)

    private boolean onGround = false;
    private double angle = 0; // current surface angle

    // Physics constants (control "feel" of movement)
    private final double ACCEL = 0.2;
    private final double FRICTION = 0.05;
    private final double GRAVITY = 0.5;
    private final double JUMP_FORCE = -10;
    private final double MAX_SPEED = 8;

    private boolean left, right; // input states

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void update(ArrayList<Surface> surfaces) {

        // Apply player input as acceleration
        if (left) groundSpeed -= ACCEL;
        if (right) groundSpeed += ACCEL;

        // Clamp speed so it doesn't grow infinitely
        if (groundSpeed > MAX_SPEED) groundSpeed = MAX_SPEED;
        if (groundSpeed < -MAX_SPEED) groundSpeed = -MAX_SPEED;

        // Apply friction when no input is given
        if (!left && !right) {
            if (groundSpeed > 0) groundSpeed -= FRICTION;
            if (groundSpeed < 0) groundSpeed += FRICTION;
        }

        if (onGround) {
            // Move along the slope direction using angle
            x += groundSpeed * Math.cos(angle);
            y += groundSpeed * Math.sin(angle);
        } else {
            // Apply gravity when in air
            velocityY += GRAVITY;

            y += velocityY;
            x += groundSpeed; // air retains horizontal momentum
        }

        // Assume not grounded until collision proves otherwise
        onGround = false;

        for (Surface s : surfaces) {

            if (s.isUnderPlayer((int) x)) {

                int groundY = s.getYAt((int) x);

                // Land on surface if falling onto it
                if (y >= groundY - 40 && velocityY >= 0) {

                    y = groundY - 40; // snap player to surface

                    velocityY = 0; // stop falling

                    onGround = true;

                    angle = s.getAngle(); // align with slope
                }
            }
        }

        // Reset angle if airborne
        if (!onGround) {
            angle = 0;
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = JUMP_FORCE; // apply upward force
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
