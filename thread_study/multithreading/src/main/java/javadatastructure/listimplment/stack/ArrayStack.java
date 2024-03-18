package javadatastructure.listimplment.stack;

public class ArrayStack<E> implements StackInterface<E>{

    private E stack[];
    private int topIndex;
    private static final int DEFAULT_CAPACITY = 64;
    private final E ERROR = null;

    public ArrayStack(E[] stack) {
        this.stack = (E[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    public ArrayStack(int n) {
        stack = (E[]) new Object[n];
        topIndex = -1;
    }

    @Override
    public void push(E newItem) {
        if (ifFull()) {
            throw new RuntimeException("full!");
        } else {
            stack[++topIndex] = newItem;
        }
    }

    private boolean ifFull() {
        return topIndex == stack.length -1;
    }

    @Override
    public E pop() {
        if(isEmpty()) return ERROR;
        return stack[topIndex--];
    }

    @Override
    public E top() {
        if(isEmpty()) return ERROR;
        return stack[topIndex];
    }

    @Override
    public boolean isEmpty() {
        return (topIndex < 0);
    }

    @Override
    public void popAll() {
        stack = (E[]) new Object[stack.length];
        topIndex = -1;
    }
}
