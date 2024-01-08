package section05;

public class ResourceTest {
    /*
    문제점

    InventoryCounter는 공유된 object라는 점이다. (현재 두 스레드로 모두 전달된다.)
    그러므로 items 멤버 변수 또한 두 스레드에서 공유되고 엑세스가 가능해진 것이고
    가장 큰 문제는 두 스레드가 increment 메서드와 decrement메서드를 호출해서 수행하는 작업인 items++와
    item-- 작업은 동시에 실행되는 거라서 사실은 각각 단일 작업이 아니라는 점이다!

    item++는 사실 세 개의 작업이다.
        1. item으로부터 값을 받아온다.
        2. 현재 값에 1을 더한다.
        3. items에 변수를 저장한다.

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

        public void increment() {
            items++;
        }
        public void decrement() {
            items--;
        }
        public int getItems() {
            return items;
        }
    }
}
