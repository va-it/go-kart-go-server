import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class UDPCommunicationThread implements Runnable {

    int player;
    Server server;
    Kart kart;
    String message;

    public UDPCommunicationThread(Server server, int player) {
        this.server = server;
        this.player = player;
        this.kart = ServerKarts.getKartFromPlayerNumber(player);
    }

    @Override
    public void run() {
        System.out.print("UDP comms thread for player " + player + " is running..\n");

        // Each server object’s threaded run() method sends kart data to one client via one outputstream
        // and receives the kartdata via one inputstream from another client.

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.

        do {
            message = server.getMessage(Messages.Protocols.UDP);

            switch (message) {
                case Messages.getOpponentSpeed:
                    int speed = ServerKarts.getOpponentSpeed(player);
                    server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.UDP);
                    break;
                case Messages.getOpponentIndex:
                    int index = ServerKarts.getOpponentIndex(player);
                    server.sendMessage(Messages.returnIndex(index), Messages.Protocols.UDP);
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        } while(true);
    }
}
