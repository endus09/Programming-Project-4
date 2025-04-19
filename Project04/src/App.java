/*
 * @author Markus Gulla
 * @author
 * @author
 * @author
 * @author
 * @author
 * @author
 */

public class App {
    public static void main(String[] args) throws Exception {
        // Declaring Variables:
        String[] chanceCards = new String[16];
        String[] communityCards = new String[16];

        chanceCards[0] = "Advance to Boardwalk";
        chanceCards[1] = "Advance to Go (Collect $200)";
        chanceCards[2] = "Advance to Illinois Avenue. If you pass Go, collect $200";
        chanceCards[3] = "Advance to St. Charles Place. If you pass Go, collect $200";
        chanceCards[4] = "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled";
        chanceCards[5] = "Advance to the nearest Railroad. If unowned, you may buy it from the Bank. If owned, pay wonder twice the rental to which they are otherwise entitled";
        chanceCards[6] = "Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times amount thrown.";
        chanceCards[7] = "Bank pays you dividend of $50";
        chanceCards[8] = "Get Out of Jail Free";
        chanceCards[9] = "Go Back 3 Spaces";
        chanceCards[10] = "Go to Jail. Go directly to Jail, do not pass Go, do not collect $200";
        chanceCards[11] = "Make general repairs on all your property. For each house pay $25. For each hotel pay $100";
        chanceCards[12] = "Speeding fine $15";
        chanceCards[13] = "Take a trip to Reading Railroad. If you pass Go, collect $200";
        chanceCards[14] = "You have been elected Chairman of the Board. Pay each player $50";
        chanceCards[15] = "Your building loan matures. Collect $150";

        communityCards[0] = "Advance to Go (Collect $200)";
        communityCards[1] = "Bank error in your favor. Collect $200";
        communityCards[2] = "Doctor's fee. Pay $50";
        communityCards[3] = "From sale of stock you get $50";
        communityCards[4] = "Get Out of Jail Free";
        communityCards[5] = "Go to Jail. Go directly to jail, do not pass Go, do not collect $200";
        communityCards[6] = "Holiday fund matures. Receive $100";
        communityCards[7] = "Income tax refund. Collect $20";
        communityCards[8] = "It is your birthday. Collect $10 from every player";
        communityCards[9] = "Life insurance matures. Collect $100";
        communityCards[10] = "Pay hospital fees of $100";
        communityCards[11] = "Pay school fees of $50";
        communityCards[12] = "Receive $25 consultancy fee";
        communityCards[13] = "You are assessed for street repair. $40 per house. $115 per hotel";
        communityCards[14] = "You have won second prize in a beauty contest. Collect $10";
        communityCards[15] = "You inherit $100";

        Deck chance = new Deck(chanceCards);
        Deck community = new Deck(communityCards);



        
        System.out.println(chance.toString()); // testing, remove later

        for (int i = 0; i < 12; i++){           // testing, remove later
            chance.discard(chance.draw());
        }
        System.out.println();                  // testing, remove later
        System.out.println(chance.toString()); // testing, remove later
        System.out.println();                  // testing, remove later
        for (int i = 0; i < 5; i++){           // testing, remove later
            chance.discard(chance.draw());
        }
        System.out.println();                  // testing, remove later
        System.out.println(chance.toString()); // testing, remove later
        System.out.println();

        System.out.println(Dice.roll()); // testing, remove later


    }
}
