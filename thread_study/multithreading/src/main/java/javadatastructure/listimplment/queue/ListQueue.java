package javadatastructure.listimplment.queue;

import javadatastructure.listimplment.link.LinkedList;
import javadatastructure.listimplment.link.ListInterface;

import java.util.ArrayList;

public class ListQueue<E> implements QueueInterface<E> {

    private ListInterface<E> list;

    public ListQueue() {
        list = new LinkedList<E>();
    }

    @Override
    public void enqueue(E newItem) {
        list.append(newItem);
    }

    @Override
    public E dequeue() {
        return list.remove(0);
    }

    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void dequeueAll() {
        list.clear();
    }
}
