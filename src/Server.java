import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.TCPServer;
import go_kart_go_network.UDPSocket;

import java.net.DatagramSocket;
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
            message = udpSocket.getMessage();
        }
        return message;
    }

    public void sendMessage(String message, Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer.sendResponse(message);
        } else {
            udpSocket.sendMessage(message);
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
            System.err.println(e);
        }
        return new Kart();
    }
}
