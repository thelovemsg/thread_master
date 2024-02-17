package src.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executor.execute(new Work(i+1));
        }

        // we prevent the executor to execute any further tasks
        executor.shutdown();

        // terminate actual (running) tasks
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)){
                executor.shutdownNow();
            }
        } catch (InterruptedException e){
            executor.shutdownNow();
        }
    }

    private static class Work implements Runnable {

        private int id;

        public Work(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            System.out.println("Task with id "+id+" is in work - thread id: " + Thread.currentThread());
            long duration = (long) (Math.random() * 5);
            try {
                TimeUnit.SECONDS.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
