import go_kart_go_network.Messages;
import go_kart_go_network.ServerDetails;
import go_kart_go.*;

public class Main {

    public static void main(String[] args) {

        // listen for connections
        // when a new client (based on player?) connects, then create a new Object to pass to thread
//        CommunicationThread communicationThreadOne = new CommunicationThread();
//        CommunicationThread communicationThreadTwo = new CommunicationThread();
//
//        Thread t1 = new Thread(communicationThreadOne);
//        t1.start() ;
//
//        Thread t2 = new Thread(communicationThreadTwo);
//        t2.start();

        // System.out.println (ServerDetails.getAddress());
        // System.out.println (ServerDetails.port);

        // start server (listening...)
        Server server = new Server();

        boolean connectionOpen = true;
        String message;

        while(connectionOpen) {
            message = server.getMessage();

            if (message.equals(Messages.establishConnection)) {
                server.sendMessage(Messages.connectionSuccessful);
            }

            if (message.equals(Messages.closeConnection)) {
                connectionOpen = false;
            }
        }

    }
}
