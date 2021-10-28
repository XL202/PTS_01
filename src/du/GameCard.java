package du;

public class GameCard implements CardInterface {
    GameCardType g;
    public GameCard(GameCardType g) {
        this.g = g;
    }
    @Override
    public int evaluate(TurnStatus ts) {
        ts.setCoins(ts.getCoins() + g.plusCoins);
        //System.out.println(ts.getCoins());
        ts.getCoins();
        ts.setActions(ts.getActions() + g.plusActions);
        ts.setBuys(ts.getBuys() + g.plusBuys);
        System.out.printf("*** [Card <%s> played: C +%d, A +%d, B +%d, Cards +%d.] ***\n", g.name, g.plusCoins, g.plusActions, g.plusBuys, g.plusCards);
        return g.plusCards;

    }

    @Override
    public GameCardType cardType() {
        return g;
    }
}
