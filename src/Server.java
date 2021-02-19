import go_kart_go.Kart;
import go_kart_go_network.TCPServerCommunicationSocket;
import go_kart_go_network.UDPCommunicationSocket;


public class Server {

    TCPServerCommunicationSocket serverSocket;
    UDPCommunicationSocket serverUDPSocket;

    public Server() {
        serverSocket = new TCPServerCommunicationSocket();
        serverUDPSocket = new UDPCommunicationSocket();
    }

    public void listen() {
        serverSocket.listen();
    }

    public String getMessage() {
        String message;
        message = serverSocket.getMessage();
        if (message == null) {
            message = serverUDPSocket.getMessage();
        }
        return message;
    }

    public void sendMessage(String message, String protocol) {
        if (protocol.equals("TCP")) {
            serverSocket.respond(message);
        } else {
            // how do I get the client address and port???
            // serverUDPSocket.respond(message, address, port);
        }

    }

    public Kart getKart() {
        return (Kart)serverSocket.getKart();
    }
}
