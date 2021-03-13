import go_kart_go_network.ServerDetails;
import go_kart_go_network.UDPSocket;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // NOTE: Need to handle case when client closes window. SocketException, connection reset by peer

    public static Client[] clients = new Client[2];

    // Multi thread based on: https://stackoverflow.com/a/14771831

    public static void main(String[] args) {

        int player = 0;

        Thread udpThread = null;
        Thread tcpThread = null;

        try {
            ServerSocket server = new ServerSocket(ServerDetails.port);
            UDPSocket udpSocket = new UDPSocket(true);

            // only allow two clients
            while (player < 2) {
                Socket tcpSocket = server.accept();
                // the line above waits for a connection. So, if we are here, it means
                // a client was started and sent a request

                ++player; // the first to connect gets to be player 1

                TCPRunnable TCPRunnable = new TCPRunnable(tcpSocket, player);

                clients[player-1] = new Client(true, player);

                UDPRunnable udpRunnable = new UDPRunnable(udpSocket);

                udpThread = new Thread(udpRunnable);
                udpThread.start();

                tcpThread = new Thread(TCPRunnable);
                tcpThread.start();
            }
        } catch (Exception ex) {
            // Something has gone wrong
            System.err.println("Error : " + ex.getMessage());
        }

        boolean keepAlive = true;

        do {

            for(int i = 0; i < clients.length; ++i) {

                try {
                    // wait a little before checking again
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }

                if (!clients[i].isConnected()) {
                    keepAlive = false;
                }
            }
        } while (keepAlive);

        // we reach this when both clients disconnect
        System.out.println("Both client disconnected");

    }

    public static Client getClientFromPlayerNumber(int player) {
        if (player == 1) {
            // client[0] holds player 1
            // because the first client to connect is assigned that number
            return clients[0];
        } else {
            return clients[1];
        }
    }
}
