package du;

import java.util.*;

public class DiscardPile {
    LinkedList<CardInterface> cards;

    public DiscardPile(LinkedList<CardInterface> cards) {
        this.cards = cards;
    }
        
    public Optional<CardInterface> getTopCard() {
    	if (cards.isEmpty()) return Optional.empty();
        return Optional.of(cards.get(cards.size()-1));
    }
        
    public void addCards(List<CardInterface> cards) {
        this.cards.addAll(cards);
    }
        
    public int getSize() {
        return cards.size();
    }
        
    public LinkedList<CardInterface> shuffle() {
        Collections.shuffle(cards);
        LinkedList<CardInterface> cards_to_send = cards;
        cards = new LinkedList<CardInterface>();
        return cards_to_send;
    }
    public LinkedList<CardInterface> get_dp() {
        return cards;
    }
}
        
        
