package mariya.dimitrova.multithreading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BCServer implements Runnable {
    private static final int PORT = 3008;
    private Players waitingPlayers;

    public BCServer() {
        this.waitingPlayers = new Players();
    }

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