package thread_study.thread_10hours.src;

class Process {
    public void consume() throws InterruptedException {
        Thread.sleep(1000);
        // => this block means they are going to get the
        // intrinsic lock of the process object
        synchronized (this) {
            System.out.println("Consume method is executed...");
            notify();
            // it is not going to handle the lock: we cane make further operations.
        }
    }

    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Running the produce method...");
            wait();
            System.out.println("Again in the producer method...");
        }
    }
}

public class ThreadCommunication {
    public static void main(String[] args) {
        Process process = new Process();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    process.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
