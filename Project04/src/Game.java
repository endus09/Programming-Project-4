/**
 * Simulates a simplified version of Monopoly focused on calculating the probability
 * of landing on different board spaces. The simulation tracks player movement,
 * jail strategies, and card effects while ignoring financial aspects of the game.
 *
 * @author Pranay Jarabani
 */

public class Game {
    private Deck chanceDeck;
    private Deck communityDeck;
    private Player player;
    private int totalMoves; // tracks total moves

    public static final String[] POSITION_NAMES = {
            "Go", "Baltic Avenue", "Community Chest #1", "Mediterranean Avenue", "Income Tax",
            "Reading Railroad", "Oriental Avenue", "Chance #1", "Vermont Avenue", "Connecticut Avenue",
            "Jail / Just Visiting", "St. Charles Place", "Electric Company", "States Avenue", "Virginia Avenue",
            "Pennsylvania Railroad", "St. James Place", "Community Chest #2", "Tennessee Avenue", "New York Avenue",
            "Free Parking", "Kentucky Avenue", "Chance #2", "Indiana Avenue", "Illinois Avenue",
            "B&O Railroad", "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens",
            "Go To Jail", "Pacific Avenue", "North Carolina Avenue", "Community Chest #3", "Pennsylvania Avenue",
            "Short Line Railroad", "Chance #3", "Park Place", "Luxury Tax", "Boardwalk"
    };

    public Game(Deck chanceDeck, Deck communityDeck, Player player) {
        this.chanceDeck = chanceDeck;
        this.communityDeck = communityDeck;
        this.player = player;
        this.totalMoves = 0;
    }

    // Simulate method - we simulate a number of turns
    public void simulate(int turns) {
        for (int i = 0; i < turns; i++) {
            int diceRoll = Dice.roll() + Dice.roll(); // Roll two
            player.moveForward(diceRoll);
            totalMoves++; // Increment total moves every turn

            // Track the landing count for the space the player lands on
            int position = player.getCurrentPosition();
            player.incrementLandingCountAt(position);
        }
    }

    // Get the count of landings at a specific space
    public int getLandingCountAt(int space) {
        return player.getLandingCountAt(space);  // This calls the Player class method
    }

    // Get the landing probabilities
    public double[] getLandingProbabilities() {
        int totalLandings = 0;
        for (int count : player.getLandings()) {
            totalLandings += count;
        }

        double[] probabilities = new double[40];
        for (int i = 0; i < 40; i++) {
            probabilities[i] = totalLandings == 0 ? 0 : (double) player.getLandingCountAt(i) / totalLandings;
        }

        return probabilities;
    }

    // Get the total number of moves
    public int getTotalMoves() {
        return totalMoves;  // Return the total number of moves made
    }

    // Get the name of a specific position
    public static String getPositionName(int index) {
        return POSITION_NAMES[index];
    }
}



