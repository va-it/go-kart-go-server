import go_kart_go.Kart;
import go_kart_go_network.Messages;

import java.net.Socket;

public class TCPCommunicationThread implements Runnable {
    Server server;
    Socket clientSocket = null;
    int player;
    Kart kart;
    String message;
    boolean connectionIsOpen;


    public TCPCommunicationThread(Socket clientSocket, int player) {
        this.clientSocket = clientSocket;
        this.player = player;
        this.connectionIsOpen = false;
    }
    public void run() {
            System.out.println("Connected to client : "+ clientSocket.getInetAddress().getHostName());

            this.server = new Server(Messages.Protocols.TCP, this.clientSocket);
            this.server.tcpServer.OpenInputOutputStreams();

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
                case Messages.startRace:
                    server.sendMessage(Messages.confirmRaceStarted, Messages.Protocols.TCP);
                    System.out.println("Race started");
                    break;
                case Messages.stopRace:
                    server.sendMessage(Messages.confirmRaceStopped, Messages.Protocols.TCP);
                    System.out.println("Race stopped");
                    break;
                case Messages.closeConnection:
                    connectionIsOpen = false;
                    System.out.println("Close connection");
                    break;
                default:
                    if (!isPlayerSpecific(message)) {
                        System.err.println("Invalid message: " + message);
                    }
            }
        } while(connectionIsOpen);
    }

    public boolean isPlayerSpecific(String message) {
        if (message.equals(Messages.sendingKartInfo(player))) {
            server.sendMessage(Messages.readyToReceiveKart(player), Messages.Protocols.TCP);
            this.kart = server.getKart();
            // here I need to check if this is a proper kart or just an empty object
            System.out.println("Kart received: " + kart.getPlayer());
            server.sendMessage(Messages.kartInfoReceived(player), Messages.Protocols.TCP);
            return true;
        }
        if (message.equals(Messages.getOpponentSpeed(player))) {
            int speed = ServerKarts.getOpponentSpeed(player);
            server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.TCP);
            System.out.println("Speed: " + Messages.returnSpeed(speed));
            return true;
        }
        if (message.equals(Messages.getOpponentIndex(player))) {
            int index = ServerKarts.getOpponentIndex(player);
            server.sendMessage(Messages.returnIndex(index), Messages.Protocols.TCP);
            System.out.println("Index: " + Messages.returnIndex(index));
            return true;
        }
        return false;
    }
}
