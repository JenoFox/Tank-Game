package tankgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Help extends State {

    private Graphics2D g2d;
    // Creates button as rectangles 
    private final Rectangle playButton = new Rectangle(30, 100, 100, 50);
    private final Rectangle exitButton = new Rectangle(1100, 100, 100, 50);

    public Help(Manager manager) {
        super(manager);
    }

    @Override  // Controls mouse input and updates accordingly
    public void tick() {
        // **** Play Button **** 
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 30 && manager.getMouseControl().getMouseX() <= 110) {
            if (manager.getMouseControl().getMouseY() >= 100 && manager.getMouseControl().getMouseY() <= 150) {
                State.setState(manager.getArena().getGamestate()); // Sets the state to gameState
                manager.getMenuSound().stop(); // Stops the menu music
                manager.getBackgroundSound().play(); // Plays the background score
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 1100 && manager.getMouseControl().getMouseX() <= 1200) {
            if (manager.getMouseControl().getMouseY() >= 100 && manager.getMouseControl().getMouseY() <= 150) {
                System.exit(0); // Exits the program
            }
        }
    }
    // Draws buttons and display messages
    @Override
    public void draw(Graphics g) {
        g2d = (Graphics2D) g;
        Font f1 = new Font("arial", Font.BOLD, 45);
        g2d.setFont(f1); //Sets font
        g2d.setColor(Color.WHITE); // Sets color
        g2d.drawString("RULES", 500, 150);
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); // Sets the font
        g2d.drawString("PLAY", playButton.x + 10, playButton.y + 35);
        g2d.draw(playButton); // Draws playButton
        g2d.drawString("EXIT", exitButton.x + 20, exitButton.y + 35);
        g2d.draw(exitButton); // Draws exitButton
        // **** GAME RULEs **** 
        Font f3 = new Font("arial", Font.BOLD, 24);
        g2d.setFont(f3); // Sets the font
        g2d.drawString("1) THE FIRST PLAYER TO REACH 3 POINTS, WINS THE GAME ", 30, 190);
        g2d.drawString("2) AFTER EVERY POINT, PLAYERS SWITCH SIDES", 30, 220);
        g2d.drawString("3) A TANK CANNOT CROSS A BARRIER", 30, 250);
        g2d.drawString("4) A TANK CAN SHOOT OVER A SANDBAG BUT NOT OVER A TANK BARRIER", 30, 280);
        g2d.drawString("5) POWER UP: RED = MAX HEALTH, GRAY = MORE BULLETS, BLUE = INCREASE SPEED", 30, 310);
        // **** CONTROLS ****
        g2d.setFont(f1);
        g2d.drawString("CONTROLS", 460, 370);
        g2d.setFont(f3);
        g2d.drawString("PLAYER 1 CONTROLS:", 200, 410);
        g2d.drawString("W:         FORWARD", 200, 440);
        g2d.drawString("S:          BACKWARD", 200, 470);
        g2d.drawString("A:          TURN LEFT", 200, 500);
        g2d.drawString("D:          TURN RIGHT", 200, 530);
        g2d.drawString("SPACE: SHOOT", 200, 560);

        g2d.drawString("PLAYER 2 CONTROLS:", 725, 410);
        g2d.drawString("UP-ARROW:         FORWARD", 725, 440);
        g2d.drawString("DOWN-ARROW:   BACKWARD", 725, 470);
        g2d.drawString("LEFT-ARROW:     TURN LEFT", 725, 500);
        g2d.drawString("RIGHT-ARROW:   TURN RIGHT", 725, 530);
        g2d.drawString("ENTER:                 SHOOT ", 725, 560);
    }
}
