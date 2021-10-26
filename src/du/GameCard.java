package du;

public class GameCard implements CardInterface {
    GameCardType g;
    public GameCard(GameCardType g) {
        this.g = g;
    }
    @Override
    public void evaluate(TurnStatus ts) {

    }

    @Override
    public GameCardType cardType() {
        return g;
    }
}
