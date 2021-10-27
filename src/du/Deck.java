package du;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    LinkedList<CardInterface> deck;
    public Deck() {
        deck = new LinkedList<>();
        for(int i=0; i<3; i++) deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_ESTATE));
        for(int i=0; i<7; i++) deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_COPPER));
        Collections.shuffle(deck);
    }
    public LinkedList<CardInterface> draw(int count) {
        LinkedList tmp = new LinkedList();
        for(int i=0; i<count; i++) tmp.add(deck.removeFirst());
        return tmp;
    }
}
