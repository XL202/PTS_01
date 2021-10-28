package du;

public class test {
    public static void main(String[] args) {
        Game game = new Game(0,0,0,0,0,0,0,0);
        game.playCard(0);
        //System.out.println(game.t.getTurnNumber());
        //System.out.println(game.ts.getCoins());


        game.endPlayCardPhase();
        game.buyCards(2);
        /*game.endPlayCardPhase();
        game.endPlayCardPhase();
        /*game.endPlayCardPhase();
        game.endPlayCardPhase();
        game.endPlayCardPhase();
        game.endTurn();
        game.endTurn();*/

        //System.out.println(game.t.getTurnNumber());
        //System.out.println(game.hand_to_string());

    }
}
