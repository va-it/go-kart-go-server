import go_kart_go_network.CommunicationSocket;
import go_kart_go_network.Messages;
import go_kart_go_network.PacketReceiver;

public class CommunicationThread implements Runnable {
    public CommunicationThread() {
    }

    @Override
    public void run() {
        System.out.print("This thread is running..");

        boolean established = false;

        CommunicationSocket communicationSocket = new CommunicationSocket();

        while(!established) {

            System.out.println("Listening");

            String message = PacketReceiver.receivePacket(communicationSocket.socket);
            System.out.println(message);

            if (message == Messages.establishConnection) {
                System.out.println("Client wants to connect");
                established = true;
            }
        }

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.
    }
}