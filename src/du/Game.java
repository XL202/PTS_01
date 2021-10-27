package du;

import java.util.LinkedList;

public class Game {
    boolean actionPhase, buyPhase;
    DiscardPile dp;
    Deck d;
    Hand h;
    Turn t;
    LinkedList<BuyDeck> bd;
    int empty_Buy_Decks_to_end_game;

    public Game(int m, int e, int c, int s, int v, int f, int l, int empty_Buy_Decks_to_end_game) {
        if (m<5) m = 5;
        if (e<5) e = 5;
        if (c<5) c = 5;
        if (s<5) s = 5;
        if (v<5) v = 5;
        if (f<5) f = 5;
        if (l<5) l = 5;
        if (empty_Buy_Decks_to_end_game < 0 || empty_Buy_Decks_to_end_game > 7) empty_Buy_Decks_to_end_game = 3;
        this.empty_Buy_Decks_to_end_game = empty_Buy_Decks_to_end_game;

        d = new Deck();
        h = new Hand(d);
        t = new Turn();
        bd = new LinkedList<>();

        bd.set(0, new BuyDeck(GameCardType.GAME_CARD_TYPE_MARKET, m));
        bd.set(1, new BuyDeck(GameCardType.GAME_CARD_TYPE_ESTATE, e));
        bd.set(2, new BuyDeck(GameCardType.GAME_CARD_TYPE_COPPER, c));
        bd.set(3, new BuyDeck(GameCardType.GAME_CARD_TYPE_SMITHY, s));
        bd.set(4, new BuyDeck(GameCardType.GAME_CARD_TYPE_VILLAGE, v));
        bd.set(5, new BuyDeck(GameCardType.GAME_CARD_TYPE_FESTIVAL, f));
        bd.set(6, new BuyDeck(GameCardType.GAME_CARD_TYPE_LABORATORY, l));



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
        if (bd.get(buyCardIdx).cardCount() > 0) {
            LinkedList<CardInterface> a = new LinkedList();
            a.add(bd.get(buyCardIdx).buy());
            dp.addCards(new LinkedList(a));
            return true;
        }
        return false;
    }

    public boolean endTurn() {
        return false;
    }
    public boolean isGameOver() {
        int tmp = 0;
        for(int i=0; i<bd.size(); i++) if (bd.get(i).cardCount() == 0) tmp++;
        if (tmp >= empty_Buy_Decks_to_end_game) return false;
        return true;
    }
}
