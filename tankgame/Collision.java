package tankgame;

import java.util.ArrayList;
// **** SHORT COMINGS ****
// Instead of checking which direction tanks are facing
// When checking collisions with walls and tanks,
// Would be much more efficient and cleaner to save 
// The tanks previous state and if there is a collision
// Revert back to the previous coordinates and repeat
// Whenever those types of collisions occur.
// The collisions with tanks can be buggy if players really try to get
// On top of eachother but can still attack eachother if overlap occurs.
public class Collision {

    private Sounds tb, wb; // Sounds for when a bullet collides with a tank and a bullet collides with a wall
    // **** Tank vs Wall ****
    // Main logic was to see which quadrant the tank was facing based on its direction
    // It would then halt the forward key or the backward key depending on which player it is
    // It also takes into account the tanks location, i.e. if it is to the right of a wall
    // And facing in the second or third quadrants, it would not be able to move forward anymore using haltF()
    // Use halt function associated with specific player
    public void playerVSwall(PlayerTank p1, Wall[] x, KeyControl keys) {
        for (int i = 0; i < x.length; i++) {
            // Player facing quadrant 1 or 4 and is to the left of a wall
            if ((p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getCenterX()
                    && (p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getMaxY()
                    && p1.getRec().getBounds2D().getCenterY() > x[i].getRec().getBounds2D().getMinY())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() >= 270 && p1.getDirection() <= 360))
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))) {
                if (p1.getID() == 1) {
                    keys.haltF();
                } else {
                    keys.haltF2();
                }
                break;
            }
            // Player facing quadrant 2 or 3 and is to the left of a wall
            else if ((p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getCenterX()
                    && (p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getMaxY()
                    && p1.getRec().getBounds2D().getCenterY() > x[i].getRec().getBounds2D().getMinY())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 90 && p1.getDirection() <= 180) || (p1.getDirection() <= 270 && p1.getDirection() >= 180))
                    || (p1.getDirection() <= -90 && p1.getDirection() >= -180) || (p1.getDirection() >= -270 && p1.getDirection() <= -180))) {
                if (p1.getID() == 1) {
                    keys.haltB();
                } else {
                    keys.haltB2();
                }
                break;
            } 
            // Player facing quadrant 3 or 4 and is above a wall
            else if ((p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getCenterY()
                    && (p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getCenterX() > x[i].getRec().getBounds2D().getMinX())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 0 && p1.getDirection() <= 90) || (p1.getDirection() >= 90 && p1.getDirection() <= 180))
                    || (p1.getDirection() >= -270 && p1.getDirection() <= -180) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))) {
                if (p1.getID() == 1) {
                    keys.haltF();
                } else {
                    keys.haltF2();
                }
                break;
            } 
            // Player facing quadrant 1 or 2 and is above a wall
            else if ((p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getCenterY()
                    && (p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getCenterX() > x[i].getRec().getBounds2D().getMinX())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 180 && p1.getDirection() <= 270) || (p1.getDirection() >= 270 && p1.getDirection() <= 360))
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -90 && p1.getDirection() >= -180))) {
                if (p1.getID() == 1) {
                    keys.haltB();
                } else {
                    keys.haltB2();
                }
                break;
            } 
            // Player facing quadrant 2 or 3 and is to the right of a wall
            else if ((p1.getRec().getBounds2D().getCenterX() >= x[i].getRec().getBounds2D().getCenterX()
                    && (p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getMaxY()
                    && p1.getRec().getBounds2D().getCenterY() > x[i].getRec().getBounds2D().getMinY())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 90 && p1.getDirection() <= 180) || (p1.getDirection() <= 270 && p1.getDirection() >= 180))
                    || (p1.getDirection() <= -90 && p1.getDirection() >= -180) || (p1.getDirection() >= -270 && p1.getDirection() <= -180))) {
                if (p1.getID() == 1) {
                    keys.haltF();
                } else {
                    keys.haltF2();
                }
                break;
            } 
            // Player facing quadrant 1 or 2 and is to the right of a wall
            else if ((p1.getRec().getBounds2D().getCenterX() >= x[i].getRec().getBounds2D().getCenterX()
                    && (p1.getRec().getBounds2D().getCenterY() <= x[i].getRec().getBounds2D().getMaxY()
                    && p1.getRec().getBounds2D().getCenterY() > x[i].getRec().getBounds2D().getMinY())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() >= 270 && p1.getDirection() <= 360))
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))) {
                if (p1.getID() == 1) {
                    keys.haltB();
                } else {
                    keys.haltB2();
                }
                break;
            } 
            // Player is facing quadrant 3 or 4 and is below a wall
            else if ((p1.getRec().getBounds2D().getCenterY() >= x[i].getRec().getBounds2D().getCenterY()
                    && (p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getCenterX() > x[i].getRec().getBounds2D().getMinX())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 180 && p1.getDirection() <= 270) || (p1.getDirection() >= 270 && p1.getDirection() <= 360))
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -90 && p1.getDirection() >= -180))) {
                if (p1.getID() == 1) {
                    keys.haltF();
                } else {
                    keys.haltF2();
                }
                break;
            } 
            // Player is facing quadrant 1 or 2 and is below a wall 
            else if ((p1.getRec().getBounds2D().getCenterY() >= x[i].getRec().getBounds2D().getCenterY()
                    && (p1.getRec().getBounds2D().getCenterX() <= x[i].getRec().getBounds2D().getMaxX()
                    && p1.getRec().getBounds2D().getCenterX() > x[i].getRec().getBounds2D().getMinX())
                    && p1.getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) && (((p1.getDirection() >= 0 && p1.getDirection() <= 90) || (p1.getDirection() >= 90 && p1.getDirection() <= 180))
                    || (p1.getDirection() >= -270 && p1.getDirection() <= -180) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))) {
                if (p1.getID() == 1) {
                    keys.haltB();
                } else {
                    keys.haltB2();
                }
                break;
            }
        }
    }
    // **** Bullet vs Wall ****
    // If a bullet rectangle intersects a wall rectangle
    // Create a new bullet vs wall sound
    // Add a new explosion to the explosion arraylist in arena
    // Remove the bullet and decrement bullet arraylist to properly check next bullet
    public void bulletVSwall(ArrayList<Bullet> bullets, Wall[] x, KeyControl keys, ArrayList<Explosion> y) {
        for (int i = 0; i < x.length; i++) {
            for (int j = 0; j < bullets.size(); j++) {
                if (bullets.get(j).getRec().getBounds2D().intersects(x[i].getRec().getBounds2D())) {
                    wb = new Sounds("Resources/wallbreak.wav");
                    wb.playonce();
                    y.add(new Explosion(bullets.get(j).getMyX(), bullets.get(j).getMyY(), 50, 50, "explosion2_"));
                    bullets.remove(j);
                    j--;
                }
            }
        }
    }
    // **** Bullet vs Player ****
    // If a bullet rectangle intersects a player rectangle
    // Create new bullet vs tank sound
    // Add a new explosion to the explosion arraylist in arena
    // Remove the bullet and decrement bullet arraylist to properly check next bullet
    // Returns boolean to update items within arena when necessary
    // As discussed earlier I would change implementation if I were to release an update of the game.
    public boolean bulletVplayer(PlayerTank p1, ArrayList<Bullet> bullets, KeyControl keys, ArrayList<Explosion> x) {
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).getRec().getBounds2D().intersects(p1.getRec().getBounds2D())) {
                tb = new Sounds("Resources/tankhit.wav");
                tb.playonce();
                x.add(new Explosion(bullets.get(i).getMyX(), bullets.get(i).getMyY(), 50, 50, "explosion1_"));
                bullets.remove(i);
                i--;
                return true;
            }
        }
        return false;
    }
    // **** Player vs Powerup ****
    // If player rectangle intersects powerup rectangle
    // Check which type of powerup it is
    // Powerup the tank according to powerup type
    // Remove powerup and decrement powerup arraylist to properly check next powerup
    public void playerVpowerup(PlayerTank p1, ArrayList<PowerUp> x, KeyControl keys) {
        for (int i = 0; i < x.size(); i++) {
            if (p1.getRec().getBounds2D().intersects(x.get(i).getRec().getBounds2D())) {
                if (x.get(i).getId() == 1) {
                    p1.setSpeed(3);
                } else if (x.get(i).getId() == 2) {
                    p1.setBulletTick(30);
                } else {
                    p1.setHealth(1);
                }
                x.remove(i);
                i--;
            }
        }
    }
    // **** Player vs Player ****
    // Discussed in short comings listed above
    // Used similar strategy as player vs wall
    // Check which direction both players are facing.
    // If both are facing opposite directions, either halt forward or backward 
    // Depending on which player is either to the right, to the left, above, or below.
    // If both players are facing the same direction, halt the players forward who is behind
    // And halt the other players backward movement since it is in front.
    // Rotation is also halted to try and prevent overlap if they are not facing
    // The same direction.
    public void playerVSplayer(PlayerTank p1, PlayerTank p2, KeyControl keys) {
        if (p1.getRec().getBounds2D().intersects(p2.getRec().getBounds2D())) {
            if (((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && ((p2.getDirection() >= 90 && p2.getDirection() <= 180) || (p2.getDirection() <= 270 && p2.getDirection() >= 180)
                    || (p2.getDirection() <= -90 && p2.getDirection() >= -180) || (p2.getDirection() >= -270 && p2.getDirection() <= -180))
                    && p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()) {
                keys.haltF();
                keys.haltF2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 90 && p1.getDirection() <= 180) || (p1.getDirection() <= 270 && p1.getDirection() >= 180)
                    || (p1.getDirection() <= -90 && p1.getDirection() >= -180) || (p1.getDirection() >= -270 && p1.getDirection() <= -180))
                    && ((p2.getDirection() <= 90 && p2.getDirection() >= 0) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() >= -90 && p2.getDirection() <= 0) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()) {
                keys.haltF();
                keys.haltF2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p2.getDirection() >= 180 && p2.getDirection() <= 270) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() <= 0 && p2.getDirection() >= -90) || (p2.getDirection() >= -180 && p2.getDirection() <= -90))
                    && ((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() <= 180 && p1.getDirection() >= 90)
                    || (p1.getDirection() <= -180 && p1.getDirection() >= -270) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                keys.haltF();
                keys.haltF2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 180 && p1.getDirection() <= 270) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() <= 0 && p1.getDirection() >= -90) || (p1.getDirection() >= -180 && p1.getDirection() <= -90))
                    && ((p2.getDirection() <= 90 && p2.getDirection() >= 0) || (p2.getDirection() <= 180 && p2.getDirection() >= 90)
                    || (p2.getDirection() <= -180 && p2.getDirection() >= -270) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                keys.haltF();
                keys.haltF2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            }
            if (((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() >= -90 && p1.getDirection() <= 0) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && ((p2.getDirection() >= 90 && p2.getDirection() <= 180) || (p2.getDirection() <= 270 && p2.getDirection() >= 180)
                    || (p2.getDirection() <= -90 && p2.getDirection() >= -180) || (p2.getDirection() >= -270 && p2.getDirection() <= -180))
                    && p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()) {
                keys.haltB();
                keys.haltB2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 90 && p1.getDirection() <= 180) || (p1.getDirection() <= 270 && p1.getDirection() >= 180)
                    || (p1.getDirection() <= -90 && p1.getDirection() >= -180) || (p1.getDirection() >= -270 && p1.getDirection() <= -180))
                    && ((p2.getDirection() <= 90 && p2.getDirection() >= 0) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() >= -90 && p2.getDirection() <= 0) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()) {
                keys.haltB();
                keys.haltB2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p2.getDirection() >= 180 && p2.getDirection() <= 270) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() <= 0 && p2.getDirection() >= -90) || (p2.getDirection() >= -180 && p2.getDirection() <= -90))
                    && ((p1.getDirection() <= 90 && p1.getDirection() >= 0) || (p1.getDirection() <= 180 && p1.getDirection() >= 90)
                    || (p1.getDirection() <= -180 && p1.getDirection() >= -270) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                keys.haltB();
                keys.haltB2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 180 && p1.getDirection() <= 270) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() <= 0 && p1.getDirection() >= -90) || (p1.getDirection() >= -180 && p1.getDirection() <= -90))
                    && ((p2.getDirection() <= 90 && p2.getDirection() >= 0) || (p2.getDirection() <= 180 && p2.getDirection() >= 90)
                    || (p2.getDirection() <= -180 && p2.getDirection() >= -270) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))
                    && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                keys.haltB();
                keys.haltB2();
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            }
            if (((p1.getDirection() >= 0 && p1.getDirection() <= 90) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() <= 0 && p1.getDirection() >= -90) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && ((p2.getDirection() >= 0 && p2.getDirection() <= 90) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() <= 0 && p2.getDirection() >= -90) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))) {
                if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else {
                    keys.haltF2();
                    keys.haltB();
                    keys.haltF();
                    keys.haltB2();
                    return;
                }
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 90 && p1.getDirection() <= 180) || (p1.getDirection() <= 270 && p1.getDirection() >= 180)
                    || (p1.getDirection() >= -270 && p1.getDirection() <= -180) || (p1.getDirection() <= -90 && p1.getDirection() >= -180))
                    && ((p2.getDirection() >= 90 && p2.getDirection() <= 180) || (p2.getDirection() <= 270 && p2.getDirection() >= 180)
                    || (p2.getDirection() >= -270 && p2.getDirection() <= -180) || (p2.getDirection() <= -90 && p2.getDirection() >= -180))) {
                if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else {
                    keys.haltF2();
                    keys.haltB();
                    keys.haltF();
                    keys.haltB2();
                    return;
                }
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() >= 180 && p1.getDirection() <= 270) || (p1.getDirection() >= 270 && p1.getDirection() <= 360)
                    || (p1.getDirection() >= -180 && p1.getDirection() <= -90) || (p1.getDirection() >= -90 && p1.getDirection() <= 0))
                    && ((p2.getDirection() >= 180 && p2.getDirection() <= 270) || (p2.getDirection() >= 270 && p2.getDirection() <= 360)
                    || (p2.getDirection() >= -180 && p2.getDirection() <= -90) || (p2.getDirection() >= -90 && p2.getDirection() <= 0))) {
                if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else {
                    keys.haltF2();
                    keys.haltB();
                    keys.haltF();
                    keys.haltB2();
                    return;
                }
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            } else if (((p1.getDirection() <= 180 && p1.getDirection() >= 90) || (p1.getDirection() <= 90 && p1.getDirection() >= 0)
                    || (p1.getDirection() <= -180 && p1.getDirection() >= -270) || (p1.getDirection() <= -270 && p1.getDirection() >= -360))
                    && ((p2.getDirection() <= 180 && p2.getDirection() >= 90) || (p2.getDirection() <= 90 && p2.getDirection() >= 0)
                    || (p2.getDirection() <= -180 && p2.getDirection() >= -270) || (p2.getDirection() <= -270 && p2.getDirection() >= -360))) {
                if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() < p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() < p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF2();
                    keys.haltB();
                } else if (p1.getRec().getBounds2D().getCenterX() > p2.getRec().getBounds2D().getCenterX()
                        && p1.getRec().getBounds2D().getCenterY() > p2.getRec().getBounds2D().getCenterY()) {
                    keys.haltF();
                    keys.haltB2();
                } else {
                    keys.haltF2();
                    keys.haltB();
                    keys.haltF();
                    keys.haltB2();
                    return;
                }
                keys.haltL();
                keys.haltL2();
                keys.haltR();
                keys.haltR2();
                return;
            }
        }
    }
}
