// @author Bryson Lott

public class Board {

    // set constants for each special space
    public static final int NORMAL = 0;
    public static final int CHANCE = 1;
    public static final int COMMUNITY_CHEST = 2;
    public static final int GO_TO_JAIL = 3;
    public static final int JAIL = 4;
    
    // creates 40 spaces
    private int[] spaces = new int[40];

    public Board() {
       
        // Set specific spaces to special types
        spaces[7] = CHANCE;
        spaces[22] = CHANCE;
        spaces[36] = CHANCE;

        spaces[2] = COMMUNITY_CHEST;
        spaces[17] = COMMUNITY_CHEST;
        spaces[33] = COMMUNITY_CHEST;

        spaces[30] = GO_TO_JAIL;
        spaces[10] = JAIL;

        // All other spaces are NORMAL by default 
    }

    // check a special space is landed on
    public boolean isChance(int position) {
        return spaces[position] == CHANCE;
    }

    public boolean isCommunityChest(int position) {
        return spaces[position] == COMMUNITY_CHEST;
    }

    public boolean isGoToJail(int position) {
        return spaces[position] == GO_TO_JAIL;
    }

    public int getJailPosition() {
        return 10;
    }
}

