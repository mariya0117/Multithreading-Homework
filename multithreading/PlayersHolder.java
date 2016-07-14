package mariya.dimitrova.multithreading;

import java.util.ArrayList;
import java.util.Vector;

/**
 * this inner class is used as a closure(implements Holder), because we do not
 * want to give reference to the whole BCServer class. It holds the
 * waitingClients and adds or gives their list it is made as a thread so the
 * BCServer does not have to wait this class's operations
 */
public class PlayersHolder implements Holder, Runnable {
    private Vector players;

    /**
     * default constructor
     */
    public PlayersHolder() {
    }

    /**
     * add player to the playersHolder. Does it synchronizing on the players
     * vector!
     *
     * @param player
     *            the player to be added to waiting players
     */
    @Override
    public void addPlayer(Player player) {
        synchronized (this.players) {
            this.players.add(player);
        }
    }

    /**
     * this method returns a string which represents player choices it does it
     * syncronizing on the players vector!
     *
     * @return a class with the currently free players
     */
    @Override
    public FreePlayers getFreePlayers() {
        synchronized (this.players) {
            // StringBuffer choices = new StringBuffer("Free
            // Players:\n0)(wait for another player to choose you)");
            ArrayList<Player> freePlayers = new ArrayList<Player>();
            for (int i = 0; i < this.players.size(); i++) {
                Player player = (Player) this.players.elementAt(i);
                if (!player.isBusy()) {
                    freePlayers.add(player);
                }
            }
            return new FreePlayers(freePlayers);
        }
    }

    /**
     * returns a specified player
     *
     * @param i
     *            the player index
     * @return the player
     * @throws ArrayIndexOutOfBoundsException
     *             if bad index
     */
    @Override
    public Player getPlayer(int i) {
        synchronized (this.players) {
            return (Player) this.players.get(i);
        }
    }

    /**
     * removes a player
     *
     * @param player
     *            the player to remove
     */
    @Override
    public void removePlayer(Player player) {
        synchronized (this.players) {
            this.players.remove(player);
        }
    }

    /**
     * initializes data starts the thread
     */
    @Override
    public void run() {
        this.players = new Vector();
    }
}
