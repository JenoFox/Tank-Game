package tankgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu extends State {

    private Graphics2D g2d;
    // Buttons are created
    private final Rectangle playButton = new Rectangle(570, 175, 100, 50);
    private final Rectangle helpButton = new Rectangle(570, 275, 100, 50);
    private final Rectangle exitButton = new Rectangle(570, 375, 100, 50);

    public Menu(Manager manager) {
        super(manager);
        manager.getMenuSound().play(); // Plays menu music
    }

    @Override // Controls mouse input and updates accordingly
    public void tick() {
        // **** Play Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 175 && manager.getMouseControl().getMouseY() <= 225) {
                State.setState(manager.getArena().getGamestate()); // Sets the currentState to gameState
                manager.getMenuSound().stop(); // Stops the menu music
                manager.getBackgroundSound().play(); // Plays the background score
            }
        }
        // **** Help Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 275 && manager.getMouseControl().getMouseY() <= 325) {
                State.setState(manager.getArena().getHelpState()); // Sets currentState to helpState
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 570 && manager.getMouseControl().getMouseX() <= 670) {
            if (manager.getMouseControl().getMouseY() >= 375 && manager.getMouseControl().getMouseY() <= 425) {
                System.exit(0); // Exits the program
            }
        }
    }
    // Draws button and display messages
    @Override
    public void draw(Graphics g) {
        g2d = (Graphics2D) g;
        Font f1 = new Font("arial", Font.BOLD, 45);
        g2d.setFont(f1); // Sets the font
        g2d.setColor(Color.WHITE); // Sets the color
        g2d.drawString("TANK GAME", 475, 150);
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); //Sets font
        g2d.drawString("PLAY", playButton.x + 10, playButton.y + 35);
        g2d.draw(playButton); // Draws playButton
        g2d.drawString("HELP", helpButton.x + 15, helpButton.y + 35);
        g2d.draw(helpButton); // Draws helpButton
        g2d.drawString("EXIT", exitButton.x + 20, exitButton.y + 35);
        g2d.draw(exitButton); // Draws exitButton
    }
}
