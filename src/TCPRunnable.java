import go_kart_go_network.Messages;

import java.net.Socket;

public class TCPRunnable implements Runnable {
    Server server;
    Socket clientSocket;
    int player;
    String message;
    boolean connectionIsOpen;

    public TCPRunnable(Socket clientSocket, int player) {
        this.clientSocket = clientSocket;
        this.player = player;
        this.connectionIsOpen = true;
    }

    public void run() {
            System.out.println("Connected to client : "+ clientSocket.getInetAddress().getHostName());

            this.server = new Server(Messages.Protocols.TCP, this.clientSocket);
            this.server.tcpServer.OpenInputOutputStreams();

            message = server.getMessage(Messages.Protocols.TCP);

            if (message.equals(Messages.establishConnection)) {
                server.sendMessage(Messages.connectionSuccessful, Messages.Protocols.TCP);
                listenForMessages();
            }
    }

    private void listenForMessages() {
        do {
            message = server.getMessage(Messages.Protocols.TCP);

            try  {
                Thread.sleep ( 1000/30 );
            } catch ( InterruptedException ex) {
                System.err.println ("Error: " + ex );
            }

            switch (message) {
                case Messages.getPlayerNumber:
                    server.sendMessage(Messages.returnPlayerNumber(player), Messages.Protocols.TCP);
                    break;
                case Messages.ready:
                    server.sendMessage(Messages.readyReceived, Messages.Protocols.TCP);
                    ClientManager.getClientFromPlayerNumber(player).setReady(true);
                    break;
                case Messages.requestToStart:
                    if (
                            ClientManager.getOpponentClient(player) != null &&
                            ClientManager.getOpponentClient(player).isReadyToStart()
                    ) {
                        ClientManager.getClientFromPlayerNumber(player).initialiseKart();
                        server.sendMessage(Messages.startRace, Messages.Protocols.TCP);
                        ClientManager.setRaceStatus(true);
                        System.out.println("Race started");
                    } else {
                        server.sendMessage(Messages.wait, Messages.Protocols.TCP);
                    }
                    break;
                case Messages.requestWinnerStatus:
                    if  (ClientManager.winnerIsSet()) {
                        server.sendMessage(Messages.winnerStatusSet, Messages.Protocols.TCP);
                    } else {
                        server.sendMessage(Messages.winnerStatusNotSet, Messages.Protocols.TCP);
                    }
                    break;
                case Messages.requestRaceStatus:
                    if (ClientManager.getRaceStatus()) {
                        server.sendMessage(Messages.raceInProgress, Messages.Protocols.TCP);
                    } else {
                        if (ClientManager.getOpponentClient(player).getKart().isWinner()) {
                            server.sendMessage(Messages.opponentWins, Messages.Protocols.TCP);
                            ClientManager.initialiseKarts();
                        } else {
                            server.sendMessage(Messages.gameOver, Messages.Protocols.TCP);
                        }
                    }
                    break;
                case Messages.stopRace:
                    server.sendMessage(Messages.confirmRaceStopped, Messages.Protocols.TCP);
                    ClientManager.getClientFromPlayerNumber(player).setReady(false);
                    ClientManager.setRaceStatus(false);
                    System.out.println("Race stopped");
                    break;
                case Messages.closeConnection:
                    connectionIsOpen = false;
                    System.out.println("Player " + player  + " closed the connection");
                    ClientManager.getClientFromPlayerNumber(player).setConnected(false);
                    this.server.tcpServer.closeConnection();
                    break;
                case Messages.checkOpponentConnection:
                    if (
                            ClientManager.getOpponentClient(player) != null &&
                            ClientManager.getOpponentClient(player).isConnected()
                    ) {
                        server.sendMessage(Messages.opponentConnected, Messages.Protocols.TCP);
                    } else {
                        if (
                                ClientManager.getOpponentClient(player) != null &&
                                !ClientManager.getOpponentClient(player).isConnected()
                        ) {
                            server.sendMessage(Messages.opponentNotConnected, Messages.Protocols.TCP);
                        }
                    }
                    break;
                default:
                    System.err.println("Invalid message: " + message);
            }
        } while(connectionIsOpen);
    }
}
