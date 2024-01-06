package section02;

public class ErrorControl {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                throw new RuntimeException("error!");
            }
        });

        thread.setName("Misbehaving thread");

        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error happened in thread " + t.getName()
                     + " the error is " + e.getMessage());
            }
        });
        thread.start();
    }
}
