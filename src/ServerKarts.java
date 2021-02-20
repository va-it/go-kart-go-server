import go_kart_go.HelperClass;
import go_kart_go.Kart;

public class ServerKarts {

    public static Kart kartPlayerOne;
    public static Kart kartPlayerTwo;

    public ServerKarts() {
        kartPlayerOne = new Kart(HelperClass.returnKartColour(1), 1);
        kartPlayerTwo = new Kart(HelperClass.returnKartColour(2), 2);
    }

    public static int getOpponentSpeed(Kart kart) {
        int player = kart.getPlayer();
        if (player == 1) {
            return kartPlayerTwo.getSpeed();
        } else {
            return kartPlayerOne.getSpeed();
        }
    }

    public static int getOpponentIndex(Kart kart) {
        int player = kart.getPlayer();
        if (player == 1) {
            return kartPlayerTwo.getImageIndex();
        } else {
            return kartPlayerOne.getImageIndex();
        }
    }
}
