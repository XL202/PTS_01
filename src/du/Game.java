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
    boolean ok = true;

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

        System.out.println("Game starts.");
        System.out.println("Turn 1, action phase.\n-----------");

        buyPhase = false;
        actionPhase = true;
        is_Action_phase_possible();

    }
    public boolean playCard(int handIdx) {
        if (!actionPhase) {
            System.err.println("Nie je možné hrať kartu pokiaľ nie je ActionPhase!");
            return false;
        }
        if (ts.getActions() > 0) {
            if (h.cards.size() > handIdx && handIdx > -1) {
                if (h.getHand().get(handIdx).cardType().isAction) t.evaluate_card(h.play(handIdx));
                else {
                    System.err.println("Zvolená karta nie je ActionCard!");
                    return false;
                }
            }
            else {
                System.err.println("Zvolená karta nie je v ruke!");
                return false;
            }
            p.addCardToPlay(h.cards.remove(handIdx));
            ts.setActions(ts.getActions() - 1);
            if (ts.getActions() == 0) {
                System.out.println("Počet Actions je 0");
                endPlayCardPhase();
            }
            //System.out.println(ts.getCoins());
        }
        else System.err.println("Nedostatok akcii!");

        //if (h.play(handIdx).cardType().isAction) ;

        //System.out.println(ts.getCoins());
        return true;
    }

    public boolean endPlayCardPhase() {
        if (buyPhase) {
            buyPhase = false;
            actionPhase = true;
            endTurn();
        }
        else if (actionPhase) {
            System.out.printf("============\nTurn %s, buy phase\n------------\n", t.getTurnNumber());
            actionPhase = false;
            buyPhase = true;
            int tmp = h.getHand().size();
            int tmp_c = 0;
            for(int i=0; i<tmp; i++) {

                if (h.getHand().get(i).cardType() == GameCardType.GAME_CARD_TYPE_COPPER) {
                    ts.setCoins(ts.getCoins() + 1);
                    p.addCardToPlay(h.getHand().remove(i));
                    i--;
                    tmp--;
                    tmp_c++;
                }
            }
            if (tmp_c != 0) System.out.printf("%d kariet COPPER bolo premenených na coins (+%d Coins)\n", tmp_c, tmp_c);
        }

        return false;
    }

    public boolean endTurn() {
        dp.addCards(h.throwCards());
        dp.addCards(p.throwAll());
        if (!t.nextTurn(empty_Buy_Decks_to_end_game, bd)) {
            int points = 0;
            int cards = 0;
            for(int i=0; i<dp.getSize(); i++) {
                points += dp.cards.get(i).cardType().points;
                cards++;
            }
            for(int i=0; i<d.deck.size(); i++) {
                points += d.deck.get(i).cardType().points;
                cards++;
            }
            System.out.println("======================\n***** Game over *****");
            System.out.printf("Počet kariet: %d\n", cards);
            System.out.printf("Počet bodov v decku: %d\n", points);
            printBuyDeck();
            printDeck();
            printDiscardPile();
            printHand();
            printPlay();
            ok = false;
            return false;
        }
        else {
            //(t.turnNumber)++;

            ts = new TurnStatus();

            h.drawCards(d.draw(5));
            System.out.printf("============\nNext turn: Turn %d, action phase.\n", t.getTurnNumber());
            //actionPhase = true;
            is_Action_phase_possible();
        }
        return false;

    }
    private void is_Action_phase_possible() {
        boolean tmp = true;
        for(int i=0; i<h.getHand().size(); i++) {
            if (h.getHand().get(i).cardType().isAction) return;
            else tmp = false;
        }
        if (!tmp) {
            System.out.println("***V Hand nie sú žiadne action cards!***");

            endPlayCardPhase();
        }

    }
    public boolean buyCards(int idBuyDeck) {
        if (!buyPhase) {
            System.err.println("Nie je možné hrať kartu pokiaľ nie je BuyPhase!");
            return false;
        }
        if (idBuyDeck > 7 && idBuyDeck < 0) {
            System.err.println("Takýto buy deck neexistuje.");
            return false;
        }
        if (ts.getBuys() < 1) {
            System.err.printf("Nie je dostatočný počet Buys pre nákup karty.\n");
            return false;
        }
        if (gct.get(idBuyDeck).cost > ts.getCoins()) {
            System.err.printf("Nie je dostatočný počet mincí pre nákup karty %s, pretože je potrebných %d a máte len %d. \n", gct.get(idBuyDeck).name, gct.get(idBuyDeck).cost, ts.getCoins());
            return false;
        }
        if (bd.get(idBuyDeck).cardCount() > 0) {
            LinkedList<CardInterface> a = new LinkedList<>();
            a.add(bd.get(idBuyDeck).buy());
            dp.addCards(a);
            ts.setCoins(ts.getCoins() - gct.get(idBuyDeck).cost);
            System.out.printf("Karta %s bola úspešne kúpená za %d coins. Zostatok coins: %d.\n", gct.get(idBuyDeck).name, gct.get(idBuyDeck).cost, ts.getCoins());
            ts.setBuys(ts.getBuys() -1);
            if (ts.getBuys() == 0) {
                System.out.println("Počet Buys je 0.");
                endPlayCardPhase();
            }
            return true;
        }
        else {
            System.err.println("Nie je možné kúpiť kartu, lebo už nie je v BuyDecku");
        }
        return false;
    }
    public void printBuyDeck() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<bd.size(); i++) {
            sb.append(gct.get(i).name + ": ");
            sb.append(bd.get(i).cardCount() + ", ");
        }
        System.out.printf("BuyDeck: [%s].\n", sb);
    }

    public void printBuyDeckDescription() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<bd.size(); i++) {
            sb.append(gct.get(i).name + ": [");
            sb.append(bd.get(i).cardCount() + ", " + gct.get(i).cost + " {");
            sb.append(gct.get(i).description + "}]\n");
        }
        System.out.printf("*** BuyDeck ***\nCard_type: [count in BD, Cost, {Description}]\n%s", sb);
    }
    public void printPlay() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<p.playPile().size(); i++) {
            sb.append(p.playPile().get(i).cardType().name + " ");
        }
        System.out.printf("PlayPile: [%s].\n", sb);
    }
    public void printDeck() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<d.deck.size(); i++) {
            sb.append(d.deck.get(i).cardType().name + " ");
        }
        System.out.printf("Deck: [%s].\n", sb);
    }
    public void printDiscardPile() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<dp.get_dp().size(); i++) {
            sb.append(dp.get_dp().get(i).cardType().name + " ");
        }
        System.out.printf("Discard Pile: [%s].\n", sb);
    }
    public void printHand() {
        StringBuilder sb = new StringBuilder();

        for(int i=0; i<h.getHand().size(); i++) {
            sb.append(h.getHand().get(i).cardType().name + " ");
        }
        System.out.printf("A: %d, B: %d, C: %d, Hand: [%s].\n", ts.getActions(), ts.getBuys(), ts.getCoins(), sb.toString());
    }
}
