package tankgame;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

public class Wall extends CollidableObjects {

    public Wall(double myX, double myY, double direction, String name) {
        image = loadImage("Resources/" + name + ".png");
        this.myX = myX;
        this.myY = myY;
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        this.direction = direction;
        setXY(myX, myY);
        setTransform();
    }

    @Override
    public void move(KeyControl x) {
    }
    // Gets the center in the x direction of the object based on the images x dimension.
    public double getCenterX() {
        return image.getWidth() / 2;
    }
    // Gets the center in the y direction of the object based on the images y dimension.
    public double getCenterY() {
        return image.getHeight() / 2;
    }
    // For accessing the shape used for collisions.
    public Shape getRec() {
        return collision;
    }
    // For accessing the wall orientation.
    public double getDirection() {
        return direction;
    }
    // Used placement of the wall object.
    private void setXY(double xPosition, double yPosition) {
        this.myX = xPosition;
        this.myY = yPosition;
        centerX = myX + (image.getWidth() / 2);
        centerY = myY + (image.getHeight() / 2);
        collision = new Rectangle2D.Double(myX, myY, 2 * getCenterX(), 2 * getCenterY());
    }
    // Used to set the wall's orientation.
    private void setDirection(double direction) {
        this.direction = direction;
    }
    // Returns the x location.
    public double getX() {
        return myX;
    }
    // Returns the y location.
    public double getY() {
        return myY;
    }
    // Used to get create the shape object from the transformation.
    private void setTransform() {
        movement = AffineTransform.getRotateInstance(Math.toRadians(this.direction), this.centerX, this.centerY);
        collision = movement.createTransformedShape(collision);
    }
    // For placing the wall border encompassing the battle area.
    public static void borderPlacement(Wall[] x) {
        double scaleX, scaleY;
        for (int i = 0, j = 0, k = 0; i < x.length; i++) {
            // Create new generic wall object.
            x[i] = new Wall(0, 0, 0, "wall");
            // Scale the x and y location based on border dimesions.
            scaleX = 2 * x[i].getCenterX() * k;
            scaleY = 2 * x[i].getCenterX() * j;
            if (scaleX < 1200 && scaleY <= 0) { // Top border
                x[i].setXY(scaleX, scaleY);
                x[i].setDirection(0);
                x[i].setTransform();
                k++; // Add to scale the x location to place the next wall further right.
            } else if (scaleX >= 1200 && scaleY < 600) { // Right border
                x[i].setXY(scaleX - 80, scaleY);
                x[i].setDirection(90); // Rotate
                x[i].setTransform();
                j++; // Add to scale the y location further down.
            } else if (scaleY >= 600 && scaleX > 0) { // Bottom border
                x[i].setXY(scaleX - 100, scaleY - 40);
                x[i].setDirection(180); // Rotate
                x[i].setTransform();
                k--; // Subtract to scale the x location back from the right of the screen. 
            } else if (scaleY <= 650 && scaleX <= 0) { // Left border
                x[i].setXY(scaleX - 30, scaleY - 70);
                x[i].setDirection(270); // Rotate
                x[i].setTransform();
                j--; // Subtract to scale the y location up from the bottom of the screen.
            }
        }
    }
    // For placing the tank barriers and give defensive positions.
    public static void barrierPlacement(Wall[] x) {
        double scaleX, scaleY;
        for (int i = 0, k = 1; i < x.length; i++) {
            // Create the generic wall object.
            x[i] = new Wall(0, 0, 0, "tankbarrier");
            scaleX = 2 * x[i].getCenterX();
            scaleY = 2 * x[i].getCenterX() * k;
            if (i < 4) { // Top left tank barriers
                x[i].setXY(scaleX + 175, scaleY);
                x[i].setDirection(0);
                x[i].setTransform();
                k++; // Scale the y location down from the top.
            } else if (i >= 4 && i < 8) { // Bottom right tank barriers
                x[i].setXY(scaleX + 925, scaleY + 200);
                x[i].setDirection(180);
                x[i].setTransform();
                k++; // Scale the y location down from the center
            } else if (i >= 8 && i < 11) { // Bottom left tank barriers
                x[i].setXY(scaleX + 400, scaleY + 150);
                x[i].setDirection(0);
                x[i].setTransform();
                k--; // Scale the y location up from the bottom
            } else if (i >= 11 && i < 14) { // Top right tank barriers
                x[i].setXY(scaleX + 650, scaleY - 115);
                x[i].setDirection(180);
                x[i].setTransform();
                k--; // Scale the y location up from the center
            }
        }
    }
    // For placing the sandbags and put obstacles for tank movement.
    public static void sandbagPlacement(Wall[] x) {
        double scaleX, scaleY;
        for (int i = 0, j = 1, k = 1; i < x.length; i++) {
            x[i] = new Wall(0, 0, 0, "sandbag");
            scaleX = 2 * x[i].getCenterX() * j;
            scaleY = 2 * x[i].getCenterX() * k;
            if (i < 3) { // Left side sandbags parallel to side walls
                x[i].setXY(scaleX + 625, scaleY + 125);
                x[i].setDirection(90);
                x[i].setTransform();
                k++; // Scale the y location down from the center.
            } else if (i >= 3 && i < 6) { // Left side sandbags perpendicular to side walls
                x[i].setXY(scaleX + 640, scaleY + 108);
                x[i].setDirection(0);
                x[i].setTransform();
                j++; // Scale the x location from the left.
            } else if (i >= 6 && i < 9) { // Right side sandbags parallel to the side walls
                x[i].setXY(scaleX + 230, scaleY + 195);
                x[i].setDirection(270);
                x[i].setTransform();
                k--; // Scale the y location up from the bottom. 
            } else if (i >= 9 && i < 12) { // Right side sandbags perpendicular to the side walls
                x[i].setXY(scaleX + 215, scaleY + 212);
                x[i].setDirection(180);
                x[i].setTransform();
                j--; // Scale the x location from the right.
            }
        }
    }
}
