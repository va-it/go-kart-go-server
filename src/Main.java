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

        CommunicationThread communicationThreadPlayerOne = new CommunicationThread();
        CommunicationThread communicationThreadPlayerTwo = new CommunicationThread();

        Thread playerOneThread = null;
        Thread playerTwoThread = null;

        // start server (listening...)
        Server server = new Server();

        boolean connectionOpen = true;
        String message;

        while(connectionOpen) {
            message = server.getMessage();

            if (message.equals(Messages.establishConnection)) {
                server.sendMessage(Messages.connectionSuccessful);
                // start threads when user connects. Player 1 is given to whoever connected first
                if (playerOneThread == null) {
                    communicationThreadPlayerOne.player = 1;
                    playerOneThread = new Thread(communicationThreadPlayerOne);
                    playerOneThread.start();
                    server.sendMessage(Messages.returnPlayerNumber(1));
                } else {
                    if (playerTwoThread == null) {
                        communicationThreadPlayerTwo.player = 2;
                        playerTwoThread = new Thread(communicationThreadPlayerTwo);
                        playerTwoThread.start();
                        server.sendMessage(Messages.returnPlayerNumber(1));
                    }
                }
            }

            if (message.equals(Messages.sendingKartInfo)) {
                Kart kart = server.getKart();
                System.out.println("Kart: " + kart.getPlayer());
                server.sendMessage(Messages.kartInfoReceived);
            }

            if (message.equals(Messages.closeConnection)) {
                connectionOpen = false;
            }
        }

        // System.out.println (ServerDetails.getAddress());
        // System.out.println (ServerDetails.port);


    }
}
