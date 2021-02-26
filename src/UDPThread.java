import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.UDPSocket;

import java.net.InetAddress;

public class UDPThread implements Runnable {

    int player;
    Server server;
    Kart kart;
    String message;
    UDPSocket udpSocket;
    InetAddress clientInetAddress;
    int clientPort = 0;

    public UDPThread(UDPSocket udpSocket, int player) {
        this.player = player;
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {

        this.server = new Server(Messages.Protocols.UDP, udpSocket);

        do {
            message = server.getMessage(Messages.Protocols.UDP);

            clientInetAddress = server.getClientAddress();
            clientPort = server.getClientPort();

            System.out.println("UDP thread " + player);

            if (message.equals(Messages.sendingKartInfo(player))) {
                // server.sendMessage(Messages.readyToReceiveKart(player), Messages.Protocols.UDP, clientInetAddress, clientPort);
                kart = server.getKart(Messages.Protocols.UDP);
                if (kart != null) {
                    try {
                        System.out.println("I am player " + player + " and I am setting player " + kart.getPlayer());
                        Main.getClientFromPlayerNumber(player).setKart(kart);
                    } catch (NullPointerException e) {
                        System.err.println("Object corrupt: " + e);
                    }
                }
                break;
            }
            if (message.equals(Messages.getOpponentSpeed(player))) {
                if (getOpponentClient() != null) {
                    int speed = getOpponentClient().getSpeed();
                    server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.UDP, clientInetAddress, clientPort);
                    System.out.println("Speed: " + Messages.returnSpeed(speed));
                }
                break;
            }

            if (message.equals(Messages.getOpponentIndex(player))) {
                if (getOpponentClient() != null) {
                    int index = getOpponentClient().getIndex();
                    server.sendMessage(Messages.returnIndex(index), Messages.Protocols.UDP, clientInetAddress, clientPort);
                    System.out.println("Index: " + Messages.returnIndex(index));
                }
                break;
            }
        } while(true);
    }

    private Client getOpponentClient() {
        return Main.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }
}
