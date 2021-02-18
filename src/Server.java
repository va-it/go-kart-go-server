import go_kart_go.Kart;
import go_kart_go_network.ServerCommunicationSocket;

public class Server {
    ServerCommunicationSocket serverSocket;
    public Server() {
        serverSocket = new ServerCommunicationSocket();
        serverSocket.listen();
        serverSocket.openInputOutputChannels();
    }

    public String getMessage() {
        return serverSocket.getMessage();
    }

    public void sendMessage(String message) {
        serverSocket.respond(message);
    }
}
