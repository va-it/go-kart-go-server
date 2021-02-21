import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.TCPServer;
import go_kart_go_network.UDPCommunicationSocket;


public class Server {

    TCPServer tcpServer;
    UDPCommunicationSocket udpCommunicationSocket;

    public Server(Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpServer = new TCPServer();
        } else {
            udpCommunicationSocket = new UDPCommunicationSocket(true);
        }
    }

    public void listen() {
        tcpServer.listen();
    }

    public String getMessage(Enum protocol) {
        String message;

        if (protocol.equals(Messages.Protocols.TCP)) {
            // message = tcpCommunicationSocket.getMessage();
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
        return (Kart) tcpServer.getObject();
    }
}
