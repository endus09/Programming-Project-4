import java.util.Random;

/*
 * Implements a 6-sided die rolling simulator.
 * 
 * @Jasmine Bonitz
 */
public class Dice {
    // Generate a random number between 1 and 6 (inclusive)
    public static int roll() {
    	Random random = new Random();
	    int rollResult = random.nextInt(6) + 1;
	    
	    return rollResult;
    }
}