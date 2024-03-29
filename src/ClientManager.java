import go_kart_go.HelperClass;

public class ClientManager {

    private static boolean RACE_IN_PROGRESS = false;

    private static Client[] clients = new Client[2];

    public static Client[] getClients() {
        return clients;
    }

    public static Client getClient(int index) {
        return clients[index];
    }

    public static void setClient(int index, Client client) {
        clients[index] = client;
    }

    public static Client getClientFromPlayerNumber(int player) {
        if (player == 1) {
            // client[0] holds player 1
            // because the first client to connect is assigned that number
            return getClient(0);
        } else {
            return getClient(1);
        }
    }

    public static Client getOpponentClient(int player) {
        return getClientFromPlayerNumber(HelperClass.getOpponentPlayerNumber(player));
    }

    public static void setRaceStatus(boolean status) {
        RACE_IN_PROGRESS = status;
    }

    public static boolean getRaceStatus() {
        return RACE_IN_PROGRESS;
    }

    public static boolean winnerIsSet() {
        return clients[0].getKart().isWinner() || clients[1].getKart().isWinner();
    }

    public static void initialiseKarts() {
        clients[0].initialiseKart();
        clients[1].initialiseKart();
    }
}
