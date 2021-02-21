import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go_network.Messages;

public class UDPCommunicationThread implements Runnable {

    int player;
    Server server;
    Kart kart;

    public UDPCommunicationThread(Server server, int player) {
        this.server = server;
        this.player = player;
        this.kart = ServerKarts.getKartFromPlayerNumber(player);
    }

    @Override
    public void run() {
        System.out.print("This thread is running..");

        // Each server object’s threaded run() method sends kart data to one client via one outputstream
        // and receives the kartdata via one inputstream from another client.

        // Here goes the logic to communicate with client
        // e.g. send opponent info, retrieve kart details etc.

        boolean connectionOpen = true;
        String message;

        while(connectionOpen) {
            message = server.getMessage(Messages.Protocols.UDP);

            switch (message) {
                case Messages.sendingKartInfo:
                    Kart kart = server.getKart();
                    System.out.println("Kart: " + kart.getPlayer());
                    break;
                case Messages.getOpponentSpeed:
                    int speed = ServerKarts.getOpponentSpeed(player);
                    server.sendMessage(Messages.returnSpeed(speed), Messages.Protocols.UDP);
                    break;
                case Messages.getOpponentIndex:
                    int index = ServerKarts.getOpponentIndex(player);
                    server.sendMessage(Messages.returnIndex(index), Messages.Protocols.UDP);
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        }
    }
}
