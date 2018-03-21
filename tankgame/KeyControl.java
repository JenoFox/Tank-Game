package tankgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyControl implements KeyListener {

    private final boolean[] keys;                       // Array to store all the keys in the keyboard.
    private boolean forward, backward, left, right;     // Player 1 movement keys
    private boolean forward2, backward2, left2, right2; // Player 2 movment keys
    private boolean shoot;                              // Player 1 shoot key
    private boolean shoot2;                             // Player 2 shoot key
    // Used to update key value upon each tick in the games update loop.
    public void tick() {
        forward = keys[KeyEvent.VK_W];
        backward = keys[KeyEvent.VK_S];
        left = keys[KeyEvent.VK_A];
        right = keys[KeyEvent.VK_D];
        shoot = keys[KeyEvent.VK_SPACE];
        forward2 = keys[KeyEvent.VK_UP];
        backward2 = keys[KeyEvent.VK_DOWN];
        left2 = keys[KeyEvent.VK_LEFT];
        right2 = keys[KeyEvent.VK_RIGHT];
        shoot2 = keys[KeyEvent.VK_ENTER];
    }
    // Constructor to create new array of keys
    public KeyControl() {
        keys = new boolean[256];
    }

    @Override // When a key is pressed its boolean value is set to true.
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }

    @Override // Not used
    public void keyTyped(KeyEvent ke) {
    }

    @Override // When a key is released its boolean value is set to false.
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
    // **** Halt Functions ****
    // Used to set keys false if necessary (Used in collisions)
    // halt functions without a number, i.e. haltF() are for player 1 keys
    // halt functions with a number, i.e. haltF2() are for player 2 keys
    public void haltF() {
        forward = false;
    }

    public void haltB() {
        backward = false;
    }

    public void haltL() {
        left = false;
    }

    public void haltR() {
        right = false;
    }

    public void haltF2() {
        forward2 = false;
    }

    public void haltB2() {
        backward2 = false;
    }

    public void haltL2() {
        left2 = false;
    }

    public void haltR2() {
        right2 = false;
    }
    // **** Getters for encapsulated data fields ****
    // Used mainly for moving the tank
    // get functions without a number, i.e. getF() are for player 1 keys
    // get functions with a number, i.e. getF2() are for player 2 keys
    public boolean getF() {
        return forward;
    }

    public boolean getF2() {
        return forward2;
    }

    public boolean getB() {
        return backward;
    }

    public boolean getB2() {
        return backward2;
    }

    public boolean getL() {
        return left;
    }

    public boolean getL2() {
        return left2;
    }

    public boolean getR() {
        return right;
    }

    public boolean getR2() {
        return right2;
    }
    
    public boolean getShoot(){
        return shoot;
    }
    
    public boolean getShoot2(){
        return shoot2;
    }
}
