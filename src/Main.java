import go_kart_go_network.UDPCommunicationSocket;

public class Main {

    public static void main(String[] args) {

        // Develop the multi-threaded server program. More than one solution is possible,but here is one:
        // The server program needs two user-defined objects for each connection to the clients,
        // each of which is passed to a thread, this may include input/output streams.

        // start server
        Server server = new Server();

        // CREATE THREADS FOR TCP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        TCPCommunicationThread TCPCommunicationThreadPlayerOne = new TCPCommunicationThread(server, 1);
        TCPCommunicationThread TCPCommunicationThreadPlayerTwo = new TCPCommunicationThread(server, 2);

        Thread playerOneTCPThread = new Thread(TCPCommunicationThreadPlayerOne);
        Thread playerTwoTCPThread = new Thread(TCPCommunicationThreadPlayerTwo);

        playerOneTCPThread.start();
        // playerTwoThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&


        // CREATE THREADS FOR UDP COMMUNICATION &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
        UDPCommunicationThread UDPCommunicationThreadPlayerOne = new UDPCommunicationThread(server, 1);
        UDPCommunicationThread UDPCommunicationThreadPlayerTwo = new UDPCommunicationThread(server, 2);

        Thread playerOneUDPThread = new Thread(UDPCommunicationThreadPlayerOne);
        Thread playerTwoUDPThread = new Thread(UDPCommunicationThreadPlayerTwo);

        playerOneUDPThread.start();
        // playerTwoThread.start();
        // &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&

    }
}
