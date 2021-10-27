package du;

import java.util.LinkedList;

public class Game {
    boolean actionPhase, buyPhase;
    Deck d;
    Hand h;
    Turn t;
    LinkedList<BuyDeck> bd;

    public Game(int m, int e, int c, int s, int v, int f, int l) {
        d = new Deck();
        h = new Hand(d);
        t = new Turn();
        bd = new LinkedList<>();
        LinkedList<CardInterface> a = new LinkedList<>();
        for(int i=0; i<m; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_MARKET);
            a.add(card);
        }
        bd.set(0, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<e; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_ESTATE);
            a.add(card);
        }
        bd.set(1, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<c; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_COPPER);
            a.add(card);
        }
        bd.set(2, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<s; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_SMITHY);
            a.add(card);
        }
        bd.set(3, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<v; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_VILLAGE);
            a.add(card);
        }
        bd.set(4, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<f; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_FESTIVAL);
            a.add(card);
        }
        bd.set(5, new BuyDeck(a));
        a = new LinkedList<>();
        for(int i=0; i<l; i++) {
            GameCard card = new GameCard(GameCardType.GAME_CARD_TYPE_LABORATORY);
            a.add(card);
        }
        bd.set(6, new BuyDeck(a));

        boolean actionPhase = true;
        boolean buyPhase = false;

    }
    public boolean playCard(int handIdx) {
        h.play(handIdx);
        return false;
    }
    private boolean endPlayCardPhase() {
        return false;
    }
    public boolean buyCard(int buyCardIdx) {
        return false;
    }

    public boolean endTurn() {
        return false;
    }
}
