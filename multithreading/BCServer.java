package mariya.dimitrova.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server of the program. Implements Runnable and the main method invokes
 * the overridden run method.
 *
 * @author mariyadimitrova
 *
 */
public class BCServer implements Runnable {
    private static final int PORT = 3008;
    private Players waitingPlayers;

    /**
     * Creates a new server. Initializes the waiting players.
     */
    public BCServer() {
        this.waitingPlayers = new Players();
    }

    /**
     * Initializes the server socket, the socket and listens for new players. If
     * a new players arrives, It starts his run method.
     */
    @Override
    public void run() {
        ServerSocket serverSocket = null;
        Socket newClientSocket = null;

        try {
            Thread.currentThread().setName("LISTENER");
            serverSocket = new ServerSocket(PORT);
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Waiting for new client...");
                newClientSocket = serverSocket.accept();

                PrintWriter playerWriter = new PrintWriter(newClientSocket.getOutputStream());
                BufferedReader playerReader = new BufferedReader(
                        new InputStreamReader(newClientSocket.getInputStream()));

                Player newPlayer = new Player(playerWriter, playerReader);
                NewPlayerThread newPlayerThread = new NewPlayerThread(newPlayer, this.waitingPlayers);
                System.out.println("New client arrived. ClientThread starts");
                new Thread(newPlayerThread).start();
            }

            newClientSocket.close();
        } catch (IOException e) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}