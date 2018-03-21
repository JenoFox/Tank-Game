package tankgame;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Bullet extends CollidableObjects {

    private boolean isShot, isRotate; // Booleans to check whether the bullet object should move or rotate
    private final Sounds bulletshot;  // Sound created when a bullet is fired from the tank

    public Bullet(double myX, double myY, double direction) {
        image = loadImage("Resources/bullet2.png");
        bulletshot = new Sounds("Resources/bulletsound.wav");
        this.myX = myX - (image.getWidth() / 2) * Math.cos(Math.toRadians(direction));
        this.myY = myY - (image.getHeight() / 2) * Math.sin(Math.toRadians(direction));
        centerX = myX - (image.getWidth() / 2);
        centerY = myY - (image.getHeight() / 2);
        this.direction = direction; // Direction will be set to tanks direction.
        // Collision rectangle dimensions set extremely small for more accurate collision detection.
        collision = new Rectangle2D.Double(myX, myY, (image.getWidth() * .01), (image.getHeight() * .01));
        isShot = false;
        isRotate = false;
    }

    @Override
    public void move(KeyControl x) {
        // **** Bullet Movement ****
        // If bullet isShot
        // Play bullet sound
        // Move bullet
        // Get transformation of bullet moving
        // Create shape based on the transformation
        if (isShot) {  
            bulletshot.playonce();
            if (isRotate) {
                moveBullet();
            } else {
                movement = AffineTransform.getRotateInstance(Math.toRadians(direction), myX, myY);
                collision = movement.createTransformedShape(collision);
                moveBullet();
                isRotate = true;
            }
        }
    }
    // Used to move the bullet at a certain speed of 6
    public void moveBullet() {
        myX = myX + 6 * Math.cos(Math.toRadians(direction)); // Trig to move X distance the correct amount
        myY = myY + 6 * Math.sin(Math.toRadians(direction)); // Trig to move Y distance the correct amount
        // Get transformation based on the distance moved. Then create shape based on that.
        movement = AffineTransform.getTranslateInstance(6 * Math.cos(Math.toRadians(direction)), 6 * Math.sin(Math.toRadians(direction)));
        collision = movement.createTransformedShape(collision);
    }

    @Override // Overridden draw function to draw the bullet at correct location based on its datafields.
    protected void draw(Graphics2D g) { // Similar logic from inherited draw, myX and myY used for rotation instead of center
        g.setTransform(original);
        g.rotate(Math.toRadians(direction), myX, myY);
        g.drawImage(image, (int) myX, (int) myY, null);
        g.setTransform(original);
    }
    // Used to get the center of the bullets X location
    public double getCenterX() {
        return image.getWidth() / 2;
    }
    // Used to get the center of the bullets Y location
    public double getCenterY() {
        return image.getHeight() / 2;
    }
    // Used to get the X location
    public double getMyX() {
        return myX;
    }
    // Used to get the Y location
    public double getMyY() {
        return myY;
    }
    // Used to set when the bullet is shot
    public void setIsShot(boolean shot) {
        isShot = shot;
    }
    // For accessing the shape used for collisions
    public Shape getRec() {
        return collision;
    }
    // Used to get bullet direction
    public double getDirection() {
        return direction;
    }
}
