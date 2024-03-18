package javadatastructure.listimplment.link;

public class CircularLinkedList<E> implements ListInterface<E> {

    private Node<E> tail;
    private int numItems;

    public CircularLinkedList() {
        numItems = 0;
        tail = new Node(-1);
        tail.next = tail;
    }

    @Override
    public void add(int index, E item) {
        if (index >= 0 && index <= numItems) {
            Node<E> prevNode = getNode(index - 1);
            Node<E> newNode = new Node<>(item, prevNode.next);
            prevNode.next = newNode;
            if (index == numItems)
                tail = newNode;
            numItems++;
        }
    }

    @Override
    public void append(E x) {
        Node<E> prevNode = tail;
        Node<E> newNode = new Node(x, tail.next);
        prevNode.next = newNode;
        tail = newNode;
        numItems++;
    }

    private Node<E> getNode(int index) {
        if (index >= -1 && index <= numItems - 1) {
            Node<E> currNode = tail;
            for (int i = 0; i <= index; i++) {
                currNode = currNode.next;
            }
            return currNode;
        } else {
            return null;
        }
    }

    @Override
    public E remove(int index) {
        if (index >= 0 && index < numItems) {
            Node<E> prevNode = getNode(index -1);
            E rItem = prevNode.next.item;
            prevNode.next = prevNode.next.next;
            if (index == numItems)
                tail = prevNode;
            numItems--;
            return rItem;
        }
        return null;
    }

    @Override
    public boolean removeItem(E x) {
        Node<E> currNode = tail.next;
        Node<E> prevNode;
        for (int i = 0; i < numItems; i++) {
            prevNode = currNode;
            currNode = currNode.next;

            if(((Comparable)(currNode.item)).compareTo(x) == 0){
                prevNode.next = currNode.next;
                numItems--;
                return true;
            }
        }

        return false;
    }

    @Override
    public E get(int index) {
        if (index >= 0 && index < numItems) {
            return getNode(index).item;
        }
        return null;
    }

    @Override
    public void set(int index, E item) {
        if (index >= 0 && index < numItems) {
            getNode(index).item = item;
        } else {

        }
    }

    public final int NOT_FOUND = -12345;
    @Override
    public int indexOf(E item) {
        Node<E> currNode = tail.next;
        int i=0;
        for (i = 0; i < numItems; i++) {
            currNode = currNode.next;
            if(((Comparable)(currNode.item)).compareTo(item) == 0) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    @Override
    public int len() {
        return numItems;
    }

    @Override
    public boolean isEmpty() {
        return numItems == 0;
    }

    @Override
    public void clear() {
        numItems = 0;
        tail = new Node<>(null, null);
    }
}
