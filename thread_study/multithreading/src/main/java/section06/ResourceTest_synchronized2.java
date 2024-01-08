package section06;

public class ResourceTest_synchronized2 {
    /*
        키워드를 사용하는 두 번째 방법은 임계 영역으로 간주되는 코드의 블록을 정의하고는 synchronized 키워드를 이용해 전체 메서드를 동기화하지는 않으면서 딱 그 영역에 대한 액세스만 제한하는 것이다.

        그러려면 먼저 락의 역할을 하게 될 동기화할 객체를 만들어야 한다.

        어떤 객체든 가능한데 동일한 객체 상에서 동기화된 모든 블록은 해당 블록 안에서는 한 개의 스레드만 실행을 허용하게 된다.

        동기화 블록이나 메서드는 Reentrant 즉, 재진입할 수 있는 요소라는 점이 중요하다.

        다시 말해서, Thread A가 이미 다른 동기화 메서드나 블록에 있는 상태에서 또 동기화 메서드에 엑세스하면
        별 문제 없이 그 동기화 메서드에 엑세스할 수 있게 되는 것이다.
     */
    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();
        incrementingThread.join();
        decrementingThread.join();

        System.out.println("we currently have " + inventoryCounter.getItems() + " items");
    }

    public static class IncrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public IncrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class DecrementingThread extends Thread {
        private InventoryCounter inventoryCounter;

        public DecrementingThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    private static class InventoryCounter {
        private static int items = 0;

        Object lock = new Object();
        public void increment() {
            synchronized (this.lock) {
                items++;
            }
        }
        public synchronized void decrement() {
            synchronized (this.lock) {
                items--;
            }
        }
        public synchronized int getItems() {
            synchronized (this.lock) {
                return items;
            }
        }
    }
}
