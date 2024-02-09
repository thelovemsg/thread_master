package src;

public class MemoryTest {
    public static int counter = 0;

    /**
     * we have to make sure this method is executed only by a single thread
     * at a given time
     */
    private synchronized static void  increment() {
        counter++;
    }

    public static void process() {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    increment();
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

        System.out.println(STR."The counter is: \{counter}");
    }
    
    public static void main(String[] args) {
        process();
    }
}
