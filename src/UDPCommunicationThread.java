import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class UDPCommunicationThread implements Runnable {

    int player;
    Server server;
    String protocol = "UDP";



    public UDPCommunicationThread(Server server, int player) {
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

        boolean connectionOpen = true;
        String message;

        while(connectionOpen) {
            message = server.getMessage(protocol);

            if (message.equals(Messages.sendingKartInfo)) {
                Kart kart = server.getKart();
                System.out.println("Kart: " + kart.getPlayer());
                server.sendMessage(Messages.kartInfoReceived, protocol);
            }

            if (message.equals(Messages.getOpponentSpeed)) {
                System.out.println("Need to return the other player's speed");
                server.sendMessage(Messages.returnSpeed(10), protocol);
            }

            if (message.equals(Messages.getOpponentIndex)) {
                System.out.println("Need to return the other player's speed");
                server.sendMessage(Messages.returnIndex(1), protocol);
            }
        }

        // System.out.println (ServerDetails.getAddress());
        // System.out.println (ServerDetails.port);



    }
}
