package tankgame;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;

public class PowerUp extends CollidableObjects {

    private final int id; // Only needed for setting each powerups id initially.

    public PowerUp(double myX, double myY) {
        double randomNum = Math.random(); // Set a random value between 0 and 1.
        this.myX = myX;
        this.myY = myY;
        // 33 percent chance between 3 different powerups.
        if (randomNum < .33) {
            id = 1;
            image = loadImage("Resources/powerup1.png");
        } else if (randomNum >= .33 && randomNum < .66) {
            id = 2;
            image = loadImage("Resources/powerup2.png");
        } else {
            id = 3;
            image = loadImage("Resources/powerup3.png");
        }
        collision = new Rectangle2D.Double(myX, myY, image.getWidth(), image.getHeight());
    }

    @Override
    public void move(KeyControl x) {
    }
    // For accessing the shape used for collisions.
    public Shape getRec() {
        return collision;
    }
    // For accessing the powerups id and giving meaning to how the tanks are affected.
    public int getId() {
        return id;
    }
}
