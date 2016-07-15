package mariya.dimitrova.multithreading;

/**
 * A class that contains the main method where the program starts.
 * 
 * @author mariyadimitrova
 *
 */
public class Engine {
    /**
     * The main method that creates and starts the server.
     */
    public static void main(String[] args) {
        BCServer myServer = new BCServer();
        Thread newServer = new Thread(myServer);
        newServer.start();
    }
}