package mariya.dimitrova.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  Cows and Bulls game console server
 */
public class BCServer implements Runnable {
    private static final int PORT = 3008;
    private PlayersHolder waitingPlayers;

    /**
     * default constructor
     */
    public BCServer() {
        this.waitingPlayers = new PlayersHolder();
        new Thread(this.waitingPlayers).start();
    }

    /**
     *
     */
    @Override
    public void run() {
        try {
            Thread.currentThread().setName("LISTENER");
            ServerSocket serverSocket = new ServerSocket(PORT);
            while( !Thread.currentThread().isInterrupted() ) {
                System.out.println("Waiting for new client...");
                Socket newClientSocket = serverSocket.accept();
                PrintWriter playerWriter = new PrintWriter( newClientSocket.getOutputStream() );
                BufferedReader playerReader = new BufferedReader( new InputStreamReader(newClientSocket.getInputStream()) );
                Player newPlayer = new Player(playerWriter, playerReader);
                NewPlayerThread newPlayerThread = new NewPlayerThread( newPlayer, this.waitingPlayers );
                System.out.println("New client arrived. ClientThread starts");
                new Thread(newPlayerThread).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}