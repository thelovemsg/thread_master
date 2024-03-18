package javadatastructure.listimplment.link;

public class CircularDoublyLinkedList<E> implements ListInterface<E> {

    private BidirectionalNode<E> head;
    private int numItems;

    public CircularDoublyLinkedList() {
        numItems = 0;
        head = new BidirectionalNode<>(null);
        head.next = head.prev = head;
    }

    @Override
    public void add(int i, E x) {
        if (i >= 0 && i <= numItems) {
            BidirectionalNode<E> prevNode = getNode(i - 1);
            BidirectionalNode<E> newNode = new BidirectionalNode<>(prevNode, x, prevNode.next);
            newNode.next.prev = newNode;
            prevNode.next = newNode;
            numItems++;
        }
    }

    @Override
    public void append(E x) {
        BidirectionalNode<E> prevNode = head.prev;
        BidirectionalNode<E> newNode = new BidirectionalNode<>(prevNode, x , head);
        prevNode.next = newNode;
        head.prev = newNode;
        numItems++;
    }

    @Override
    public E remove(int i) {
        if (i >= 0 && i <= numItems) {
            BidirectionalNode<E> currNode = getNode(i);
            BidirectionalNode<E> prev = currNode.prev;
            BidirectionalNode<E> next = currNode.next;
            prev.next = next;
            next.prev = prev;
            numItems--;
            return currNode.item;
        }
        return null;
    }

    @Override
    public boolean removeItem(E x) {
        BidirectionalNode<E> currNode = head;
        for (int i = 0; i < numItems; i++) {
            currNode = currNode.next;

            if(((Comparable)(currNode.item)).compareTo(x) == 0) {
                currNode.prev.next = currNode.next;
                currNode.next.prev = currNode.prev;
                numItems--;
                return true;
            }
        }
        return false;
    }

    @Override
    public E get(int i) {
        if (i >= 0 && i <= numItems) {
            return getNode(i).item;
        }

        return null;
    }

    private BidirectionalNode<E> getNode(int index) {
        if (index >= 0 && index <= numItems -1) {
            BidirectionalNode<E> currNode = head;
            if (index < numItems/2) {
                for(int i=0; i<= index; i++) {
                    currNode = currNode.next;
                }
            } else {
                for(int i=numItems - 1; i >= index; i--) {
                    currNode = currNode.prev;
                }
            }
            return currNode;
        } else {
            // 에러 처리
            throw new RuntimeException("there is no item in proper range.");
        }
    }

    @Override
    public void set(int i, E x) {
        if (i >= 0 && i <= numItems) {
             getNode(i).item = x;
        }
    }

    private final int NOT_FOUND = -12345;

    @Override
    public int indexOf(E x) {
        BidirectionalNode<E> currNode = head;
        for (int i = 0; i <= numItems-1; i++) {
            currNode = currNode.next;
            if(((Comparable)(currNode.item)).compareTo(x)==0){
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
        head.next = head.prev = head;
    }
}
