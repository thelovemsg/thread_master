package section02;

public class Basic {
    public static void main(String[] args) throws InterruptedException {

        //1. thread를 생성
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("We are now in thread " + Thread.currentThread().getName());
                System.out.println("Current thread priority is " + Thread.currentThread().getPriority());
            }
        });

        //스레드 이름 설정
        thread.setName("New Worker Thread");

        //스레드 우선순위 설정
        thread.setPriority(Thread.MAX_PRIORITY);

        //2. 쓰레드 실행 -> JVM이 새 스레드를 생성해 운영체제에 전달한다.
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("we are in thread: " + Thread.currentThread().getName() + " after starting a new thread");

        //1초동안 운영체제에게 이 이 스레드를 멈추시오!
        Thread.sleep(1000);
    }
}
