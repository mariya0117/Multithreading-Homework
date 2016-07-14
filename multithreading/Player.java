package mariya.dimitrova.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Class holds player's reader and writer. It has readLine() and writeLine()
 * methods to ease the communication
 *
 * @author Mihail Stoynov
 */
class Player {
    private String name;
    private boolean isBusy;
    private PrintWriter playerWriter;
    private BufferedReader playerReader;
    private char[] playerNumber;

    /**
     * general purpose constructor, initializes member variables /@param name
     * player name
     *
     * @param clientWriter
     *            player text output
     * @param clientReader
     *            player text input
     */
    public Player(PrintWriter clientWriter, BufferedReader clientReader) {
        // if bad data
        if (clientWriter == null || clientReader == null) {
            throw new NullPointerException("Player: null data supplied");
        }
        this.isBusy = false;
        this.playerWriter = clientWriter;
        this.playerReader = clientReader;
    }

    /**
     * Gets the player number
     *
     * @return the number
     */
    public char[] getNumber() {
        return this.playerNumber;
    }

    /**
     * sets the player number
     *
     * @param playerNumber
     *            the new player number
     */
    public void setNumber(char[] playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * name getter
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * asks the player for its name
     *
     * @return the player name
     */
    public String askForName() {
        this.write("Please enter your name: ");
        try {
            return this.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * isBusy getter
     *
     * @return isBusy
     */
    public boolean isBusy() {
        return this.isBusy;
    }

    /**
     * todo: look at this method, it is synchronized, and if the player is busy,
     * returns false todo: if the player is not busy, makes him busy and returns
     * true
     *
     * @return true if free ( but makes him busy ), false if busy
     */
    public synchronized boolean setSafelyBusy() {
        if (this.isBusy) {
            return false;
        } else {
            this.isBusy = true;
            return true;
        }
    }

    /**
     * name setter
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * reads one char
     *
     * @return the char read, -1 if eof or error
     */
    public char readOneChar() {
        char[] result = new char[1];
        try {
            if (this.playerReader.read(result, 0, 1) != 1) {
                return (char) -1;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return (char) -1;
        }
        return result[0];
    }

    public long skip() {
        int result = 0;
        try {
            while (this.playerReader.skip(1L) > 0) {
                result++;
            }
            // return playerReader.skip(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * writes a string to the player input
     *
     * @param toWrite
     *            the string to write
     */
    public void write(String toWrite) {
        this.playerWriter.write(toWrite);
        this.playerWriter.flush();
    }

    /**
     * reads a line from the player
     *
     * @return the line read
     * @throws IOException
     *             socket closed or some other error
     */
    public String readLine() throws IOException {
        String s = this.playerReader.readLine();
        return s;
    }

    /**
     * writes a line to the player
     *
     * @param line
     */
    public void writeLine(String line) {
        this.playerWriter.println(line);
        this.playerWriter.flush();
    }

    /**
     * closes player streams
     */
    public void closeStreams() {
        try {
            this.playerReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.playerWriter.close();
    }
}
