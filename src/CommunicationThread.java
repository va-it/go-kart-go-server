
import go_kart_go_network.Messages;
import go_kart_go_network.PacketReceiver;

import java.net.Socket;

public class CommunicationThread implements Runnable {

    int player;

    @Override
    public void run() {
        System.out.print("This thread is running..");



        // Each server object’s threaded run() method sends kart data to one client via one outputstream
        // and receives the kartdata via one inputstream from another client.

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.
    }
}