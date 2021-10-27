package du;

import java.util.*;

public class Hand {
    List<CardInterface> cards;
    public Hand(Deck c){
        this.cards = new LinkedList<>();
        LinkedList<CardInterface> a = c.draw(5);
        for(int i=0; i<5; i++) cards.add(a.get(i));
    }
    public boolean isActionCard(int idx) {
        return cards.get(idx).cardType().isAction;
    }

    public Optional<CardInterface> play(int idx) {
        Optional<CardInterface> a = Optional.of(cards.get(idx));
        return a;
    }
}
