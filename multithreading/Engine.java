package mariya.dimitrova.multithreading;

public class Engine {
    /**
     * the stupid method
     */
    public static void main(String[] args) {
        // create and start server
        BCServer myServer = new BCServer();
        Thread newServer = new Thread(myServer);
        newServer.start();
    }
}