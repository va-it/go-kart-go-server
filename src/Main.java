import go_kart_go_network.Messages;
import go_kart_go_network.ServerDetails;
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

        CommunicationThread communicationThreadPlayerOne = new CommunicationThread();
        CommunicationThread communicationThreadPlayerTwo = new CommunicationThread();

        Thread playerOneThread = new Thread(communicationThreadPlayerOne);
        Thread playerTwoThread = new Thread(communicationThreadPlayerTwo);

        playerOneThread.start();
        playerTwoThread.start();

        // start server (listening...)
        Server server = new Server();

        boolean connectionOpen = true;
        String message;

        while(connectionOpen) {
            message = server.getMessage();

            if (message.equals(Messages.establishConnection)) {
                server.sendMessage(Messages.connectionSuccessful);
                if (playerOneThread.isAlive()) {
                    communicationThreadPlayerOne.player = 1;
                    server.sendMessage(Messages.returnPlayerNumber(1));
                } else {
                    if (playerTwoThread.isAlive()) {
                        communicationThreadPlayerTwo.player = 2;
                        server.sendMessage(Messages.returnPlayerNumber(2));
                    }
                }
            }

            if (message.equals(Messages.closeConnection)) {
                connectionOpen = false;
            }
        }

        // System.out.println (ServerDetails.getAddress());
        // System.out.println (ServerDetails.port);


    }
}
