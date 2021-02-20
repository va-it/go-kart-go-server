
import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class TCPCommunicationThread implements Runnable {

    int player;
    Server server;
    Kart kart;

    public TCPCommunicationThread(Server server, int player) {
        this.server = server;
        this.player = player;
        if (player == 1) {
            this.kart = ServerKarts.kartPlayerOne;
        } else {
            this.kart = ServerKarts.kartPlayerOne;
        }
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

        while(connectionOpen) {
            message = server.getMessage(Messages.Protocols.TCP);

            switch (message) {
                case Messages.establishConnection:
                    // tell the client that the connection was successull and return their player number
                    server.sendMessage(Messages.connectionSuccessful, Messages.Protocols.TCP);
                    server.sendMessage(Messages.returnPlayerNumber(player), Messages.Protocols.TCP);
                    break;
                case Messages.getPlayerNumber:
                    server.sendMessage(Messages.returnPlayerNumber(player), Messages.Protocols.TCP);
                    break;
                case Messages.startRace:
                    server.sendMessage(Messages.confirmRaceStarted, Messages.Protocols.TCP);
                    break;
                case Messages.stopRace:
                    server.sendMessage(Messages.confirmRaceStopped, Messages.Protocols.TCP);
                    break;
                case Messages.closeConnection:
                    connectionOpen = false;
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        }
    }
}