package du;

import java.util.LinkedList;

public class Play {
    LinkedList a = new LinkedList();
    public Play(){

    }
    public void addCardToPlay(CardInterface c) {
        a.add(c);
    }
    public LinkedList<CardInterface> throwAll() {
        return a;
    }
}
