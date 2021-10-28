package du;

import java.util.*;

public class Hand {
    LinkedList<CardInterface> cards;
    public Hand(Deck c){
        this.cards = new LinkedList<>();
        LinkedList<CardInterface> a = c.draw(5);
        for(int i=0; i<5; i++) cards.add(a.get(i));
    }
    public boolean isActionCard(int idx) {
        return cards.get(idx).cardType().isAction;
    }

    public CardInterface play(int idx) {
        if (cards.size()> idx) return cards.get(idx);
        else return null;
    }
    public LinkedList<CardInterface> getHand() {
        return cards;
    }
    public void drawCards(LinkedList<CardInterface> c) {
        cards.addAll(c);
    }
    public LinkedList<CardInterface> throwCards() {
        LinkedList<CardInterface> tmp = new LinkedList<>();
        for(int i=0; i<cards.size(); i++) tmp.add(cards.removeLast());
        return tmp;
    }
}
