package mariya.dimitrova.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class contains the players writer and reader.
 *
 * @author mariyadimitrova
 *
 */
public class Player {
    private String name;
    private boolean isBusy;
    private PrintWriter playerWriter;
    private BufferedReader playerReader;
    private String playerNumber;

    /**
     * Creates a new player with reader and writer. By default (in the begging
     * of the game) the player is not busy.
     *
     * @param clientWriter
     * @param clientReader
     */
    public Player(PrintWriter clientWriter, BufferedReader clientReader) {
        if (clientWriter == null || clientReader == null) {
            throw new NullPointerException("Player: null data supplied");
        }

        this.isBusy = false;
        this.playerWriter = clientWriter;
        this.playerReader = clientReader;
    }

    /**
     * Get the value of the number of the player.
     *
     * @return This value.
     */
    public String getNumber() {
        return this.playerNumber;
    }

    /**
     * Sets the new value of the number of the player.
     *
     * @param playerNumber
     *            The new value to be set.
     */
    public void setNumber(String playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     *
     */

    /**
     * name getter
     *
     * @return the name
     */
    public String getName() {
        return this.name;
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
     * asks the player for its name
     *
     * @return the player name
     */
    public String askForName() {
        this.write("Please enter your name: ");

        return this.readLine();
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

    // hoi

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
    public String readLine() {
        String s = "";

        try {
            s = this.playerReader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

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