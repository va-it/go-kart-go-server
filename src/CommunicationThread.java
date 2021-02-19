
import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class CommunicationThread implements Runnable {

    int player;
    Server server;

    public CommunicationThread(Server server, int player) {
        this.server = server;
        this.player = player;
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
                server.sendMessage(Messages.connectionSuccessful, "TCP");
                server.sendMessage(Messages.returnPlayerNumber(player), "TCP");
            }

            if (message.equals(Messages.sendingKartInfo)) {
                Kart kart = server.getKart();
                System.out.println("Kart: " + kart.getPlayer());
                server.sendMessage(Messages.kartInfoReceived, "TCP");
            }

            if (message.equals(Messages.getOpponentSpeed)) {
                System.out.println("Need to return the other player's speed");
                server.sendMessage(Messages.returnSpeed(10), "TCP");
            }

            if (message.equals(Messages.closeConnection)) {
                connectionOpen = false;
            }
        }

        // System.out.println (ServerDetails.getAddress());
        // System.out.println (ServerDetails.port);



    }
}