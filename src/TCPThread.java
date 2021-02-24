import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.net.Socket;

public class TCPThread implements Runnable {
    Server server;
    Socket clientSocket = null;
    int player;
    Kart kart;
    String message;
    boolean connectionIsOpen;
    private Timer connectionChecker;


    public TCPThread(Socket clientSocket, int player) {
        this.clientSocket = clientSocket;
        this.player = player;
        this.connectionIsOpen = false;
    }

    public void run() {
            System.out.println("Connected to client : "+ clientSocket.getInetAddress().getHostName());

            connectionChecker = new Timer(1000, connectionCheckerListener());

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
                case Messages.ready:
                    server.sendMessage(Messages.readyReceived, Messages.Protocols.TCP);
                    Main.getClientFromPlayerNumber(player).setReady(true);
                case Messages.requestToStart:
                    connectionChecker.start();
                    break;
                case Messages.sendingKartInfo:
                    server.sendMessage(Messages.readyToReceiveKart, Messages.Protocols.TCP);
                    Main.getClientFromPlayerNumber(player).setKart(server.getKart(Messages.Protocols.TCP));
                    server.sendMessage(Messages.kartInfoReceived, Messages.Protocols.TCP);
                    break;
                case Messages.getOpponentSpeed:
                    int speed = getOpponentClient().getSpeed();
                    server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.TCP);
                    System.out.println("Speed: " + Messages.returnSpeed(speed));
                    break;
                case Messages.getOpponentIndex:
                    int index = getOpponentClient().getIndex();
                    server.sendMessage(Messages.returnIndex(index), Messages.Protocols.TCP);
                    System.out.println("Index: " + Messages.returnIndex(index));
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
                    System.err.println("Invalid message: " + message);
            }
        } while(connectionIsOpen);
    }

    private Client getOpponentClient() {
        return Main.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }

    private ActionListener connectionCheckerListener() {
        // replaced new ActionListener() { @Override actionPerformed ... } with lambda expression
        ActionListener actionListener = e -> {
            System.out.println("Checking other client is connected");
            if (getOpponentClient() != null && getOpponentClient().isConnected() && getOpponentClient().isReady()) {
                server.sendMessage(Messages.startRace, Messages.Protocols.TCP);
                connectionChecker.stop();
                System.out.println("Race started");
            }
        };
        return actionListener;
    }
}
