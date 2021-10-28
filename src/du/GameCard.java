package du;

public class GameCard implements CardInterface {
    GameCardType g;
    public GameCard(GameCardType g) {
        this.g = g;
    }
    @Override
    public int evaluate(TurnStatus ts) {
        ts.setCoins(ts.getCoins() + this.g.plusCoins);
        ts.setActions(ts.getActions() + this.g.plusActions);
        ts.setBuys(ts.getBuys() + this.g.plusBuys);
        return g.plusCards;
    }

    @Override
    public GameCardType cardType() {
        return g;
    }
}
