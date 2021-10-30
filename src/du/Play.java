package du;

import java.util.LinkedList;

public class Play {
    LinkedList<CardInterface> a = new LinkedList();
    public Play(){

    }
    public void addCardToPlay(CardInterface c) {
        a.add(c);
    }
    public LinkedList<CardInterface> throwAll() {
        LinkedList<CardInterface> tmp = new LinkedList<>();
        int size = a.size();
        for(int i=0; i<size; i++) tmp.add(a.remove(0));
        return tmp;
    }
    public LinkedList<CardInterface> playPile() {
        return a;
    }
}
