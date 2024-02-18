package src.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class PBFirstWorker implements Runnable {

    private BlockingQueue<String> queue;

    public PBFirstWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            queue.put("B");
            queue.put("H");
            queue.put("F");
            Thread.sleep(2000);
            queue.put("A");
            Thread.sleep(1000);
            queue.put("Z");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Person implements Comparable<Person> {

    private int age;
    private String name;

    public Person(int gae, String name) {
        this.age = gae;
        this.name = name;
    }

    @Override
    public int compareTo(Person person) {
        return Integer.compare(this.age, person.getAge());
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class PBSecondWorker implements Runnable {

    private BlockingQueue<String> queue;

    public PBSecondWorker(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(queue.take());
            Thread.sleep(1000);
            System.out.println(queue.take());
            Thread.sleep(2000);
            System.out.println(queue.take());
            System.out.println(queue.take());
            System.out.println(queue.take());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

public class PriorityBlockingQueueExample {
    /**
     * It implements the BlockingQueue interface
     *
     * - unbounded concurrent queue
     * - it uses the same ordering rules as the java.util.PriorityQueue class
     *      and we have to implement the Comparable interface
     *
     *          // it determines what will the order in the queue
     *
     *      The priority can be the same compare == 0 case
     *
     * - no null items are allowed !!!
     */

    public static void main(String[] args) {
        BlockingQueue<String> queue = new PriorityBlockingQueue<>();
        PBFirstWorker first = new PBFirstWorker(queue);
        PBSecondWorker second = new PBSecondWorker(queue);

        new Thread(first).start();
        new Thread(second).start();

    }
}