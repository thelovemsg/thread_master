package src;

public class App {
    public static void main(String[] args) throws InterruptedException {
        // IT IS NOT PARALLEL EXECUTION
        // it's multi-threading
//        Thread t1 = new Thread(new Runner1());
//        Thread t2 = new Thread(new Runner2());
//
//        t1.start();
//        t2.start();

        Thread t11 = new Runner11();
        Thread t22 = new Runner22();

        t11.start();
        t22.start();

        // we can wait for the thread to finish : join()
        t11.join();
        t22.join();

        System.out.println("Finished with threads...");

    }
}

class Runner1 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(STR."Runner1 : \{i}");
        }
    }
}

class Runner2 implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(STR."Runner2 : \{i}");
        }
    }
}

class Runner11 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(STR."Runner11 : \{i}");
        }
    }
}

class Runner22 extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(STR."Runner22 : \{i}");
        }
    }
}
