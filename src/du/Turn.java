package du;

import java.util.LinkedList;

public class Turn {
    TurnStatus ts;
    int turnNumber;
    Hand hand;
    Deck deck;
    DiscardPile discardPile;
    Play play;
    public Turn(Hand h, Deck d, DiscardPile dp, Play p, TurnStatus ts) {
        hand = h;
        deck = d;
        play = p;
        discardPile = dp;
        this.ts = ts;
        turnNumber = 1;
    }
    public boolean evaluate_card(CardInterface card) {
        int drawCount = card.evaluate(ts);

        System.out.printf("%d add actions\n", card.cardType().plusActions);
        hand.drawCards(deck.draw(drawCount));
        return false;

    }
    public boolean nextTurn(int c, LinkedList<BuyDeck> bd) {
        AtLeastNEmptyDecks a = new AtLeastNEmptyDecks(c, bd);
        if (a.isGameOver()) return false;
        turnNumber++;
        //ts = new TurnStatus();
        return true;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

}
