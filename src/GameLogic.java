public class GameLogic {
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

}
