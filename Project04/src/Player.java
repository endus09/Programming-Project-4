/*
 * Represents a player on the Monopoly board.
 * Tracks the player's current position on the board, 
 * number of times each space has been landed on, and the number of turns taken.
 * Methods to move the player, update their position, and keep statistics for the 
 * "Program Output" part.
 * 
 * @author Caleb Park
 */

public class Player {
    private int position; // Current board position (0–39)
    private int jailTurns; // How many turns stuck in jail
    private boolean inJail; // Jail state
    private int GOOJFC; // Get Out Of Jail Free Cards
    private int doublesRolled;  // Number of consecutive doubles rolled
    private final int[] landings; // Track how often the player lands on each space

    public Player() {
        this.position = 0; // Start at GO
        this.jailTurns = 0;
        this.inJail = false;
        this.doublesRolled = 0;
        this.landings = new int[40]; // 40 Monopoly spaces
        landings[0] = 1; // Start on GO
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int newPosition) {
        position = newPosition % 40; // Wrap around board
        if (position < 0) // Just in case position is negative
            position += 40;

        landings[position]++;
    }

    public boolean isInJail() {
        return inJail;
    }

    public void sendToJail() {
        inJail = true;
        jailTurns = 0;
        setPosition(10); // Jail is at position 10 on the board
    }

    public void releaseFromJail() {
        inJail = false;
        jailTurns = 0;
    }

    public void incrementJailTurns() {
        jailTurns++;
    }

    public int getJailTurns() {
        return jailTurns;
    }

    public void resetDoubles() {
        doublesRolled = 0;
    }

    public void incrementDoubles() {
        doublesRolled++;
    }

    public int getDoublesRolled() {
        return doublesRolled;
    }

    public int[] getLandings() {
        return landings;
    }

    public int getLandingCountAt(int space) {
        if (space < 0 || space >= 40) return 0;
        return landings[space];
    }

    public void moveForward(int steps) {
        setPosition(position + steps);
    }

    public void moveTo(int space) {
        setPosition(space);
    }

    public void addGOOJFC() {
        GOOJFC++;
    }

    public boolean useGOOJFC() {
        if (GOOJFC > 0) {
            GOOJFC--;
            return true;
        }
        return false;
    }
}
