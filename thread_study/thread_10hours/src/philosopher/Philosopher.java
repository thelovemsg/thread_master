package src.philosopher;

import java.util.Random;

public class Philosopher implements Runnable{

    private int id;
    private volatile boolean full;
    private Chopstick leftChopstick;
    private Chopstick rightChopstick;
    private Random random;
    private int eatingCounter;

    public Philosopher(int id, Chopstick leftChopstick, Chopstick rightChopstick) {
        this.id = id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {

            //after eating a log (1000) then we will terminate the given thread
            while(!full) {
                think();
                if(leftChopstick.pickUp(this, State.LEFT)) {
                    // the philosophers is able to acquire the left chopstick
                    if(rightChopstick.pickUp(this, State.RIGHT)){
                        // the philosophers is able to acquire the right chopstick
                        eat();
                        rightChopstick.putDown(this, State.RIGHT);
                    }
                    leftChopstick.putDown(this, State.LEFT);
                }
                eat();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void think() throws InterruptedException {
        System.out.println(STR."\{this}: is thinking...");
        // philosopher thinks for a random time [0,1000]
        Thread.sleep(random.nextInt(1000));
    }

    private void eat() throws InterruptedException {
        System.out.println(STR."\{this}: is eating...");
        // philosopher eats for a random time [0,1000]
        eatingCounter++;
        Thread.sleep(random.nextInt(1000));
    }

    public void setFull(boolean isFull) {
        this.full = isFull;
    }


    public boolean isFull() {
        return this.full;
    }

    @Override
    public String toString() {
        return STR."Philosopher \{id}";
    }

    public int getEatingCounter() {
        return eatingCounter;
    }
}
