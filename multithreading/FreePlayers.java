package mariya.dimitrova.multithreading;

import java.util.ArrayList;

/**
 * Class that holds the currently free players.
 *
 * @author Mihail Stoynov
 */
public class FreePlayers {

    private ArrayList<Player> freePlayers;

    /**
     * Creates new FreePlayers. Represents a list of all players that are not
     * currently playing a game.
     *
     * @param freePlayers
     *            This is the list of the free players.
     */
    public FreePlayers(ArrayList<Player> freePlayers) {
        this.freePlayers = freePlayers;
    }

    /**
     * Returns the player at the indicated index.
     *
     * @param index
     *            This is the player number.
     * @return The player at that index in freePlayers.
     */
    public Player getPlayer(int index) {
        return this.freePlayers.get(index - 1);
    }

    /**
     * Gets the size of the list of all of the players not playing a game.
     *
     * @return This is a integer representing the number of free players
     *         currently.
     */
    public int size() {
        return this.freePlayers.size();
    }
}