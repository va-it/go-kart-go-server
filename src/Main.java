import go_kart_go_network.ServerDetails;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // Multi thread based on: https://stackoverflow.com/a/14771831

    public static void main(String[] args) {

        ServerKarts serverKarts = new ServerKarts();
        int player = 0;

        Thread playerOneThread = null;
        Thread playerTwoThread = null;

        try {
            ServerSocket server = new ServerSocket(ServerDetails.port);
            while (player < 2) {
                Socket socket = server.accept();
                // HERE WE HAVE CONNECTED WITH A CLIENT SO LET'S START A THREAD
                ++player;
                TCPCommunicationThread TCPCommunicationThread = new TCPCommunicationThread(socket, player);
                if (player == 1) {
                    playerOneThread = new Thread(TCPCommunicationThread);
                } else {
                    playerTwoThread = new Thread(TCPCommunicationThread);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error : " + ex.getMessage());
        }
        playerOneThread.start();
        playerTwoThread.start();
    }
}
