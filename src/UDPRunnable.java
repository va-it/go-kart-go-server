import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.UDPSocket;

import java.net.InetAddress;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UDPRunnable implements Runnable {

    Server server;
    Kart kart;
    String message;
    UDPSocket udpSocket;
    InetAddress clientInetAddress;
    int clientPort = 0;
    private Lock key;

    public UDPRunnable(UDPSocket udpSocket) {
        this.udpSocket = udpSocket;
        this.key = new ReentrantLock();
    }

    @Override
    public void run() {

        this.server = new Server(Messages.Protocols.UDP, udpSocket);

        do {
            key.lock();

            try {
                message = server.getMessage(Messages.Protocols.UDP);

                clientInetAddress = server.getClientAddress();
                clientPort = server.getClientPort();

                if (message.equals(Messages.sendingKartInfo)) {
                    kart = server.getKart(Messages.Protocols.UDP);
                    if (kart != null) {
                        GameLogic.getClientFromPlayerNumber(kart.getPlayer()).setKart(kart);

                        // check here for collisions
                        // Main.checkCollisions();
                    }
                }

                if (message.equals(Messages.getOpponentKartInfo(1))) {
                    sendKartToClient(1);
                }

                if (message.equals(Messages.getOpponentKartInfo(2))) {
                    sendKartToClient(2);
                }
            } finally {
                key.unlock();
            }
            // keep listening for messages until the socket is closed
        } while(!this.udpSocket.socketIsClosed());
    }

    private void sendKartToClient(int player) {
        if (getOpponentClient(player) != null) {
            Kart kart = GameLogic.getClientFromPlayerNumber(player).getKart();
            if (kart != null) {
                server.sendKart(Messages.Protocols.UDP, kart, clientInetAddress, clientPort);
            }
        }
    }

    private Client getOpponentClient(int player) {
        return GameLogic.getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }

    public void stopListening() {
        this.udpSocket.closeSocket();
    }
}