import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.UDPSocket;

import java.net.InetAddress;

public class UDPRunnable implements Runnable {

    Server server;
    Kart kart;
    String message;
    UDPSocket udpSocket;
    InetAddress clientInetAddress;
    int clientPort = 0;

    public UDPRunnable(UDPSocket udpSocket) {
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

            if (message.equals(Messages.getOpponentKartInfo(1))) {
                sendKartToClient(1);
            }

            if (message.equals(Messages.getOpponentKartInfo(2))) {
                sendKartToClient(2);
            }
            // keep listening for messages until the socket is closed
        } while(!this.udpSocket.socketIsClosed());
    }

    private void sendKartToClient(int player) {
        if (getOpponentClient(player) != null) {
            Kart kart = Main.getClientFromPlayerNumber(player).getKart();
            server.sendKart(Messages.Protocols.UDP, kart, clientInetAddress, clientPort);
        }
    }

    private Client getOpponentClient(int player) {
        return Main.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }

    public void stopListening() {
        this.udpSocket.closeSocket();
    }
}