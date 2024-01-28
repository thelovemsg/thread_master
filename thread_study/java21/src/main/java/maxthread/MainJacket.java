package maxthread;

import java.util.ArrayList;
import java.util.List;

public class MainJacket {
    private static void handleUserRequest() {
//        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        System.out.println("Ending thread " + Thread.currentThread());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting main " + Thread.currentThread());
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add(startThread());
        }

        // join on the threads
        for (Thread thread : list) {
            thread.join();
        }

        System.out.println("Ending main " + Thread.currentThread());
    }

    private static Thread startThread() {
//        new Thread(() -> handleUserRequest()).start();
        return Thread.startVirtualThread(() -> handleUserRequest());
    }

}
