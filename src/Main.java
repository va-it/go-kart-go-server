import go_kart_go_network.PacketReceiver;
import go_kart_go_network.UDPCommunicationSocket;

public class Main {

    public static void main(String[] args) {

//        UDPCommunicationSocket udpCommunicationSocket = new UDPCommunicationSocket(true);
//        PacketReceiver receiver = new PacketReceiver();
//        receiver.receivePacket(udpCommunicationSocket.socket);

        // Develop the multi-threaded server program. More than one solution is possible,but here is one:
        // The server program needs two user-defined objects for each connection to the clients,
        // each of which is passed to a thread, this may include input/output streams.

        // start server
        Server serverTCP = new Server("TCP");

        // CREATE THREADS FOR TCP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        TCPCommunicationThread TCPCommunicationThreadPlayerOne = new TCPCommunicationThread(serverTCP, 1);
        TCPCommunicationThread TCPCommunicationThreadPlayerTwo = new TCPCommunicationThread(serverTCP, 2);

        Thread playerOneTCPThread = new Thread(TCPCommunicationThreadPlayerOne);
        Thread playerTwoTCPThread = new Thread(TCPCommunicationThreadPlayerTwo);

        playerOneTCPThread.start();
        // playerTwoThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        // start server
        Server serverUDP = new Server("UDP");

        // CREATE THREADS FOR UDP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        UDPCommunicationThread UDPCommunicationThreadPlayerOne = new UDPCommunicationThread(serverUDP, 1);
        UDPCommunicationThread UDPCommunicationThreadPlayerTwo = new UDPCommunicationThread(serverUDP, 2);

        Thread playerOneUDPThread = new Thread(UDPCommunicationThreadPlayerOne);
        Thread playerTwoUDPThread = new Thread(UDPCommunicationThreadPlayerTwo);

        playerOneUDPThread.start();
        // playerTwoThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    }
}
