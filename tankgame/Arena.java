package tankgame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JFrame;

public class Arena extends JApplet implements Runnable {

    // **** Game Set Up ****
    private JFrame frame;
    private Thread thread;
    private Canvas background;
    private BufferStrategy bs;
    // **** Images , Sounds and Graphics ****   
    private Sounds MenuClip, BackgroundClip, Victory;
    private Graphics2D g;
    private BufferedImage arenaBackground, menuBackground, infobar;
    private BufferedImage health1, health2;
    // **** Inputs ****
    private KeyControl keyManager;
    private MouseControl mouseManager;
    // **** States ****
    private State gameState;
    private State menuState;
    private State helpState;
    // **** Manager ****
    private Manager manager;
    // **** Booleans ****
    private boolean isRunning;
    private boolean sideCheck;
    private boolean victory;
    // **** GameObjects ****
    private PlayerTank player1;
    private PlayerTank player2;
    private final Wall[] wall = new Wall[42];
    private final Wall[] sandbag = new Wall[12];
    private final Wall[] barrier = new Wall[14];
    private ArrayList<Bullet> bullet;
    private ArrayList<Bullet> bullet2;
    private ArrayList<Explosion> explosions;
    private ArrayList<PowerUp> powerups;
    private Collision CD;
    // **** Ticks and Counters ****
    private int timeCheck;
    private int timeCheck2;
    private int winTick;
    private int p1Score;
    private int p2Score;
    private int powerUpTick;

    // **** Methods Begin ****
    // Used to initialize all data fields and objects being affected in the game
    private void initialize() {
        // Both players scores start at 0
        p1Score = 0;
        p2Score = 0;
        powerUpTick = 0; // Powerup tick starts at 0 and then ticks to create new powerup at a certain time
        winTick = 75; // Tick to animate winning message
        // TimeChecks for checking how often a player can shoot
        timeCheck = 60;
        timeCheck2 = 60;
        // Various images are loaded
        arenaBackground = loadImage("Resources/grassbackground.jpg");
        menuBackground = loadImage("Resources/steelbackground.jpg");
        infobar = loadImage("Resources/infobar.jpg");
        health1 = loadImage("Resources/healthbar-1.png");
        health2 = loadImage("Resources/healthbar-1.png");
        MenuClip = new Sounds("Resources/MenuMusic.wav");
        BackgroundClip = new Sounds("Resources/backgroundmusic.wav");
        Victory = new Sounds("Resources/victory.wav");
        // Player tanks created at specific locations, P1 starts at upper left corner, P2 at lower right
        player1 = new PlayerTank(75, 75, 0, 1);
        player2 = new PlayerTank(1050, 500, 180, 2);
        // Create new arraylist of bullets, powerups, and explosions
        bullet = new ArrayList();
        bullet2 = new ArrayList();
        powerups = new ArrayList();
        explosions = new ArrayList();
        // Place the wall according to the wall type (wall, barrier, sandbag)
        Wall.borderPlacement(wall);
        Wall.barrierPlacement(barrier);
        Wall.sandbagPlacement(sandbag);
        CD = new Collision(); // New collision detector object
        manager = new Manager(this); // Manages states and gives access to arena methods
        gameState = new GameState(manager); // GameState is initiliazed, when game is being played
        menuState = new Menu(manager); // Menu is initiliazed
        helpState = new Help(manager); // HelpState gives information of the game
        State.setState(menuState); // Starts at the menu state
        victory = false; // No one is victorious upon start of the game
    }
    // **** Switch Sides Between Rounds ****
    // Reinitialize the objects based on the round 
    private void switchSides() {
        // P1 is in the upper left, and P2 is in the lower right on even scored rounds
        // Switch spots for odd numbered rounds
        if ((p1Score + p2Score) % 2 == 1) {
            player2 = new PlayerTank(75, 75, 0, 2);
            player1 = new PlayerTank(1050, 500, 180, 1);
        } else {
            player1 = new PlayerTank(75, 75, 0, 1);
            player2 = new PlayerTank(1050, 500, 180, 2);
        }
        // Create new arraylists for bullets, powerups, and reinitialize the powerup tick
        bullet = new ArrayList();
        bullet2 = new ArrayList();
        powerups = new ArrayList();
        powerUpTick = 0;
        // Set health images back to max health
        health1 = loadImage("Resources/healthbar-1.png");
        health2 = loadImage("Resources/healthbar-1.png");
    }
    // Used to Create BufferedImage object for visual representation of the objects
    private BufferedImage loadImage(String pathName) {
        try {
            return ImageIO.read(new File(pathName));
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return null;
    }
    // **** Update ****
    // Updates locations of objects, whether collisions occur,
    // If sides need to be switched, updates score and health, etc...
    private void update() {
        // If the state is in the game playing state, update relevant objects and data fields
        if (State.getState() == gameState) {
            // If point is scored by either player, switch sides
            if (sideCheck) {
                switchSides();
                sideCheck = false;
            }
            // If powerup is allowed to spawn and there are not 3 powerups already on the field
            // Create a new powerup in a random location oriented towards the middle of the playing field
            if (powerUpTick > 1000 && powerups.size() < 3) {
                powerups.add(new PowerUp((Math.random() * 600) + 200, (Math.random() * 300) + 100));
                powerUpTick = 0;
            }
            // Do not increment the powerupTick if 3 powerups on the field
            // Or else another powerup has a potential to immediatly spawn again directly after previous one has been collided with
            if (powerups.size() < 3) {
                powerUpTick++;
            }
            keyManager.tick(); // Update key control values
            // **** Collision Detection ****
            // Check for bullets vs walls and player vs powerups first
            CD.bulletVSwall(bullet, wall, keyManager, explosions);
            CD.bulletVSwall(bullet2, wall, keyManager, explosions);
            CD.bulletVSwall(bullet, barrier, keyManager, explosions);
            CD.bulletVSwall(bullet2, barrier, keyManager, explosions);
            CD.playerVpowerup(player1, powerups, keyManager);
            CD.playerVpowerup(player2, powerups, keyManager);
            // **** Bullet vs Player ****
            // If a bullet collides with a player, decrement the players health that was hit
            // Create a new explosion at that location
            // If player explodes (health == 5) add an larger explosion
            // Increment players score that defeated the other player and signal that sides need to be switched
            if (CD.bulletVplayer(player2, bullet, keyManager, explosions)) {
                if (player2.getHealth() < 5) {
                    player2.setHealth(player2.getHealth() + 1);
                } else {
                    explosions.remove(explosions.size() - 1);
                    explosions.add(new Explosion(player2.getRec().getBounds2D().getMinX(), player2.getRec().getBounds2D().getMinY(), 300, 300, "explosion1_"));
                    p1Score++;
                    sideCheck = true;
                }
            }
            if (CD.bulletVplayer(player1, bullet2, keyManager, explosions)) {
                if (player1.getHealth() < 5) {
                    player1.setHealth(player1.getHealth() + 1);
                } else {
                    explosions.remove(explosions.size() - 1);
                    explosions.add(new Explosion(player1.getRec().getBounds2D().getMinX(), player1.getRec().getBounds2D().getMinY(), 300, 300, "explosion1_"));
                    p2Score++;
                    sideCheck = true;
                }
            }
            // Update the health images for both players after checking for collisions with bullets
            health2 = loadImage("Resources/healthbar-" + player2.getHealth() + ".png");
            health1 = loadImage("Resources/healthbar-" + player1.getHealth() + ".png");
            // **** Players vs Obstacles or other player ****
            // Check for movement collisions, stop movement if necessary
            CD.playerVSwall(player1, wall, keyManager);
            CD.playerVSwall(player2, wall, keyManager);
            CD.playerVSwall(player1, barrier, keyManager);
            CD.playerVSwall(player2, barrier, keyManager);
            CD.playerVSwall(player1, sandbag, keyManager);
            CD.playerVSwall(player2, sandbag, keyManager);
            CD.playerVSplayer(player1, player2, keyManager);
            // Move the players
            player1.move(keyManager);
            player2.move(keyManager);
            // **** Shooting ****
            // If the players shoot key is pressed and if the player is allowed to shoot(timeCheck)
            // Add a new bullet on the center location of the tank and fire the bullet
            // Set that bullet's isShot to true since it has now been shot
            // Reset the bulletTick so that it cannot fire again until allowed to
            if (keyManager.getShoot()) {
                if (timeCheck > 60) {
                    bullet.add(new Bullet(player1.getCenterX(), player1.getCenterY(), player1.getDirection()));
                    bullet.get(bullet.size() - 1).setIsShot(true);
                    timeCheck = player1.getBulletTick();
                }
            }
            if (keyManager.getShoot2()) {
                if (timeCheck2 > 60) {
                    bullet2.add(new Bullet(player2.getCenterX(), player2.getCenterY(), player2.getDirection()));
                    bullet2.get(bullet2.size() - 1).setIsShot(true);
                    timeCheck2 = player2.getBulletTick();
                }
            }
            // Move the bullets that have been fired
            for (int i = 0; i < bullet.size(); i++) {
                bullet.get(i).move(keyManager);
            }
            for (int i = 0; i < bullet2.size(); i++) {
                bullet2.get(i).move(keyManager);
            }
            // Increment the timeChecks to update when a tank can shoot again
            timeCheck++;
            timeCheck2++;
        }
    }
    // **** Draw ****
    // Used to display necessary items to the jframe
    // Different states the game can be in, draw according to the state
    // Handles all images and graphics objects
    private void draw() {
        bs = background.getBufferStrategy(); // Prevent flickering, drawing to a buffer first, then put onto actual screen.
        if (bs == null) { // Create bufferStrategy if it is null
            background.createBufferStrategy(2);
            return;
        }
        g = (Graphics2D) bs.getDrawGraphics(); // Get the graphics object from the bs
        g.drawImage(arenaBackground, 0, 0, 1250, 700, null);  // Draw the backgrounnd
        // If state is in the game playing state, draw all game objects
        if (State.getState() == gameState) {
            // Loop to draw the wall border encompassing the game objects
            for (int i = 0; i < wall.length; i++) {
                wall[i].draw(g);
            }
            // Loop for drawing the tank barriers
            for (int i = 0; i < barrier.length; i++) {
                barrier[i].draw(g);
            }
            // Loop for drawing the sandbags
            for (int i = 0; i < sandbag.length; i++) {
                sandbag[i].draw(g);
            }
            // Loop for drawing the powerups
            for (int i = 0; i < powerups.size(); i++) {
                powerups.get(i).draw(g);
            }
            // Loop for drawing player 1 bullets
            for (int i = 0; i < bullet.size(); i++) {
                bullet.get(i).draw(g);
            }
            // Loop for drawing player 2 bullets
            for (int i = 0; i < bullet2.size(); i++) {
                bullet2.get(i).draw(g);
            }
            player1.draw(g); // Draw player 1
            player2.draw(g); // Draw player 2
            // Animate the explosions that occur
            for (int i = 0; i < explosions.size(); i++) {
                // If the explosion counter is less than 6, else remove the explosion object
                if (explosions.get(i).getCounter() < 6) {
                    explosions.get(i).draw(g);
                    explosions.get(i).setCounter(explosions.get(i).getCounter() + 1); // Update counter so that it can draw next image
                } else {
                    explosions.remove(i);
                    i--;
                }
            }
            // **** Elements Drawn for Player Info ****
            g.drawImage(infobar, 0, 604, 1250, 60, null); // Draw info bar image
            g.setColor(Color.white); // Set the graphics color to white
            g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 48)); // Set the font wanted
            // Draw two strings to organize player info, player 1 on the left side and player 2 on the right
            g.drawString("PLAYER1:   ", 5, 650);
            g.drawString("PLAYER2:   ", 610, 650);
            // Draw the health bar images associated with each player, health goes down each hit
            g.drawImage(health1, 245, 608, 330, 50, null);
            g.drawImage(health2, 855, 608, 330, 50, null);
            g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 40)); // Set new font smaller size
            // Draw two strings for representing the players scores, top center left for P1, top center right for P2
            g.drawString(Integer.toString(p1Score), 540, 30);
            g.drawString(Integer.toString(p2Score), 620, 30);
            g.drawString("|", 586, 28); // Bar to divide player scores
            // **** Winning Animation ****
            // Whichever player scores 3 points first 
            // Animation using ticks to display font larger or smaller of the winning player
            // Explosion effect is added to the losing player to signify the loss
            // Set the graphics object according to specifications
            // Set winTick accordingly to animate
            if (p1Score == 3) {
                if (winTick >= 75 && winTick < 150) {
                    g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 150));
                    g.drawString("PLAYER1 WINS!", 20, 250);
                    winTick++;
                } else if (winTick >= 150) {
                    winTick = 0;
                } else {
                    explosions.add(new Explosion(player2.getRec().getBounds2D().getMinX(), player2.getRec().getBounds2D().getMinY(), 100, 100, "explosion1_"));
                    g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 100));
                    g.drawString("PLAYER1 WINS!", 200, 250);
                    winTick++;
                }
                gameState.tick();
                gameState.draw(g);
            } else if (p2Score == 3) {
                if (winTick >= 75 && winTick < 150) {
                    g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 150));
                    g.drawString("PLAYER2 WINS!", 20, 250);
                    winTick++;
                } else if (winTick >= 150) {
                    winTick = 0;
                } else {
                    explosions.add(new Explosion(player1.getRec().getBounds2D().getMinX(), player1.getRec().getBounds2D().getMinY(), 100, 100, "explosion1_"));
                    g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 100));
                    g.drawString("PLAYER2 WINS!", 200, 250);
                    winTick++;
                }
                gameState.tick();
                gameState.draw(g);
            }
        }
        // If game is in the menu state, draw the menu
        if (State.getState() == menuState) {
            menuState.tick();
            g.drawImage(menuBackground, 0, 0, 1220, 680, null);
            menuState.draw(g);
        }
        // If game is in the help state, draw the help menu
        if (State.getState() == helpState) {
            helpState.tick();
            g.drawImage(menuBackground, 0, 0, 1220, 680, null);
            helpState.draw(g);
        }
        // End draw
        bs.show();
        g.dispose();
    }

    // **** Frame, Thread, and Game Setup ****
    @Override // **** Game LOOP ****
    public void run() {
        initialize(); // Function initialize called to initialize all necessary objects
        int fps = 60; // Number of frames wanted per second
        double timeperupdate = 1000000000 / fps;
        double delta = 0;
        long now;
        long timer = 0;
        int ticks = 0;
        long lastTime = System.nanoTime(); // Gets system time in nano-seconds
        while (isRunning) {
            // Controls the frame rate using delta and timer. 
            now = System.nanoTime();
            delta += (now - lastTime) / timeperupdate;
            timer += now - lastTime;
            lastTime = now;
            // If no player has reached a score of 3 yet keep updating and drawing
            if (p1Score < 3 && p2Score < 3 && delta >= 1) {
                update();
                draw();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000) {
                ticks = 0;
                timer = 0;
            }
            // If a player has reached a score of 3 stop updating but continue drawing
            if (p1Score >= 3 || p2Score >= 3) {
                ticks++;
                if (!victory) { // Play victory music and stop the background music
                    BackgroundClip.stop();
                    Victory.play();
                    victory = true;
                }
                draw();
            }
        }
        stop();
    }

    @Override // USed to create the new thread when the game is started
    public synchronized void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override // Used to stop the thread and join it once the game is no longer in its running state
    public synchronized void stop() {
        if (!isRunning) {
            return;
        }
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    // **** Frame and Canvas SetUP ****
    // Create the new frame, canvas, KeyControl, and MouseControl objects
    // Set dimensions of frame
    // Take care of any attributes that are necessary for the frame and canvas
    private void setUp() {
        frame = new JFrame("Tank Game!"); // create new frame with title.
        background = new Canvas(); // creating canvas to put within frame.
        background.setPreferredSize(new Dimension(1200, 650)); // setting background dimensions
        keyManager = new KeyControl();
        mouseManager = new MouseControl();
        frame.addKeyListener(keyManager); // Add the keyListener
        background.addMouseListener(mouseManager); // Add the mouseListener
        background.setFocusable(false);
        frame.setSize(1200, 650);
        frame.add(background); // adds canvas to the frame.
        frame.pack(); // Pack the canvas into the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true); // Make sure frame is visible
        frame.setResizable(false); // Frame is not resizeable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit on closing the window
    }
    // Used to return if the user is in the game playing state
    public State getGamestate() {
        return gameState;
    }
    // Used to return if the user is in ther help menu
    public State getHelpState() {
        return helpState;
    }
    // Returns the key control object of the game
    public KeyControl getKeyControl() {
        return keyManager;
    }
    // Returns the mousecontrol object of the game
    public MouseControl getMouseControl() {
        return mouseManager;
    }
    // Returns the menu music 
    public Sounds getMenuSound() {
        return MenuClip;
    }
    // Returns the background music of the game
    public Sounds getBackgroundSound() {
        return BackgroundClip;
    }
    // Returns the music played upon one tank winning
    public Sounds getVictoryMusic() {
        return Victory;
    }
    // **** MAIN ****
    public static void main(String[] args) {
        Arena tankGame = new Arena();
        tankGame.setUp();
        tankGame.start();
    }
}