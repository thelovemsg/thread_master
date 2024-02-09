package section06;

public class DataRace {
    /**
     * 공유 리소스에서 멀티 스레드로 발생할 수있는 두 가지!
     * 경쟁 상태와 데이터 경쟁ㅡ
     *
     * 데이터 경쟁을 피하는 방법?
     *
     * Java 는 일반적으로 다른 스레드를 통해 동시에 일어나는 연산에 대해서는 시맨틱 이전의 순서는 보장하지 않지만 예외은 경우가 몇 가지 있다.
     *
     * 1. synchronized 키워드 사용
     * 2. volatile 키워드를 사용
     *
     * 공유 변수에 volatile을 선언하면 volatile 변수 접근 전에 코드가 접근 명령을 수행하기 전에 실행되도록 하고 접근 명령 이후에 volatile 변수 접근 후의 코드가 실행되도록 한다.
     */
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if(y > x)
                System.out.println("y > x - Data Race is detected");
        }
    }
}
