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

        Thread playerOneTCPThread;
        Thread playerTwoTCPThread;
        Thread singleUDPThread = null;

        try {
            ServerSocket server = new ServerSocket(ServerDetails.port);
            UDPSocket udpSocket = new UDPSocket(true);

            // only allow two clients
            while (player < 2) {
                Socket tcpSocket = server.accept();
                // the line above waits for a connection. So, if we are here, it means
                // a client was started and sent a request

                ++player; // the first to connect gets to be player 1

                TCPThread TCPThread = new TCPThread(tcpSocket, player);

                clients[player-1] = new Client(true, player, TCPThread);

                UDPThread udpThread = new UDPThread(udpSocket);
                singleUDPThread = new Thread(udpThread);
                singleUDPThread.start();

                if (player == 1) {
                    playerOneTCPThread = new Thread(TCPThread);
                    playerOneTCPThread.start();
                } else {
                    playerTwoTCPThread = new Thread(TCPThread);
                    playerTwoTCPThread.start();
                }
            }
        } catch (Exception ex) {
            // Something has gone wrong
            System.err.println("Error : " + ex.getMessage());
        }
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
