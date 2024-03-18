package javadatastructure.listimplment.link;

import java.util.Iterator;

public class LikedListIterator implements Iterator<Node>{

    private Node current;

    public LikedListIterator(LinkedList list) {
        current = list.getNode(-1);
    }

    public boolean hasNext() {
        return current.next != null;
    }

    @Override
    public Node next() {
        return current = current.next;
    }

}
