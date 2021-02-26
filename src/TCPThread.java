import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;

import java.net.Socket;

public class TCPThread implements Runnable {
    Server server;
    Socket clientSocket = null;
    int player;
    String message;
    boolean connectionIsOpen;
    Kart kart;

    public TCPThread(Socket clientSocket, int player) {
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

            System.out.println("TCP thread " + player);

            switch (message) {
                case Messages.getPlayerNumber:
                    server.sendMessage(Messages.returnPlayerNumber(player), Messages.Protocols.TCP);
                    break;
                case Messages.ready:
                    server.sendMessage(Messages.readyReceived, Messages.Protocols.TCP);
                    Main.getClientFromPlayerNumber(player).setReady(true);
                    break;
                case Messages.requestToStart:
                    if (getOpponentClient() != null && getOpponentClient().isReadyToStart()) {
                        server.sendMessage(Messages.startRace, Messages.Protocols.TCP);
                        System.out.println("Race started");
                    } else {
                        server.sendMessage(Messages.wait, Messages.Protocols.TCP);
                        System.out.println("Wait");
                    }
                    break;
                case Messages.running:
                    System.out.println(player + " running");
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
                    //if (!isPlayerSpecific(message)) {
                        System.err.println("Invalid message: " + message);
                    //}
            }
        } while(connectionIsOpen);
    }

//    private boolean isPlayerSpecific(String message) {
//        if (message.equals(Messages.sendingKartInfo(player))) {
//            server.sendMessage(Messages.readyToReceiveKart(player), Messages.Protocols.TCP);
//            kart = server.getKart(Messages.Protocols.TCP);
//            if (kart != null) {
//                try {
//                    System.out.println("I am player " + player + " and I am setting player " + kart.getPlayer());
//                    System.out.println("player " + player + " speed: " + kart.getSpeed());
//                    System.out.println("player " + player + " index: " + kart.getSpeed());
//                    Main.getClientFromPlayerNumber(player).setKart(kart);
//                } catch (NullPointerException e) {
//                    System.err.println("Object corrupt: " + e);
//                }
//            }
//            return true;
//        }
//        if (message.equals(Messages.getOpponentSpeed(player))) {
//            if (getOpponentClient() != null) {
//                int speed = getOpponentClient().getSpeed();
//                server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.TCP);
//                System.out.println("Speed: " + Messages.returnSpeed(speed));
//            }
//            return true;
//        }
//
//        if (message.equals(Messages.getOpponentIndex(player))) {
//            if (getOpponentClient() != null) {
//                int index = getOpponentClient().getIndex();
//                server.sendMessage(Messages.returnIndex(index), Messages.Protocols.TCP);
//                System.out.println("Index: " + Messages.returnIndex(index));
//            }
//            return true;
//        }
//        return false;
//    }

    private Client getOpponentClient() {
        return Main.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }
}
