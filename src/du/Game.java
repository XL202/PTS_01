package du;

import java.util.LinkedList;

public class Game {
    boolean actionPhase, buyPhase;
    DiscardPile dp;
    Deck d;
    Hand h;
    Turn t;
    Play p;
    TurnStatus ts;
    LinkedList<BuyDeck> bd;
    LinkedList<GameCardType> gct;
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



        p = new Play();
        dp = new DiscardPile(new LinkedList<>());
        d = new Deck(null, dp);
        h = new Hand(d);
        ts = new TurnStatus();
        t = new Turn(h, d, dp, p, ts);
        bd = new LinkedList<>();
        this.gct = new LinkedList<>();
        gct.add(GameCardType.GAME_CARD_TYPE_MARKET);
        gct.add(GameCardType.GAME_CARD_TYPE_ESTATE);
        gct.add(GameCardType.GAME_CARD_TYPE_COPPER);
        gct.add(GameCardType.GAME_CARD_TYPE_SMITHY);
        gct.add(GameCardType.GAME_CARD_TYPE_VILLAGE);
        gct.add(GameCardType.GAME_CARD_TYPE_FESTIVAL);
        gct.add(GameCardType.GAME_CARD_TYPE_LABORATORY);

        bd.add(new BuyDeck(gct.get(0), m));
        bd.add(new BuyDeck(gct.get(1), e));
        bd.add(new BuyDeck(gct.get(2), c));
        bd.add(new BuyDeck(gct.get(3), s));
        bd.add(new BuyDeck(gct.get(4), v));
        bd.add(new BuyDeck(gct.get(5), f));
        bd.add(new BuyDeck(gct.get(6), l));

        System.out.printf("Game starts. {Buy deck: %s}\n", buyDecks());
        System.out.printf("Deck: {%s}\n", deck_to_string());
        System.out.printf("DiscardPile: {%s}\n", dp_to_string());
        System.out.printf("Hand: A: %d, B: %d, C: %d, {%s}\n", ts.getActions(), ts.getBuys(), ts.getCoins(), hand_to_string());
        System.out.println("Turn 1, action phase.\n-----------");
        actionPhase = true;
        buyPhase = false;

    }
    public boolean playCard(int handIdx) {
        if (ts.getActions() > 0) {
            if (h.cards.size() > handIdx) t.evaluate_card(h.play(handIdx));
            else {
                System.out.println("Zvolená karta nie je v ruke!");
                return false;
            }
            p.addCardToPlay(h.cards.remove(handIdx));
            ts.setActions(ts.getActions() - 1);
            //System.out.println(ts.getCoins());
        }
        else System.out.println("Nedostatok akcii!");

        //if (h.play(handIdx).cardType().isAction) ;

        //System.out.println(ts.getCoins());
        return true;
    }
    public boolean endPlayCardPhase() {
        if (actionPhase) System.out.printf("Turn %s, buy phase\n------------\n", t.getTurnNumber());
        actionPhase = !actionPhase;


        if (buyPhase) endTurn();
        buyPhase = !buyPhase;
        return false;
    }
    /*public boolean buyCard(int buyCardIdx) {
        if (bd.get(buyCardIdx).cardCount() > 0) {
            LinkedList<CardInterface> a = new LinkedList();
            a.add(bd.get(buyCardIdx).buy());
            dp.addCards(new LinkedList(a));
            return true;
        }
        return false;
    }*/

    public boolean endTurn() {
        if (!t.nextTurn(empty_Buy_Decks_to_end_game, bd)) {


            System.out.println("***** Game over *****");
        }
        else {
            //(t.turnNumber)++;
            dp.addCards(h.throwCards());
            dp.addCards(p.throwAll());
            h.drawCards(d.draw(5));
            System.out.printf("Next turn: Turn %d, action phase.\n", t.getTurnNumber());
            System.out.printf("{Buy deck: %s}\n", buyDecks());
            System.out.printf("Deck: {%s}\n", deck_to_string());
            System.out.printf("DiscardPile: {%s}\n", dp_to_string());
            System.out.printf("Hand: A: %d, B: %d, C: %d, {%s}\n------------\n", ts.getActions(), ts.getBuys(), ts.getCoins(), hand_to_string());

        }
        return false;

    }
    public boolean buyCards(int idBuyDeck) {
        if (idBuyDeck > 7) {
            System.out.println("Takýto buy deck neexistuje.");
            return false;
        }
        if (ts.getBuys() < 1) {
            System.out.printf("Nie je dostatočný počet Buys pre nákup karty \n");
            return false;
        }

        if (gct.get(idBuyDeck).cost > ts.getCoins()) {
            System.out.printf("Nie je dostatočný počet mincí pre nákup karty %s, pretože je potrebných %d a máte len %d. \n", gct.get(idBuyDeck).name, gct.get(idBuyDeck).cost, ts.getCoins());
            return false;
        }
        if (bd.get(idBuyDeck).cardCount() > 0) {
            LinkedList<CardInterface> a = new LinkedList<>();
            a.add(bd.get(idBuyDeck).buy());
            dp.addCards(a);
            ts.setCoins(ts.getCoins() - gct.get(idBuyDeck).cost);
            System.out.printf("Karta %s bola úspešne kúpená za %d coins. Zostatok coins: %d.\n", gct.get(idBuyDeck).name, gct.get(idBuyDeck).cost, ts.getCoins());
            ts.setBuys(ts.getBuys() -1);
            return true;
        }
        else {
            System.out.println("Nie je možné kúpiť kartu, lebo už nie je v BuyDecku");
        }
        return false;
    }
    private String buyDecks() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<bd.size(); i++) {
            sb.append(gct.get(i).name + ": ");
            sb.append(bd.get(i).cardCount() + " ");
        }
        return sb.toString();
    }
    public String deck_to_string() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<d.deck.size(); i++) {
            sb.append(d.deck.get(i).cardType().name + " ");
        }
        return sb.toString();
    }
    public String dp_to_string() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<dp.get_dp().size(); i++) {
            sb.append(dp.get_dp().get(i).cardType().name + " ");
        }
        return sb.toString();
    }
    public String hand_to_string() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<h.getHand().size(); i++) {
            sb.append(h.getHand().get(i).cardType().name + " ");
        }
        return sb.toString();
    }
}
