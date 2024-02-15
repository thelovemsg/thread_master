package src.executor;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Worker implements Runnable {

    private int id;

    public Worker(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("Task with id"+id+ " is in work - thread id :"+ Thread.currentThread().getId());
        long duration = (long) (Math.random()*5);
        try {
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class FixedThreadPoolExecutor {
    public static void main(String[] args) {
        // it is a single thread that will execute the tasks sequentially so one after another.
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            executor.execute(new Worker(i+1));
        }

        executor.shutdown();

        // It's not terminated yet because we have to shut down the executor !!!
    }
}
