package mariya.dimitrova.multithreading;

import java.util.ArrayList;

/****
 * A
 *
 * class that contains all of the players that can currently participate in
 * a*game.**
 *
 * @author Mihail Stoynov
 */

public class FreePlayers {
    private ArrayList<Player> freePlayers;

    /**
     * Creates a list of all of the free players.
     *
     * @param freePlayers
     *            List of free Players.
     */
    public FreePlayers(ArrayList<Player> freePlayers) {
        this.freePlayers = freePlayers;
    }

    /**
     * Gets a player at a certain index. The indexes start from 1. Used at the
     * begging of the game, when the player chooses a opponent.
     *
     * @param index
     *            The index of the free player you want.
     * @return The free player.
     */
    public Player getPlayer(int index) {
        return this.freePlayers.get(index - 1);
    }

    /**
     * Gets the size of the list of free players. Used to iterate over the list.
     *
     * @return Returns the number of free players currently.
     */
    public int size() {
        return this.freePlayers.size();
    }
}