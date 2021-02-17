import go_kart_go.Kart;
import go_kart_go_network.ServerCommunicationSocket;

public class Server {
    ServerCommunicationSocket serverSocket;
    public Server() {
        serverSocket = new ServerCommunicationSocket();
        serverSocket.listen();
    }

    public String getMessage() {
        return serverSocket.getMessage();
    }
}
