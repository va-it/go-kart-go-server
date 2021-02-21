import go_kart_go.HelperClass;
import go_kart_go.Kart;

public class ServerKarts {

    public static Kart kartPlayerOne;
    public static Kart kartPlayerTwo;

    public ServerKarts() {
        kartPlayerOne = new Kart(HelperClass.returnKartColour(1), 1);
        kartPlayerTwo = new Kart(HelperClass.returnKartColour(2), 2);
    }

    public static Kart getKartFromPlayerNumber(int player) {
        if (player == 1) {
            return ServerKarts.kartPlayerOne;
        } else {
            return ServerKarts.kartPlayerTwo;
        }
    }

    public static int getOpponentSpeed(int requestingPlayer) {
        if (requestingPlayer == 1) {
            return kartPlayerTwo.getSpeed();
        } else {
            return kartPlayerOne.getSpeed();
        }
    }

    public static int getOpponentIndex(int requestingPlayer) {
        if (requestingPlayer == 1) {
            return kartPlayerTwo.getImageIndex();
        } else {
            return kartPlayerOne.getImageIndex();
        }
    }


}
