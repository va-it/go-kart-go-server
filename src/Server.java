import go_kart_go.Kart;
import go_kart_go_network.PacketReceiver;
import go_kart_go_network.PacketSender;
import go_kart_go_network.TCPServerCommunicationSocket;
import go_kart_go_network.UDPCommunicationSocket;

import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {

    TCPServerCommunicationSocket serverSocket;
    UDPCommunicationSocket serverUDPSocket;
    PacketReceiver packetReceiver;

    public Server() {
        serverSocket = new TCPServerCommunicationSocket();
        serverUDPSocket = new UDPCommunicationSocket();
    }

    public void listen() {
        serverSocket.listen();
    }

    public String getMessage() {
        return serverSocket.getMessage();
    }

    public void sendMessage(String message) {
        serverSocket.respond(message);
    }

    public String getUDPMessage() {
        return PacketReceiver.receivePacket(serverUDPSocket.socket);
    }

    public void sendUDPMessage(String message, InetAddress receiverAddress, int receiverPort) {
        PacketSender.sendPacket(message, receiverAddress, receiverPort, serverUDPSocket.socket);
    }

    public Kart getKart() {
        return (Kart)serverSocket.getKart();
    }
}
