import go_kart_go.Kart;
import go_kart_go_network.TCPServerCommunicationSocket;

public class Server {
    TCPServerCommunicationSocket serverSocket;
    public Server() {
        serverSocket = new TCPServerCommunicationSocket();
        serverSocket.listen();
    }

    public void listen() {
        serverSocket.listen();
    }

    public String getMessage() {
        return serverSocket.getMessage();
    }

    public int getClientPort() {
        return serverSocket.returnClientPort();
    }

    public void sendMessage(String message) {
        serverSocket.respond(message);
    }
}
