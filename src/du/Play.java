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
        for(int i=0; i<a.size(); i++) tmp.add(a.removeLast());
        return tmp;
    }
}
