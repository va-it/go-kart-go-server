import go_kart_go.Kart;
import go_kart_go_network.Messages;
import go_kart_go_network.TCPCommunicationSocket;
import go_kart_go_network.UDPCommunicationSocket;


public class Server {

    TCPCommunicationSocket tcpCommunicationSocket;
    UDPCommunicationSocket udpCommunicationSocket;

    public Server(Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpCommunicationSocket = new TCPCommunicationSocket();
        } else {
            udpCommunicationSocket = new UDPCommunicationSocket(true);
        }
    }

    public void listen() {
        tcpCommunicationSocket.listen();
    }

    public String getMessage(Enum protocol) {
        String message;

        if (protocol.equals(Messages.Protocols.TCP)) {
            message = tcpCommunicationSocket.getMessage();
        } else {
            message = udpCommunicationSocket.getMessage();
        }
        return message;
    }

    public void sendMessage(String message, Enum protocol) {
        if (protocol.equals(Messages.Protocols.TCP)) {
            tcpCommunicationSocket.sendMessage(message);
        } else {
            udpCommunicationSocket.sendMessage(message);
        }
    }

    public Kart getKart() {
        // return (Kart) udpCommunicationSocket.getObject();
        return (Kart) tcpCommunicationSocket.getKart();
    }
}
