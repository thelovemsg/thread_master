package section06;

import java.util.Random;

/*
    * 데드락이란 모두가 움직이려 하지만 다른 사람이 움직이기를 기다리느라 움직이지 못하는 상태와 같다.

    데드락 방지 방법은? ⇒ 상점? 비순!

    1. 상호 배제(Mutual Exclusion) : 한 번에 한 스레드만 단독으로 리소스에 접근 가능. 예를 들어 한 번에 하나의 기차만 철도를 이용할 수 있다.
    2. 점유와 대기(Hold and wait) : 최소 하나의 스레드가 리소스를 점유하며 다른 리소스에 대기한다.
    3. 비선점 할당(Non-preemtive allocation) : 스레드가 사용 완료할 때까지 리소스르 사용할 수 없다.
    4. 순환대기(Circular wait) : 한 스레드가 철도 A를 점유하며 다른 스레드가 점유한 철도 B를 기다리고 철도 B를 점유한 스레드는 철도 A를 기다리느 는 상황.

    데드락의 확실한 솔루션은 네 가지 조건 중 하나라도 충족하지 않게 만드는 것이다.

    가장 간단한 방법은 순환 대기를 예방하는 것이다.

    동일한 순서로 공유 리소스를 잠그고 모든 코드에 해당 순서를 유지하면 된다.  (가장 간단하고 쉽다.)

    그런데 복잡한 프로그램의 경우에는 순서를 유지하기가 힘드므로 이러한 경우에는 데드락을 감지하는 감지 장치를 사용하는 것이다. (Use watchdog!)
* */

public class LockingAndDeadlock {

    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();

    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                intersection.takeRoadA();
            }
        }
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        @Override
        public void run() {
            while(true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                intersection.takeRoadB();
            }
        }
    }


    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by thread " + Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Road B is locked by thread road A ");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadA) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());

                synchronized (roadB) {
                    System.out.println("Road A is locked by thread road B ");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
