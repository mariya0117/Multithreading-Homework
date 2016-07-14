package mariya.dimitrova.multithreading;

import java.util.ArrayList;

/**
 * class that holds the currently free players
 *
 * @author Mihail Stoynov
 */
public class FreePlayers {

    private ArrayList<Player> freePlayers;

    /**
     * general purpose constructor, only constructor
     *
     * @param freePlayers
     *            the players
     */
    public FreePlayers(ArrayList<Player> freePlayers) {
        this.freePlayers = freePlayers;
    }

    /**
     * returns one player
     *
     * @param i
     *            the player number
     * @return the player
     */
    public Player getPlayer(int i) {
        return this.freePlayers.get(i - 1);
    }

    /**
     * returns the number of free players currently
     *
     * @return their number
     */
    public int size() {
        return this.freePlayers.size();
    }
}