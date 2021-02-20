import go_kart_go.Kart;
import go_kart_go_network.TCPCommunicationSocket;
import go_kart_go_network.UDPCommunicationSocket;


public class Server {

    TCPCommunicationSocket tcpCommunicationSocket;
    UDPCommunicationSocket udpCommunicationSocket;

    public Server(String protocol) {
        if (protocol.equals("TCP")) {
            tcpCommunicationSocket = new TCPCommunicationSocket();
        } else {
            udpCommunicationSocket = new UDPCommunicationSocket();
        }
    }

    public void listen() {
        tcpCommunicationSocket.listen();
    }

    public String getMessage(String protocol) {
        String message;

        if (protocol.equals("TCP")) {
            message = tcpCommunicationSocket.getMessage();
        } else {
            message = udpCommunicationSocket.getMessage();
        }
        return message;
    }

    public void sendMessage(String message, String protocol) {
        if (protocol.equals("TCP")) {
            tcpCommunicationSocket.sendMessage(message);
        } else {
            udpCommunicationSocket.sendMessage(message);
        }
    }

    public Kart getKart() {
        return (Kart) udpCommunicationSocket.getObject();
    }
}
