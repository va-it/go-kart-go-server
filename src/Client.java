import go_kart_go.HelperClass;
import go_kart_go.Kart;
import go_kart_go.RaceTrack;

public class Client {
    private boolean connected;
    private boolean ready;
    private int playerNumber;
    private Kart kart;

    public Client(boolean connected, int playerNumber) {
        setConnected(connected);
        setPlayerNumber(playerNumber);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Kart getKart() {
        return kart;
    }

    public void setKart(Kart kart) {
        this.kart = kart;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public boolean isReadyToStart() {
        return isConnected() && isReady();
    }

    public void initialiseKart() {
        this.kart = new Kart(HelperClass.getColourFromPlayerNumber(playerNumber), playerNumber);
        this.kart.setInitialPosition();
    }
}
