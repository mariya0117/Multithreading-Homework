package mariya.dimitrova.multithreading;

/**
 * this class represents a game of bulls and cows
 */
class NewGameThread implements Runnable {
    private static final int WAIT_MILIS = 500;
    private Player p1;// the player that starts first
    private Player p2;// the player that is second
    /**
     * the holder, because when the game ends, holder must be given to
     * NewPlayerThread
     */
    private Holder holder;
    // private char[] p1Number = null;
    // private char[] p2Number = null;

    /**
     * general purpose constructor, initializes data
     *
     * @param p1
     *            the player that was asked to play
     * @param p2
     *            the player that started the game
     * @param holder
     *            the holder , because when the game ends, player must be given
     *            to NewPlayerThread
     */
    public NewGameThread(Player p1, Player p2, Holder holder) {
        this.p1 = p1;
        this.p2 = p2;
        this.holder = holder;
    }

    /**
     * the run method only asks the player for its name and waits for the other
     * player digit then starts the game
     */
    @Override
    public void run() {
        this.readP1Number();
        Thread.currentThread().setName(this.p1.getName() + " vs UNKNOWN");
        while (!Thread.currentThread().isInterrupted() && this.p2.getNumber() == null) {
            System.out.println("SLEEP-" + WAIT_MILIS);
            try {
                Thread.sleep(WAIT_MILIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().setName(this.p1.getName() + " vs " + this.p2.getName());

        this.startGame();
    }

    /**
     * method for reading the asked player digit
     */
    public void readP1Number() {
        this.p1.setNumber(this.readNumber(this.p1, "Please insert your number: "));
    }

    /**
     * method for reading the asking player digit
     */
    public void readP2Number() {
        this.p2.setNumber(this.readNumber(this.p2, "Please insert your number: "));
    }

    /**
     * reads the player digit
     *
     * @param player
     *            the player
     * @param userLine
     *            the string to be prompted to ther user
     * @return the digit
     */
    private char[] readNumber(Player player, String userLine) {
        char[] result = new char[4];
        boolean badNumber = true;

        while (badNumber) {
            badNumber = false;
            player.writeLine("");
            player.write(userLine);
            char temp;

            for (int i = 0; i < 4; i++) {
                if ((temp = player.readOneChar()) != -1 &&
                        temp >= '0' && temp <= '9') {
                    result[i] = temp;
                } else {
                    player.writeLine("");
                    player.writeLine("Bad digit, tryAgain !");
                    badNumber = true;
                    break;
                }
            }

            // player.skip();

            if (this.hasEqualDigits(result)) {
                player.writeLine("");
                player.writeLine("You cannot have equal digits !");
                badNumber = true;
            }
        }

        // player.writeLine("");//todo: remove this line in release
        // player.writeLine("The Number U entered is "+
        // result[0]+result[1]+result[2]+result[3]);//todo: remove this line in
        // release
        return result;
    }

    /**
     * checks if the number supplied as char[] has equal digits
     *
     * @param digits
     *            the number
     * @return true if the number has equal digits, false otherwise
     */
    private boolean hasEqualDigits(char[] digits) {
        char[] d = digits;// to make it shorter
        return d[0] == d[1] || d[0] == d[2] || d[0] == d[3] ||
                d[1] == d[2] || d[1] == d[3] ||
                d[2] == d[3];
    }

    /**
     * the game method
     */
    public void startGame() {
        boolean noWinner = true;
        // char[] p1Guess = new char[4];
        // char[] p2Guess = new char[4];

        Player onTurn = this.p1;
        Player offTurn = this.p2;

        while (noWinner) {
            // asking player is first
            char[] onTurnGuess = this.readNumber(onTurn, "Please make your guess: ");
            int offTurnCows = this.nCows(offTurn.getNumber(), onTurnGuess);
            int offTurnBulls = this.nBulls(offTurn.getNumber(), onTurnGuess);
            String guessString = " --> " + offTurnCows + "c" + offTurnBulls + "b";
            onTurn.write(guessString);
            offTurn.writeLine("\t" + this.p1.getName() + "'s Guess " + onTurnGuess[0] + onTurnGuess[1] + onTurnGuess[2]
                    + onTurnGuess[3] + guessString);
            if (offTurnBulls == 4) {
                onTurn.writeLine("");
                onTurn.writeLine("");
                onTurn.writeLine("Game end. You Win!");
                offTurn.writeLine("");
                offTurn.writeLine("Game end. Winner - " + onTurn.getName());
                noWinner = false;
            } else {
                Player temp = onTurn;
                onTurn = offTurn;
                offTurn = temp;
            }
        }
        onTurn.closeStreams();
        offTurn.closeStreams();
        this.holder.removePlayer(onTurn);
        this.holder.removePlayer(offTurn);
    }

    /**
     * counts the number of bulls
     *
     * @param number
     *            the player number
     * @param guess
     *            the other player guess
     * @return bulls
     */
    private int nBulls(char[] number, char[] guess) {
        int result = 0;
        if (number[0] == guess[0]) {
            result++;
        }
        if (number[1] == guess[1]) {
            result++;
        }
        if (number[2] == guess[2]) {
            result++;
        }
        if (number[3] == guess[3]) {
            result++;
        }
        return result;
    }

    /**
     * counts the number of cows
     *
     * @param number
     *            the player number
     * @param guess
     *            the other player guess
     * @return cows
     */
    private int nCows(char[] number, char[] guess) {
        int result = 0;
        if (number[0] == guess[1]) {
            result++;
        }
        if (number[0] == guess[2]) {
            result++;
        }
        if (number[0] == guess[3]) {
            result++;
        }

        if (number[1] == guess[0]) {
            result++;
        }
        if (number[1] == guess[2]) {
            result++;
        }
        if (number[1] == guess[3]) {
            result++;
        }

        if (number[2] == guess[0]) {
            result++;
        }
        if (number[2] == guess[1]) {
            result++;
        }
        if (number[2] == guess[3]) {
            result++;
        }
        return result;
    }
}