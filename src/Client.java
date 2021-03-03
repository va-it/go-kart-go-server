import go_kart_go.Kart;

public class Client {
    private boolean connected;
    private boolean ready;
    private int playerNumber;
    private TCPThread tcpThread;

    public Kart kart;

    public Client(boolean connected, int playerNumber, TCPThread tcpThread) {
        this.connected = connected;
        this.playerNumber = playerNumber;
        this.kart = new Kart();
        this.tcpThread = tcpThread;
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

    public boolean isReadyToStart() {
        return isConnected() && isReady();
    }
}
