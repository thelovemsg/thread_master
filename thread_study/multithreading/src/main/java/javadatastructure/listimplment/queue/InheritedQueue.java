package javadatastructure.listimplment.queue;

import javadatastructure.listimplment.link.LinkedList;

public class InheritedQueue<E> extends LinkedList<E> implements QueueInterface<E> {

    public InheritedQueue() {
        super();
    }

    @Override
    public void enqueue(E newItem) {
        append(newItem);
    }

    @Override
    public E dequeue() {
        return remove(0);
    }

    @Override
    public E front() {
        return get(0);
    }

    @Override
    public void dequeueAll() {
        clear();
    }
}
