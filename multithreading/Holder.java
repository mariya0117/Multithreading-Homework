package mariya.dimitrova.multithreading;

/**
 * inteface used for the closure/callbackmechanism for chasll
 * BCServer.PlayersHolder
 */
public interface Holder {

    /**
     * adds a player to the queue
     *
     * @param player
     *            the player to add
     */
    public void addPlayer(Player player);

    /**
     * returns the players, ready to play
     *
     * @return
     */
    public FreePlayers getFreePlayers();

    /**
     * gets a player
     *
     * @param i
     *            player number
     * @return the player
     */
    public Player getPlayer(int i);

    /**
     * removes a player
     *
     * @param player
     *            the player to remove
     */
    public void removePlayer(Player player);
}