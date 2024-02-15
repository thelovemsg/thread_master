package src;

import static java.lang.StringTemplate.STR;

public class IndependentSynchronization {
    public static int counter1 = 0;
    public static int counter2 = 0;

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    /**
     * Because we have instantiated two independent objects, we
     * can use them as independent locks.
     *
     * The first thread is going to execute increment one,
     * and it doesn't have to wait for the second thread
     * finishing with the increment2 and vice versa.
     */
    private static void increment1() {
        // class level locking
        // at the same time != parallel - CPU time slicing
        synchronized (lock1) {
            counter1++;
        }
    }

    private static synchronized void increment2() {
        synchronized (lock2) {
            counter2++;
        }
    }

    /**
     * synchronized block is a bit better programming approach
     * because usually it is a rule of thumb that
     * we synchronize blocks aht are 100% necessary
     */

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment1();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment2();
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(STR."The counter1 is: \{counter1}");
        System.out.println(STR."The counter2 is: \{counter2}");
    }

    public static void main(String[] args) {
        process();
    }
}
