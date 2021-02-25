import go_kart_go.Kart;
import go_kart_go_network.Messages;

import java.net.Socket;

public class UDPThread implements Runnable {

    int player;
    Server server;
    Kart kart;
    String message;

    public UDPThread(int player) {
        this.player = player;
    }

    @Override
    public void run() {

        this.server = new Server(Messages.Protocols.UDP, null);

        do {
            message = server.getMessage(Messages.Protocols.UDP);

            System.out.println("UDP thread " + player);

            switch (message) {
                case Messages.sendingKartInfo:
                    server.sendMessage(Messages.readyToReceiveKart, Messages.Protocols.UDP);
                    kart = server.getKart(Messages.Protocols.UDP);
                    try {
                        System.out.println("I am player " + player + " and I am setting player " + kart.getPlayer());
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
