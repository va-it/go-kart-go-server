import go_kart_go_network.ServerDetails;
import go_kart_go_network.UDPSocket;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    // Multi thread based on: https://stackoverflow.com/a/14771831

    public static void main(String[] args) {

        int player = 0;

        Thread udpThread = null;
        Thread tcpThread = null;

        ServerSocket server = null;
        UDPSocket udpSocket = null;

        UDPRunnable udpRunnable = null;

        try {
            server = new ServerSocket(ServerDetails.port);
            udpSocket = new UDPSocket(true);

            // only allow two clients
            while (player < 2) {
                Socket tcpSocket = server.accept();
                // the line above waits for a connection. So, if we are here, it means
                // a client was started and sent a request

                ++player; // the first to connect gets to be player 1

                TCPRunnable tcpRunnable = new TCPRunnable(tcpSocket, player);

                ClientManager.setClient(player-1, new Client(true, player));

                udpRunnable = new UDPRunnable(udpSocket);

                udpThread = new Thread(udpRunnable);
                udpThread.start();

                tcpThread = new Thread(tcpRunnable);
                tcpThread.start();
            }
        } catch (Exception ex) {
            // Something has gone wrong
            System.err.println("Error : " + ex.getMessage());
        }


        while (true) {
            // keep server alive until there is at least one client connected
            boolean activeClientConnections = false;

            try {
                // wait a little before checking (again)
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println("System error: " + e);
            }

            for(int i = 0; i < ClientManager.getClients().length; ++i) {

                if (ClientManager.getClient(i).isConnected()) {
                    activeClientConnections = true;
                    break;
                }
            }

            if (!activeClientConnections) {
                break;
            }
        }

        // we reach this when both clients disconnect
        System.out.println("Both client disconnected");

        // stop listening for messages via UDP
        udpRunnable.stopListening();
    }
}
