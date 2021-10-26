package du;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hand {
    List<CardInterface> cards;
    public Hand(ArrayList<CardInterface> cards){
        this.cards = cards;
    }
    public boolean isActionCard(int idx) {
        return cards.get(idx).cardType().isAction;
    }
    public Optional<CardInterface> play(int idx) {
        Optional<CardInterface> a = Optional.of(cards.get(idx));
        return a;
    }
}
