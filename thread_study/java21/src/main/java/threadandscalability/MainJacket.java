package threadandscalability;

public class MainJacket {
    private static void handleUserRequest() {
        System.out.println("Starting thread " + Thread.currentThread());

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Ending thread " + Thread.currentThread());
    }

    public static void main(String[] args) {
        System.out.println("Starting main");
        for (int i = 0; i < 100000; i++) {
            new Thread(() -> handleUserRequest()).start();
        }
        System.out.println("Ending main");
    }

}
