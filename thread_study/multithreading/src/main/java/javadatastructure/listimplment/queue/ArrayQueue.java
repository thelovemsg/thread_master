package javadatastructure.listimplment.queue;

public class ArrayQueue<E> implements QueueInterface<E> {

    private E queue[];
    private int front, tail, numItems;
    private static final int DEFAULT_CAPACITY = 64;
    private final E ERROR = null;

    public ArrayQueue() {
        queue = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        tail = DEFAULT_CAPACITY - 1;
        numItems = 0;
    }

    @Override
    public void enqueue(E newItem) {
        if (isFull()) {
            System.out.println("queue is full!");
        } else {
            tail = (tail+1) % queue.length;
            queue[tail++] = newItem;
            numItems++;
        }
    }

    private boolean isFull() {
        return numItems == DEFAULT_CAPACITY;
    }

    @Override
    public E dequeue() {
        if (isEmpty())
            return ERROR;
        E queueFront = queue[front];
        front = (front+1) % queue.length;
        return queueFront;
    }

    @Override
    public E front() {
        if(isEmpty()) return ERROR;
        return queue[front];
    }

    @Override
    public boolean isEmpty() {
        return numItems == 0;
    }

    @Override
    public void dequeueAll() {
        queue = (E[]) new Object[DEFAULT_CAPACITY];
        front = 0;
        tail = queue.length-1;
        numItems = 0;
    }
}
