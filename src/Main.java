import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class Main {

    public static void main(String[] args) {

        // start server for TCP communications
        Server serverTCP = new Server(Messages.Protocols.TCP);

        ServerKarts serverKarts = new ServerKarts();

        // CREATE THREADS FOR TCP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        TCPCommunicationThread TCPCommunicationThreadPlayerOne = new TCPCommunicationThread(serverTCP, 1, serverKarts.kartPlayerOne);
        TCPCommunicationThread TCPCommunicationThreadPlayerTwo = new TCPCommunicationThread(serverTCP, 2, serverKarts.kartPlayerTwo);

        Thread playerOneTCPThread = new Thread(TCPCommunicationThreadPlayerOne);
        Thread playerTwoTCPThread = new Thread(TCPCommunicationThreadPlayerTwo);

        playerOneTCPThread.start();
        playerTwoTCPThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        // start server for UDP communications
        Server serverUDP = new Server(Messages.Protocols.UDP);

        // CREATE THREADS FOR UDP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        UDPCommunicationThread UDPCommunicationThreadPlayerOne = new UDPCommunicationThread(serverUDP, 1, serverKarts.kartPlayerOne);
        UDPCommunicationThread UDPCommunicationThreadPlayerTwo = new UDPCommunicationThread(serverUDP, 2, serverKarts.kartPlayerTwo);

        Thread playerOneUDPThread = new Thread(UDPCommunicationThreadPlayerOne);
        Thread playerTwoUDPThread = new Thread(UDPCommunicationThreadPlayerTwo);

        playerOneUDPThread.start();
        playerTwoUDPThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    }
}
