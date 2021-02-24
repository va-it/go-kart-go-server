import go_kart_go.Kart;

import java.io.StreamCorruptedException;

public class Client {
    private boolean connected;
    private boolean ready;
    private int playerNumber;

    public Kart kart;

    public Client(boolean connected, int playerNumber) {
        this.connected = connected;
        this.playerNumber = playerNumber;
        this.kart = new Kart();
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

    public int getSpeed() {
        if (kart != null) {
            return kart.getSpeed();
        } else {
            return 0;
        }
    }

    public int getIndex() {
        if (kart != null) {
            return kart.getImageIndex();
        } else {
            return 0;
        }
    }
}
