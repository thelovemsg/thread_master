package section06;

public class ResourceTest_synchronized {
    /*
        Synchronized

        - Locking 메커니즘
        - 여러개의 스레드가 코드 블록이나 전체 메서드에 액세스할 수 없도록 설계된 락킹 메커니즘입니다.

        synchronized 키워드를 이용해서 하나 이상의 메서드를 선언한다.

        여러 개의 스레드가 이 클래스의 동일한 객체에서 해당 메서드를 호출하려고 하면, 한 개의 스레드만 메서드 중 하나를 실행할 수 있게 된다.

        method1()과 method2()가 synchronized 키워드가 선언되어 있고 Thread A가 method1을 실행하면 Thread B는 method1과 2 모두를 실행할 수 없다.

        각 동기화된 메서드를 하나의 방에 대한 문 하나로 생각하면 된다.
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

        public synchronized void increment() {
            items++;
        }
        public synchronized void decrement() {
            items--;
        }
        public synchronized int getItems() {
            return items;
        }
    }
}
