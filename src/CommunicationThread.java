
import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.PacketReceiver;

import java.net.Socket;

public class CommunicationThread implements Runnable {

    int player;
    // start server (listening...)
    Server server;

    public CommunicationThread(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        System.out.print("This thread is running..");

        // Each server object’s threaded run() method sends kart data to one client via one outputstream
        // and receives the kartdata via one inputstream from another client.

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.

        this.server.listen();

        boolean connectionOpen = true;
        String message;
        String udpMessage;

        while(connectionOpen) {
            message = server.getMessage();

            // calling this obviously puts the server in listeining mode...
            // udpMessage = server.getUDPMessage();

            if (message.equals(Messages.establishConnection)) {
                // tell the client that the connection was successull and return their player number
                server.sendMessage(Messages.connectionSuccessful);
                server.sendMessage(Messages.returnPlayerNumber(1));
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