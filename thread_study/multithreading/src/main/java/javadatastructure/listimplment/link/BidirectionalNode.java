package javadatastructure.listimplment.link;

public class BidirectionalNode<E> {
    public BidirectionalNode<E> prev;
    public E item;
    public BidirectionalNode<E> next;

    public BidirectionalNode() {
        this.prev = this.next = null;
        this.item = null;
    }

    public BidirectionalNode(E item) {
        this.prev = this.next = null;
        this.item = item;
    }

    public BidirectionalNode(BidirectionalNode<E> prevNode, E newItem, BidirectionalNode<E> nextNode) {
        prev = prevNode; next = nextNode;
        item = newItem;
    }

    public BidirectionalNode<E> getPrev() {
        return prev;
    }

    public E getItem() {
        return item;
    }

    public BidirectionalNode<E> getNext() {
        return next;
    }
}
