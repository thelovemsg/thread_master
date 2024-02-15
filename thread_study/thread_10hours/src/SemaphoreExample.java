package src;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

// singleton pattern
enum Downloader {
    INSTANCE;
    private Semaphore semaphore = new Semaphore(3, true);

    public void download() {
        try {
            semaphore.acquire();
            downloadData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    private void downloadData() {
        try {
            System.out.println("Downloading data from the web...ㅌ");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SemaphoreExample {
    /**
     * It is used to contrl access to a shared resource that uses a counter variable
     *  //semaphore maintains a set of permits
     *
     *  - acquire() ... if a permit is available then takes it
     *  - release() ... adds a permit
     *
     *      semaphore just keeps a count of the number of permits available new Semaphore(int permits, boolean fair) !!!
     * @param args
     */
    public static void main(String[] args) {
        // create multiple threads - executors
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 12; i++) {
            service.execute(new Runnable() {
                public void run() {
                    Downloader.INSTANCE.download();
                }
            });
        }
    }
}
