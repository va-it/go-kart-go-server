import go_kart_go_network.ServerDetails;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // Multi thread based on: https://stackoverflow.com/a/14771831

    public static void main(String[] args) {

        ServerKarts serverKarts = new ServerKarts();
        int player = 0;

        Thread playerOneThread;
        Thread playerTwoThread;

        try {
            ServerSocket server = new ServerSocket(ServerDetails.port);
            // only allow two clients
            while (player < 2) {
                Socket socket = server.accept();
                // the line above waits for a connection. So, if we are here, it means
                // a client was started and sent a request
                ++player; // the first to connect gets to be player 1
                TCPCommunicationThread TCPCommunicationThread = new TCPCommunicationThread(socket, player);
                if (player == 1) {
                    playerOneThread = new Thread(TCPCommunicationThread);
                    playerOneThread.start();
                } else {
                    playerTwoThread = new Thread(TCPCommunicationThread);
                    playerTwoThread.start();
                }
            }
        } catch (Exception ex) {
            // Something has gone wrong
            System.err.println("Error : " + ex.getMessage());
        }
    }
}
