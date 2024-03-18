package javadatastructure.listimplment.stack;

import javadatastructure.listimplment.link.LinkedList;

public class InheritedStack<E> extends LinkedList implements StackInterface<E> {

    public InheritedStack() {
        super();
    }

    @Override
    public void push(E newItem) {
        add(0, newItem);
    }

    @Override
    public E pop() {
        if (!isEmpty())
            return (E) remove(0);
        return null;
    }

    @Override
    public E top() {
        return (E) get(0);
    }

    @Override
    public void popAll() {
        clear();
    }
}
