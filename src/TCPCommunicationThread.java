import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class TCPCommunicationThread implements Runnable {

    int player;
    Server server;
    Kart kart;
    String message;
    boolean connectionIsOpen;

    public TCPCommunicationThread(Server server, int player) {
        this.server = server;
        this.player = player;
        this.kart = ServerKarts.getKartFromPlayerNumber(player);
        this.connectionIsOpen = false;
    }

    @Override
    public void run() {
        System.out.print("TCP comms thread for player " + player + " is running..\n");

        // Each server object’s threaded run() method sends kart data to one client via one outputstream
        // and receives the kartdata via one inputstream from another client.

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.

        this.server.listen();

        message = server.getMessage(Messages.Protocols.TCP);

        if (message.equals(Messages.establishConnection)) {
            server.sendMessage(Messages.connectionSuccessful, Messages.Protocols.TCP);
            connectionIsOpen = true;
            listenForMessages();
        }
    }

    private void listenForMessages() {
        do {
            message = server.getMessage(Messages.Protocols.TCP);
            switch (message) {
                case Messages.getPlayerNumber:
                    server.sendMessage(Messages.returnPlayerNumber(player), Messages.Protocols.TCP);
                    break;
                case Messages.sendingKartInfo:
                    server.sendMessage(Messages.sendingKartInfo, Messages.Protocols.TCP);
                    Kart kart = server.getKart();
                    server.sendMessage(Messages.sendingKartInfo, Messages.Protocols.TCP);
                    System.out.println("Kart: " + kart.getPlayer());
                    break;
                case Messages.startRace:
                    server.sendMessage(Messages.confirmRaceStarted, Messages.Protocols.TCP);
                    break;
                case Messages.stopRace:
                    server.sendMessage(Messages.confirmRaceStopped, Messages.Protocols.TCP);
                    break;
                case Messages.closeConnection:
                    connectionIsOpen = false;
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        } while(connectionIsOpen);
    }
}