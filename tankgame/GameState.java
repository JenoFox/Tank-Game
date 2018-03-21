package tankgame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class GameState extends State {
    // Buttons are created using rectangle
    private final Rectangle menuButton = new Rectangle(540, 375, 100, 50);
    private final Rectangle exitButton = new Rectangle(540, 475, 100, 50);

    GameState(Manager manager) {
        super(manager);
    }

    @Override // Controls mouse input and updates accordingly
    public void tick() {
        // **** Menu Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 540 && manager.getMouseControl().getMouseX() <= 640) {
            if (manager.getMouseControl().getMouseY() >= 375 && manager.getMouseControl().getMouseY() <= 425) {
                manager.getBackgroundSound().stop(); // Stops the background score
                manager.getVictoryMusic().stop(); // Stops the victory music.
                manager.reset(); // Resets and initialize method is called again
            }
        }
        // **** Exit Button ****
        if (manager.getMouseControl().isLeftPressed() && manager.getMouseControl().getMouseX() >= 540 && manager.getMouseControl().getMouseX() <= 640) {
            if (manager.getMouseControl().getMouseY() >= 475 && manager.getMouseControl().getMouseY() <= 525) {
                System.exit(0); //Exits the program
            }
        }
    }
    // Buttons are drawn here.
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font f2 = new Font("arial", Font.BOLD, 30);
        g2d.setFont(f2); //Sets the font
        g2d.drawString("MENU", menuButton.x + 5, menuButton.y + 35);
        g2d.draw(menuButton); // Draws the menu button
        g2d.drawString("EXIT", exitButton.x + 20, exitButton.y + 35);
        g2d.draw(exitButton); // Draws the exit button
    }
}
