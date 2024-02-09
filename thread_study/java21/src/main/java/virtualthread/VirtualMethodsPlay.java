package virtualthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.Executors.*;

public class VirtualMethodsPlay {

    private static void handleUserRequest() {
        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ending thread " + Thread.currentThread());
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Starting main");
//        playWithVirtualBuilder();
        playWithVirtualExecutorService();
        System.out.println("Ending main");
    }

    private static void playWithVirtualBuilder() throws InterruptedException {
        ThreadFactory factory = Thread.ofVirtual().name("userthread", 0).factory();
        Thread vThread1 = factory.newThread(VirtualMethodsPlay::handleUserRequest);
        vThread1.start();
        Thread vThread2 = factory.newThread(VirtualMethodsPlay::handleUserRequest);
        vThread2.start();

        vThread1.join();
        vThread2.join();
    }

    private static void playWithVirtualExecutorService() {
        //Create an Virtual Thread ExecutorService
        //Note the try with resource which will make sure all Virtual thread are terminated
        ThreadFactory factory = Thread.ofVirtual().name("userthread", 0).factory();
        try (ExecutorService srv = Executors.newThreadPerTaskExecutor(factory)){
//        try (ExecutorService srv = Executors.newVirtualThreadPerTaskExecutor()){
            //Submit two tasks to the Executor service
            srv.submit(VirtualMethodsPlay::handleUserRequest);
            srv.submit(VirtualMethodsPlay::handleUserRequest);
        }
    }
}
