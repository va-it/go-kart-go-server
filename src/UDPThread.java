import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.UDPSocket;

import java.net.InetAddress;

public class UDPThread implements Runnable {

    Server server;
    Kart kart;
    String message;
    UDPSocket udpSocket;
    InetAddress clientInetAddress;
    int clientPort = 0;

    public UDPThread(UDPSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Override
    public void run() {

        this.server = new Server(Messages.Protocols.UDP, udpSocket);

        do {
            message = server.getMessage(Messages.Protocols.UDP);

            clientInetAddress = server.getClientAddress();
            clientPort = server.getClientPort();

            if (message.equals(Messages.sendingKartInfo)) {
                kart = server.getKart(Messages.Protocols.UDP);
                if (kart != null) {
                    Main.getClientFromPlayerNumber(kart.getPlayer()).setKart(kart);
                }
            }

            if (message.equals("get_kart_" + 1)) {
                if (getOpponentClient(1) != null) {
                    Kart kart = Main.getClientFromPlayerNumber(1).getKart();
                    server.sendKart(Messages.Protocols.UDP, kart, clientInetAddress, clientPort);
                }
            }

            if (message.equals("get_kart_" + 2)) {
                if (getOpponentClient(2) != null) {
                    Kart kart = Main.getClientFromPlayerNumber(2).getKart();
                    server.sendKart(Messages.Protocols.UDP, kart, clientInetAddress, clientPort);
                }
            }

        } while(true);
    }

    private Client getOpponentClient(int player) {
        return Main.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }
}