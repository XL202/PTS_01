package du;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class Deck {
    LinkedList<CardInterface> deck;
    public Deck(LinkedList<CardInterface> deck) {

        if (deck == null) {
            this.deck = new LinkedList<>();
            for(int i=0; i<3; i++) this.deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_ESTATE));
            for(int i=0; i<7; i++) this.deck.add(new GameCard(GameCardType.GAME_CARD_TYPE_COPPER));
            Collections.shuffle(this.deck);
        }
        else {
            for (int i = 0; i < deck.size(); i++) {
                this.deck.add(deck.get(i));
            }
        }

    }
    public LinkedList<CardInterface> draw(int count) {
        LinkedList tmp = new LinkedList();
        if (deck.size() < count) {
            count = deck.size();
        }
        for(int i=0; i<count; i++) tmp.add(deck.removeFirst());
        return tmp;
    }
}
