package du;

import java.util.LinkedList;

public class Turn {
    TurnStatus ts;
    int turnNumber;
    Hand hand;
    Deck deck;
    DiscardPile discardPile;
    Play play;
    public Turn(Hand h, Deck d, DiscardPile dp, Play p) {
        hand = h;
        deck = d;
        play = p;
        discardPile = dp;
        this.ts = new TurnStatus();
        turnNumber = 0;
    }
    public boolean evaluate_card(CardInterface card) {
        int drawCount = card.evaluate(ts);
        int tmp;
        LinkedList<CardInterface> tmp_cards = new LinkedList<>(deck.draw(drawCount));
        if (tmp_cards.size() < drawCount) {
            tmp = drawCount - tmp_cards.size();
            deck = new Deck(discardPile.shuffle());
            tmp_cards.addAll(deck.draw(tmp));
        }
        hand.drawCards(tmp_cards);
        return false;

    }
    public boolean nextTurn(int c, LinkedList<BuyDeck> bd) {
        AtLeastNEmptyDecks a = new AtLeastNEmptyDecks(c, bd);
        if (a.isGameOver()) return false;

        return true;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

}
