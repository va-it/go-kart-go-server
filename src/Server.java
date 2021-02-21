import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.TCPServer;
import go_kart_go_network.UDPCommunicationSocket;

import java.net.Socket;


public class Server {

    TCPServer tcpServer;
    UDPCommunicationSocket udpCommunicationSocket;

    public Server(Enum protocol, Socket clientSocket) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer = new TCPServer(clientSocket);
        } else {
            udpCommunicationSocket = new UDPCommunicationSocket(true);
        }
    }

    public String getMessage(Enum protocol) {
        String message;

        if (protocol.equals(Messages.Protocols.TCP)) {
            message = tcpServer.getRequest();
        } else {
            message = udpCommunicationSocket.getMessage();
        }
        return message;
    }

    public void sendMessage(String message, Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer.sendResponse(message);
        } else {
            udpCommunicationSocket.sendMessage(message);
        }
    }

    public Kart getKart() {
        try {
            return (Kart) tcpServer.getObject();
        } catch (ClassCastException e) {
            System.err.println(e);
        }
        return new Kart();
    }
}
