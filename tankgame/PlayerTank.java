package tankgame;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class PlayerTank extends CollidableObjects {

    private final int id;       // Player 1 or Player 2
    private int speed;          // Rotation and Movement speed
    private Sounds rev;         // Sounds the engine makes when a player moves forward or backward
    private int tick = 50;      // Tick to control how often the engine is allowed to sound to prevent distortion
    private int bulletTick;     // Tick for how fast a tank can shoot
    private int healthCount;    // Counter to keep track of tank's health

    public PlayerTank(double myX, double myY, double direction, int id) {
        healthCount = 1;        // Full health is set to 1
        bulletTick = 0;
        this.id = id;
        image = loadImage("Resources/tank" + this.id + "-0.png");
        this.myX = myX;
        this.myY = myY;
        speed = 2;              // Speed is initially set to 2, doubles if powerup active
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        this.direction = direction;
        collision = new Rectangle2D.Double(myX, myY + (image.getHeight() * .025), (image.getWidth()), (image.getHeight()) * .95);
    }

    @Override // For moving both tanks, ** COULD BE IMPROVED ** so that it is generic for either tank
    public void move(KeyControl x) {
        // Movement for player 1, 
        if (id == 1) {
                // **** Moving tank forward and backward ****
                // Update x and y location
                // Update the center location
                // Get the transformation
                // Create the new shape from the transformation
            if (x.getF()) { // Moving player 1 forward
                // Tick for playing tank engine
                if (tick > 50) {
                    rev = new Sounds("Resources/tankrev1.wav");
                    rev.playonce();
                    tick = 0;
                }
                myX = myX + speed * Math.cos(Math.toRadians(direction));
                myY = myY + speed * Math.sin(Math.toRadians(direction));
                centerX = centerX + speed * Math.cos(Math.toRadians(direction));
                centerY = centerY + speed * Math.sin(Math.toRadians(direction));
                movement = AffineTransform.getTranslateInstance(speed * Math.cos(Math.toRadians(direction)), speed * Math.sin(Math.toRadians(direction)));
                collision = movement.createTransformedShape(collision);
            }
            if (x.getB()) { // Moving player 1 backward
                // Tick for playing tank engine
                if (tick > 50) {
                    rev = new Sounds("Resources/tankrev1.wav");
                    rev.playonce();
                    tick = 0;
                }
                myX = myX - speed * Math.cos(Math.toRadians(direction));
                myY = myY - speed * Math.sin(Math.toRadians(direction));
                centerX = centerX - speed * Math.cos(Math.toRadians(direction));
                centerY = centerY - speed * Math.sin(Math.toRadians(direction));
                movement = AffineTransform.getTranslateInstance(-speed * Math.cos(Math.toRadians(direction)), -speed * Math.sin(Math.toRadians(direction)));
                collision = movement.createTransformedShape(collision);
            }
                // **** Rotating the tank left or right ****
                // Update direction the tank is facing
                // Get the transformation
                // Create the new shape from the transformation
            if (x.getL()) { // Roating the tank left
                direction = direction - speed;
                if (direction < -360) { // If it becomes less than -360 set it back to 0.
                    direction = direction + 360;
                }
                movement = AffineTransform.getRotateInstance(Math.toRadians(-speed), centerX, centerY);
                collision = movement.createTransformedShape(collision);
            }
            if (x.getR()) { // Rotating the tank right
                direction = direction + speed;
                if (direction > 360) { // If it becomes greater than 360 set it back to 0.
                    direction = direction - 360;
                }
                movement = AffineTransform.getRotateInstance(Math.toRadians(speed), centerX, centerY);
                collision = movement.createTransformedShape(collision);
            }
            tick++; // Increment (Tick) through every update in arena.
        }
        // Movement for player 2
        // **** Implementation commentary same as player 1 movement ****
        // **** See above player 1 commentary for any implementation questions ****
        if (id == 2) {
            if (x.getF2()) {
                if (tick > 50) {
                    rev = new Sounds("Resources/tankrev1.wav");
                    rev.playonce();
                    tick = 0;
                }
                myX = myX + speed * Math.cos(Math.toRadians(direction));
                myY = myY + speed * Math.sin(Math.toRadians(direction));
                centerX = centerX + speed * Math.cos(Math.toRadians(direction));
                centerY = centerY + speed * Math.sin(Math.toRadians(direction));
                movement = AffineTransform.getTranslateInstance(speed * Math.cos(Math.toRadians(direction)), speed * Math.sin(Math.toRadians(direction)));
                collision = movement.createTransformedShape(collision);
            }
            if (x.getB2()) {
                if (tick > 50) {
                    rev = new Sounds("Resources/tankrev1.wav");
                    rev.playonce();
                    tick = 0;
                }
                myX = myX - speed * Math.cos(Math.toRadians(direction));
                myY = myY - speed * Math.sin(Math.toRadians(direction));
                centerX = centerX - speed * Math.cos(Math.toRadians(direction));
                centerY = centerY - speed * Math.sin(Math.toRadians(direction));
                movement = AffineTransform.getTranslateInstance(-speed * Math.cos(Math.toRadians(direction)), -speed * Math.sin(Math.toRadians(direction)));
                collision = movement.createTransformedShape(collision);
            }
            if (x.getL2()) {
                direction = direction - speed;
                if (direction < -360) {
                    direction = direction + 360;
                }
                movement = AffineTransform.getRotateInstance(Math.toRadians(-speed), centerX, centerY);
                collision = movement.createTransformedShape(collision);
            }
            if (x.getR2()) {
                direction = direction + speed;
                if (direction > 360) {
                    direction = direction - 360;
                }
                movement = AffineTransform.getRotateInstance(Math.toRadians(speed), centerX, centerY);
                collision = movement.createTransformedShape(collision);
            }
            tick++;
        }
    }
    // Returns updated center of tank int the x direction
    public double getCenterX() {
        return centerX;
    }
    // Returns updated center of tank int the y direction
    public double getCenterY() {
        return centerY;
    }
    // Returns updated direction the tank is facing
    public double getDirection() {
        return direction;
    }
    // Returns updated x location
    public double getMyX() {
        return myX;
    }
    // Returns updated y location
    public double getMyY() {
        return myY;
    }
    // For accessing the shape used for collisions.
    public Shape getRec() {
        return collision;
    }
    // Returns id, player 1 or player 2
    public int getID() {
        return id;
    }
    // Used to powerup the tank 1.5 times faster
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    // For accessing how fast a tank can shoot
    public int getBulletTick() {
        return bulletTick;
    }
    // Used to powerup the rate at which bullets are fired 2 times faster 
    public void setBulletTick(int bulletTick) {
        this.bulletTick = bulletTick;
    }
    // Returns tanks current health
    public int getHealth() {
        return healthCount;
    }
    // Used to powerup the tank back to full health
    public void setHealth(int healthCount) {
        this.healthCount = healthCount;
    }
}
