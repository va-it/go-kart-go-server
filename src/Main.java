import go_kart_go_network.Messages;
import go_kart_go.*;

public class Main {

    public static void main(String[] args) {

        // Develop the multi-threaded server program. More than one solution is possible,but here is one:
        // The server program needs two user-defined objects for each connection to the clients,
        // each of which is passed to a thread, this may include input/output streams.

        //  CommunicationThread communicationThreadPlayerOne;
        //  CommunicationThread communicationThreadPlayerTwo;
        //
        //  Thread t1 = new Thread(communicationThreadOne);
        //  t1.start() ;
        //
        //  Thread t2 = new Thread(communicationThreadTwo);
        //  t2.start();

        // start server
        Server server = new Server();

        CommunicationThread communicationThreadPlayerOne = new CommunicationThread(server);
        CommunicationThread communicationThreadPlayerTwo = new CommunicationThread(server);

        Thread playerOneThread = new Thread(communicationThreadPlayerOne);
        Thread playerTwoThread = new Thread(communicationThreadPlayerTwo);

        playerOneThread.start();
        // playerTwoThread.start();

    }
}
