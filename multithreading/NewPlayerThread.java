package mariya.dimitrova.multithreading;

import java.io.IOException;

/**
 * this class handles new user. its task is simple: to ask if the user wants to
 * play or to wait to be asked for a game.
 */
public class NewPlayerThread implements Runnable {
    Player player;
    Holder holder;

    /**
     * general purpose constructor.
     *
     * @param newPlayer
     *            the new player
     */
    public NewPlayerThread(Player newPlayer, Holder holder) {
        this.player = newPlayer;
        this.holder = holder;
    }

    @Override
    public void run() {
        try {
            String name = this.player.askForName();
            this.player.setName(name);

            Thread.currentThread().setName(this.player.getName());
            int choice;
            FreePlayers freePlayers;

            do {
                freePlayers = this.holder.getFreePlayers();
                this.displayPossibleChoices(freePlayers);
                choice = this.getPlayerChoice();

                if (choice <= -1 ||
                        choice > freePlayers.size()) {
                    this.player.writeLine("");
                    this.player.writeLine("Bad choice or player busy");
                    choice = -1;
                }
            } while (choice == -1);

            if (choice == 0) {
                this.holder.addPlayer(this.player);
            } else {
                Player p1 = freePlayers.getPlayer(choice);

                p1.writeLine("");
                this.player.writeLine("");
                String gameTitle = this.player.getName() + " vs " + p1.getName() + " Game started";
                p1.writeLine(gameTitle);
                this.player.writeLine(gameTitle);

                NewGameThread newGame = new NewGameThread(p1, this.player, this.holder);
                new Thread(newGame).start();
                newGame.readP2Number();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * displays the possible choices to the default output
     */
    public void displayPossibleChoices(FreePlayers freePlayers) {
        this.player.writeLine("");
        this.player.writeLine("Free Players:");
        this.player.writeLine("0) (wait for another player to choose you)");

        for (int i = 1; i <= freePlayers.size(); i++) {
            Player freePlayer = freePlayers.getPlayer(i);
            this.player.writeLine(i + ")" + freePlayer.getName());
        }

        this.player.write("Choose player: ");
    }

    /**
     * gets player choice; If -1, it mean there was an exceptions and the input
     * is wrong.
     *
     * @return the player choice
     * @throws IOException
     *             if error in reading/writing
     */
    private int getPlayerChoice() throws IOException {
        int choice;

        do {
            try {
                choice = Integer.parseInt(this.player.readLine());
            } catch (NumberFormatException nfx) {
                choice = -1;
            }
        } while (choice == -1);

        return choice;
    }
}