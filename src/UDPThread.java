import go_kart_go.Kart;
import go_kart_go_network.Messages;

import java.io.StreamCorruptedException;
import java.net.Socket;

public class UDPThread implements Runnable {

    int player;
    Server server;
    Kart kart;
    String message;
    Socket clientSocket;

    public UDPThread(Socket clientSocket, int player) {
        this.player = player;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        this.server = new Server(Messages.Protocols.UDP, clientSocket);

        do {
            message = server.getMessage(Messages.Protocols.UDP);

            switch (message) {
                case Messages.sendingKartInfo:
                    server.sendMessage(Messages.readyToReceiveKart, Messages.Protocols.UDP);
                    kart = server.getKart(Messages.Protocols.UDP);
                    try {
                        Main.getClientFromPlayerNumber(player).setKart(kart);
                    } catch (NullPointerException e) {
                        System.err.println("Object corrupt: " + e);
                    }
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        } while(true);
    }
}
