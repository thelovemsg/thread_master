package section10;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class IOBoundApplication {
    private static final int NUMBER_OF_TASK = 10000;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Press enter to start");
        s.nextLine();
        System.out.printf("Running %d task\n", NUMBER_OF_TASK);

        long start = System.currentTimeMillis();
        performTasks();
        System.out.printf("Task took %dms ot complete\n", System.currentTimeMillis() - start);
    }

    private static void performTasks() {
        ExecutorService service = Executors.newFixedThreadPool(1000);
        try {
            for (int i = 0; i < NUMBER_OF_TASK; i++) {
                service.submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 100; j++) {
                            blockingIoOperation();
                        }
                    }
                });
            }
        } finally {
            service.shutdown(); // 서비스 종료
            try {
                if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                    service.shutdownNow(); // 대기 시간 초과 후 서비스 즉시 종료
                }
            } catch (InterruptedException ie) {
                service.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

    }

    private static void blockingIoOperation() {
        System.out.println("Execution a blocking task from thread: " + Thread.currentThread());
        try {
            Thread.sleep(10); // => 블로킹 IO 연산과 유사하게 동작.
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
