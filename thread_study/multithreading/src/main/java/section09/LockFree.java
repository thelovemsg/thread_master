package section09;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 데드락
 * 2. 같은 락을 사용하는 멀티 스레드가 있는 경우 스레드 하나가 나머지보다 락을 더 오래 가지고 있을 수 있다. 스레드 하나만 같은 락으로 보호되는 임계 영역에 들어갈 수 있는데 긴 임계 영역이 다른 스레드를 느리게 할 수 있다.  따라서 너머지 스레드가 전부 가장 느려지는 상황에 처한다.
 * 3. 우선순위 역전 : 리스소 하나와 그 로시스의 락을 공유하는 스레드가 두 개일 때 생기는 문제. 스레드 하나가 운영체제에 의해 낮은 우선순위를 갖게 되는 것이다.
 * 4. 스레드가 락을 지닌 채 그냥 죽거나 인터럽트 될 때다. 이를 방지하기 위해 개발자는 모든 중요 영역을 타임아웃을 지닌 tryLock을 사용해 try와 block하여 감싸는 등의 복잡한 코드를 쓰도록 요구한다.
 * 5. 성능 오버헤드 : 락을 얻기 위한 스레드 간의 다툼이 생긴다. 스레드 A가 락을 획득하면 스레드 B가 그 락을 가지려 해 스레드 B가 블록된다. 그러면 스레드 B가 블록되어 스레드 B에서 다른 스레드로의 컨텍스트 스위치가 일어난다. 따라서 락이 릴리스 될 때 스레드 B를 가져오는 오버헤드가 일어난다.
 *
 * 그러면 락에 대한 대안이 있을까?
 *
 * 최초 락이 필요했던 이유는?
 *
 * 문제의 핵심은 멀티 스레드가 리소스를 공유하고 최소 하나의 스레드가 리소스를 수정하여 원자적 연산이 되지 않는 것이다.
 *
 *
 * Lock-Free 알고리즘은 전통적인 락(lock) 기반의 동기화를 사용하지 않고
 * , 멀티스레드 환경에서 동시성을 관리하는 접근법입니다. 이 방법은 락을 사용하지 않기 때문에,
 * 락으로 인한 문제점들(예: 데드락, 락 경합, 스레드 블록킹)을 피할 수 있습니다. 그러나 모든 상황에서 락-프리 방식이 적합하거나 효과적인 것은 아닙니다.
 *
 * Lock-Free 알고리즘의 특징:
 * 원자적 연산 사용: Lock-Free 알고리즘은 원자적 연산(atomic operations)을 사용합니다. 이는 멀티스레드 환경에서도 중단되거나 방해받지 않고 한 번에 완료되는 연산입니다.
 * Java에서는 java.util.concurrent.atomic 패키지를 통해 원자적 변수를 제공합니다.
 *
 * 비차단(non-blocking): 스레드가 자원을 기다리는 동안 차단(block)되지 않습니다. 대신, 다른 스레드가 자원을 사용하고 있지 않다면 연산을 수행하고, 그렇지 않으면 반복적으로 시도하거나 다른 작업을 수행합니다.
 *
 * CAS(Compare-And-Swap) 연산: Lock-Free 알고리즘에서 주로 사용되는 CAS 연산은 값을 비교하고, 조건에 맞으면 새로운 값으로 교체하는 원자적 연산입니다.
 *
 * Lock-Free 알고리즘의 한계:
 * 복잡성: Lock-Free 프로그래밍은 구현이 복잡하고, 올바르게 작성하기 어려울 수 있습니다. 잘못 구현된 경우, 성능 문제나 예측하지 못한 동작이 발생할 수 있습니다.
 *
 * ABA 문제: CAS 연산은 ABA 문제에 취약할 수 있습니다. 이는 공유 자원이 A에서 B로, 그리고 다시 A로 변경되었을 때, CAS 연산이 이를 구별하지 못하는 문제입니다.
 *
 * 스핀-로킹(spin-locking)과 버지경합(livelock): 스레드가 계속해서 자원을 획득하려 시도하는 과정에서 CPU 시간을 낭비할 수 있으며, 이는 시스템의 전체적인 성능 저하로 이어질 수 있습니다.
 *
 * 결론
 * Lock-Free 알고리즘은 특정 시나리오에서 매우 유용할 수 있지만, 모든 상황에 적합한 만능 해결책은 아닙니다.
 * 복잡도와 특정 문제에 대한 해결책을 고려하여, 전통적인 락 기반의 접근법과 Lock-Free 접근법 중 어느 것이 주어진 문제에 더 적합한지 판단해야 합니다.
 * 때로는 두 접근법을 혼합하여 사용하는 것이 최선의 해결책일 수도 있습니다.
 */
public class LockFree {

    public static void main(String[] args) throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println(inventoryCounter.getItems());
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
        private AtomicInteger items = new AtomicInteger(0);

        public void increment() {
            items.incrementAndGet();
        }

        public void decrement() {
            items.decrementAndGet();
        }

        public int getItems() {
            return items.get();
        }
    }
}
