package src;

import static java.lang.StringTemplate.STR;

public class MemoryTest {
    public static int counter1 = 0;
    public static int counter2 = 0;

    /**
     * we have to make sure this method is executed only by a single thread
     * at a given time
     */

    /**
     * because App object has a single lock: this is why the methods
     * can not be executed "at the same time"
     * - time slicing algorithm
     */
//    private synchronized static void increment1() {
//        counter1++;
//    }

    // usually it is not a good practice to use synchronized keyword
    // we should define synchronized
    private static void increment1() {
        // class level locking
        synchronized (MemoryTest.class) {
            counter1++;
        }
    }

    private static synchronized void increment2() {
        synchronized (MemoryTest.class) {
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
