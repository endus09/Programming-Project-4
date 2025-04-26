import java.util.Arrays;

/**
 * Simulates a simplified version of Monopoly focused on calculating the probability
 * of landing on different board spaces. The simulation tracks player movement,
 * jail strategies, and card effects while ignoring financial aspects of the game.
 * 
 * @author Pranay Jarabani
 */

public class Reserve {
    // Constants
    private static final int BOARD_SIZE = 40;
    private static final int JAIL_POSITION = 10;
    private static final int GO_TO_JAIL_POSITION = 30;
    private static final int MAX_TURNS_IN_JAIL = 3;
    
    // Game components
    private final Deck chanceDeck;
    private final Deck communityChestDeck;
    private final Dice dice;
    
    // Player state
    private int playerPosition;
    private int consecutiveDoubles;
    private int turnsInJail;
    private boolean inJail;
    private int gojfCards; // Get Out of Jail Free cards
    
    // Tracking
    private final int[] landings;
    private int totalMoves;
    private final boolean useStrategyB;
    
    public Reserve(String[] chanceCards, String[] communityCards, boolean useStrategyB) {
        this.chanceDeck = new Deck(chanceCards);
        this.communityChestDeck = new Deck(communityCards);
        this.dice = new Dice();
        this.landings = new int[BOARD_SIZE];
        this.useStrategyB = useStrategyB;
        this.playerPosition = 0; // Start at GO
        landings[0] = 1; // Initial position counts as a landing
    }
    
    public void simulate(int turns) {
        for (int i = 0; i < turns; i++) {
            playTurn();
        }
    }
    
    private void playTurn() {
        totalMoves++;
        
        if (inJail) {
            handleJailTurn();
            return;
        }
        
        // Roll dice
        int roll1 = dice.roll();
        int roll2 = dice.roll();
        
        if (roll1 == roll2) {
            consecutiveDoubles++;
            if (consecutiveDoubles == 3) {
                goToJail("Rolled three doubles");
                return;
            }
        } else {
            consecutiveDoubles = 0;
        }
        
        // Move player
        int steps = roll1 + roll2;
        movePlayer(steps);
    }
    
    private void movePlayer(int steps) {
        playerPosition = (playerPosition + steps) % BOARD_SIZE;
        landings[playerPosition]++;
        
        // Check for special positions
        if (playerPosition == GO_TO_JAIL_POSITION) {
            goToJail("Landed on Go To Jail");
            return;
        }
        
        // Handle Chance and Community Chest
        if (playerPosition == 7 || playerPosition == 22 || playerPosition == 36) {
            handleChanceCard();
        } else if (playerPosition == 2 || playerPosition == 17 || playerPosition == 33) {
            handleCommunityChestCard();
        }
    }
    
    private void handleChanceCard() {
        String card = chanceDeck.draw();
        if (card == null) return;
        
        if (card.contains("Go to Jail")) {
            goToJail("Chance card: " + card);
        } else if (card.contains("Get Out of Jail Free")) {
            gojfCards++;
        } else if (card.contains("Advance to Boardwalk")) {
            moveTo(39); // Boardwalk position
        } else if (card.contains("Advance to Go")) {
            moveTo(0);
        } else if (card.contains("Advance to Illinois Avenue")) {
            moveTo(24);
        } else if (card.contains("Advance to St. Charles Place")) {
            moveTo(11);
        } else if (card.contains("nearest Railroad")) {
            moveToNearestRailroad();
        } else if (card.contains("nearest Utility")) {
            moveToNearestUtility();
        } else if (card.contains("Go Back 3 Spaces")) {
            movePlayer(-3);
        } else if (card.contains("Reading Railroad")) {
            moveTo(5); // Reading Railroad position
        }
        
        chanceDeck.discard(card);
    }
    
    private void handleCommunityChestCard() {
        String card = communityChestDeck.draw();
        if (card == null) return;
        
        if (card.contains("Go to Jail")) {
            goToJail("Community Chest card: " + card);
        } else if (card.contains("Get Out of Jail Free")) {
            gojfCards++;
        } else if (card.contains("Advance to Go")) {
            moveTo(0);
        }
        
        communityChestDeck.discard(card);
    }
    
    private void moveTo(int position) {
        playerPosition = position;
        landings[position]++;
    }
    
    private void moveToNearestRailroad() {
        // Current positions of railroads in classic Monopoly
        int[] railroads = {5, 15, 25, 35};
        moveToNearest(railroads);
    }
    
    private void moveToNearestUtility() {
        // Current positions of utilities in classic Monopoly
        int[] utilities = {12, 28};
        moveToNearest(utilities);
    }
    
    private void moveToNearest(int[] positions) {
        int closest = positions[0];
        int minDistance = Integer.MAX_VALUE;
        
        for (int pos : positions) {
            int distance = (pos - playerPosition + BOARD_SIZE) % BOARD_SIZE;
            if (distance < minDistance) {
                minDistance = distance;
                closest = pos;
            }
        }
        
        moveTo(closest);
    }
    
    private void goToJail(String reason) {
        inJail = true;
        turnsInJail = 0;
        consecutiveDoubles = 0;
        moveTo(JAIL_POSITION);
    }
    
    private void handleJailTurn() {
        turnsInJail++;
        
        if (gojfCards > 0) {
            // Use Get Out of Jail Free card
            gojfCards--;
            inJail = false;
            // Roll and move normally
            int roll1 = dice.roll();
            int roll2 = dice.roll();
            movePlayer(roll1 + roll2);
            return;
        }
        
        if (!useStrategyB) {
            // Strategy A: Pay fine immediately
            inJail = false;
            int roll1 = dice.roll();
            int roll2 = dice.roll();
            movePlayer(roll1 + roll2);
            return;
        }
        
        // Strategy B: Try to roll doubles
        if (turnsInJail > MAX_TURNS_IN_JAIL) {
            // After 3 failed attempts, pay fine
            inJail = false;
            int roll1 = dice.roll();
            int roll2 = dice.roll();
            movePlayer(roll1 + roll2);
            return;
        }
        
        // Attempt to roll doubles
        int roll1 = dice.roll();
        int roll2 = dice.roll();
        
        if (roll1 == roll2) {
            // Success - get out of jail
            inJail = false;
            movePlayer(roll1 + roll2);
        } else {
            // Still in jail - count this as landing on jail again
            landings[JAIL_POSITION]++;
        }
    }
    
    public int[] getLandings() {
        return Arrays.copyOf(landings, landings.length);
    }
    
    public int getTotalMoves() {
        return totalMoves;
    }
    
    public double[] getLandingProbabilities() {
        double[] probabilities = new double[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            probabilities[i] = (double) landings[i] / totalMoves;
        }
        return probabilities;
    }

// Game results printout
  private final int simulationNumber;
    private final Results results1000 = new Results();
    private final Results results10000 = new Results();
    private final Results results100000 = new Results();
    private final Results results1000000 = new Results();
    
    private static class Results {
        int[] counts = new int[BOARD_SIZE];
        double[] percentages = new double[BOARD_SIZE];
    }

    // Modify the constructor to accept simulationNumber
    public Reserve(String[] chanceCards, String[] communityCards, boolean useStrategyB, int simulationNumber) {
        this.chanceDeck = new Deck(chanceCards);
        this.communityChestDeck = new Deck(communityCards);
        this.dice = new Dice();
        this.landings = new int[BOARD_SIZE];
        this.useStrategyB = useStrategyB;
        this.simulationNumber = simulationNumber;
        this.playerPosition = 0;
        landings[0] = 1;
    }

    // [Keep all your existing methods]
    // Add these new methods for output:

    private void captureResults(Results results) {
        System.arraycopy(landings, 0, results.counts, 0, BOARD_SIZE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            results.percentages[i] = (double) landings[i] / totalMoves * 100;
        }
    }

    public void printResults() {
        String strategy = useStrategyB ? "B" : "A";
        System.out.printf("Strategy %s Simulation #%d of 10%n%n", strategy, simulationNumber);
        printTable();
    }

    private void printTable() {
        // Print header
        System.out.println("|    | n = 1,000 | n = 10,000 | n = 100,000 | n = 1,000,000 |");
        System.out.println("|---|---|---|---|---|");
        System.out.println("|    | Count   | %    | Count   | %    | Count   | %    | Count   | %    |");
        
        // Board position names
        String[] positionNames = {
            "Go", "Baltic", "Comm Chest #1", "Mediterranean", "Income tax",
            "Reading RR", "Oriental ave", "Chance #1", "Vermont Ave", "Connecticut Ave",
            "Jail", "St. Charles Place", "Electric company", "States Ave", "Virginia Ave",
            "Pennsylvania RR", "St. James PI", "Comm Chest #2", "Tennessee Ave", "New York Ave",
            "Free Parking", "Kentucky Ave", "Chance #2", "Indiana Ave", "Illinois Ave",
            "B&O RR", "Atlantic ave", "Ventnor Ave", "Water Works", "Marvin Gardens",
            "Go to Jail", "Pacific Ave", "North Carolina Ave", "Comm. Chest #3", "Pennsylvania Ave",
            "Short Line RR", "Chance #3", "Park Place", "Luxury Tax", "Boardwalk"
        };
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.printf("| %-15s | %6d | %5.2f | %6d | %5.2f | %6d | %5.2f | %6d | %5.2f |%n",
                positionNames[i],
                results1000.counts[i], results1000.percentages[i],
                results10000.counts[i], results10000.percentages[i],
                results100000.counts[i], results100000.percentages[i],
                results1000000.counts[i], results1000000.percentages[i]);
        }
        System.out.println();
    }

    // Modify the simulate method to capture results at intervals
    public void simulate(int turns) {
        for (int turn = 1; turn <= turns; turn++) {
            playTurn();
            
            if (turn == 1_000) captureResults(results1000);
            else if (turn == 10_000) captureResults(results10000);
            else if (turn == 100_000) captureResults(results100000);
            else if (turn == 1_000_000) captureResults(results1000000);
        }
    }
}
