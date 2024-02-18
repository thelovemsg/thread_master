package src.collection;


import java.util.concurrent.Exchanger;

class ExchangeFirstThread implements Runnable {

    private int counter;
    private Exchanger<Integer> exchanger;

    public ExchangeFirstThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        // it will run forever (infinite loop)
        while (true) {
            counter++;
            System.out.println(STR."FirstThread incremented the counter : \{counter}");

            try {
                counter = exchanger.exchange(counter);
                System.out.println(STR."FirstThread get the counter : \{counter}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class ExchangeSecondThread implements Runnable {

    private int counter;
    private Exchanger<Integer> exchanger;

    public ExchangeSecondThread(Exchanger<Integer> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {

        // it will run forever (infinite loop)
        while (true) {
            counter--;
            System.out.println(STR."SecondThread decremented the counter : \{counter}");

            try {
                counter = exchanger.exchange(counter);
                System.out.println(STR."SecondThread get the counter : \{counter}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ExchangerExample {

    /*
    With the help of the Exchanger -> two threads can exchanged objects

    exchange() -> exchanging objects is done via one of the two exchange() methods
     */

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();
        ExchangeFirstThread t1 = new ExchangeFirstThread(exchanger);
        ExchangeSecondThread t2 = new ExchangeSecondThread(exchanger);

        new Thread(t1).start();
        new Thread(t2).start();
    }
}
