package tankgame;

public class Manager {

    private Arena arena;
    // Initiliazes arena object
    public Manager(Arena arena) {
        this.arena = arena;
    }
    // Returns arena object
    public Arena getArena() {
        return arena;
    }
    // Sets arena object
    public void setArena(Arena arena) {
        this.arena = arena;
    }
    // Returns keyControl from arena
    public KeyControl getKeyControl() {
        return arena.getKeyControl();
    }
    // Returns mouseControl from arena
    public MouseControl getMouseControl() {
        return arena.getMouseControl();
    }
    // Calls run method, to re-initialize
    public void reset() {
        arena.run();
    }
    // Returns menuSounds 
    public Sounds getMenuSound() {
        return arena.getMenuSound();
    }
    // Returns Background Sound
    public Sounds getBackgroundSound() {
        return arena.getBackgroundSound();
    }
    // Returns victoryMusic
    public Sounds getVictoryMusic() {
        return arena.getVictoryMusic();
    }
}
