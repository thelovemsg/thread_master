package future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SimpleThreadTest {
    public static void main(String[] args) {
        Thread thread = new SimpleThread("Simple", 2);
        thread.start();

        Runnable r = new SimpleRunnable();
        Thread thread1 = new Thread(r);
        thread1.start();
    }

}

class SimpleThread extends Thread {
    private final int secs;

    public SimpleThread(String name, int secs) {
        this.secs = secs;
        this.setName(name);
    }

    @Override
    public void run() {
        System.out.printf("%s : Starting Simple Thread \n", this.getName());
        try {
            TimeUnit.SECONDS.sleep(this.secs);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.printf("%s : Ending Simple Thread \n", this.getName());
    }
}

class SimpleRunnable implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Ending Simple Thread");
    }
}