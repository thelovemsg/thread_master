package src;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerEx {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            increment();
        });

        Thread t2 = new Thread(() -> {
            increment();
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter: " + counter);
    }

    public static void increment() {
        for (int i = 0; i < 10000; i++) {
            counter.getAndIncrement();
        }
    }
}
