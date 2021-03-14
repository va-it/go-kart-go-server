import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.TCPServer;
import go_kart_go_network.UDPSocket;

import java.net.InetAddress;
import java.net.Socket;

public class Server {

    TCPServer tcpServer;
    UDPSocket udpSocket;

    public Server(Enum protocol, Socket clientSocket) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer = new TCPServer(clientSocket);
        }
    }

    public Server(Enum protocol, UDPSocket udpSocket) {
        if (protocol.equals(Messages.Protocols.UDP)) {
            this.udpSocket = udpSocket;
        }
    }

    public String getMessage(Enum protocol) {
        String message;

        if (protocol.equals(Messages.Protocols.TCP)) {
            message = tcpServer.getRequest();
        } else {
            message = udpSocket.getMessage(true);
        }
        return message;
    }

    public void sendMessage(String message, Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer.sendResponse(message);
        }
    }

    public void sendMessage(String message, Enum protocol, InetAddress clientAddress, int clientPort) {
         if (protocol.equals(Messages.Protocols.UDP)) {
            udpSocket.sendMessage(message, clientAddress, clientPort);
        }
    }

    public Kart getKart(Enum protocol) {
        try {
            if (protocol.equals(Messages.Protocols.TCP)) {
                return (Kart) tcpServer.getObject();
            } else {
                return (Kart) udpSocket.getObject();
            }
        } catch (ClassCastException e) {
            System.err.println("Unable to create kart: " + e);
        }
        return null;
    }

    public void sendKart(Enum protocol, Kart kart) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer.sendObject(kart);
        }
    }

    public void sendKart(Enum protocol, Kart kart, InetAddress clientAddress, int clientPort) {
        if (protocol.equals(Messages.Protocols.UDP)) {
            udpSocket.sendObject(kart, clientAddress, clientPort);
        }
    }

    public InetAddress getClientAddress() {
        return udpSocket.packetReceiver.packet.getAddress();
    }

    public int getClientPort() {
        return udpSocket.packetReceiver.packet.getPort();
    }
}
