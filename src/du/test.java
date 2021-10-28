package du;

public class test {
    public static void main(String[] args) {
        Game game = new Game(0,0,0,0,0,0,0,0);
        game.playCard(0);
        System.out.println(game.ts.getCoins());
        //System.out.println(game.hand_to_string());

    }
}
