package mariya.dimitrova.multithreading;

public class Engine {

    public static void main(String[] args) {
        // create and start server
        BCServer myServer = new BCServer();
        Thread newServer = new Thread(myServer);
        newServer.start();
    }

}
