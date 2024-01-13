package section07;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. ReentrantReadWriteLock
 * 읽기 잠금 가능,쓰기 잠금 가능
 *
 * 이러한 잠금 방식은 특히 읽기 작업이 쓰기 작업보다 훨씬 더 빈번하게 발생하는 경우에 유용합니다.
 * 예를 들어, 이 코드에서는 읽기 스레드가 7개 있고 쓰기 스레드는 하나뿐입니다. 이 경우 ReentrantReadWriteLock을 사용하면 읽기 작업이 서로 방해받지 않고 동시에 수행될 수 있으므로 전체 성능이 향상됩니다.
 *
 *
 * 2. 논데몬 스레드는 왜 멈추지 않을까?
 * 논데몬 스레드(non-daemon thread)로 설정된 장시간 실행되는 작업이 있는 경우 프로그램이 종료되지 않는 이유는 Java의 스레드 관리 및 프로그램 종료 조건에 기반합니다. 이를 이해하기 위해서는 Java 가상 머신(JVM)이 프로그램을 어떻게 종료시키는지 알아야 합니다.
 *
 * Java의 스레드 관리 및 프로그램 종료 조건
 * 논데몬 스레드의 중요성: Java에서 논데몬 스레드는 애플리케이션의 주요 작업을 수행합니다. JVM은 논데몬 스레드가 모두 종료될 때까지 프로그램의 실행을 계속 유지합니다. 즉, 프로그램은 모든 논데몬 스레드가 작업을 완료하고 종료될 때까지 계속 실행됩니다.
 *
 * 장시간 실행되는 논데몬 스레드: 만약 논데몬 스레드가 장시간 실행되는 작업을 수행하고 있다면 (예를 들어, 무한 루프에 빠져 있거나 매우 긴 계산을 수행하고 있다면), 이 스레드는 종료되지 않습니다. 이 경우, JVM은 이 스레드가 계속 실행 중임을 감지하고 프로그램을 종료시키지 않습니다.
 *
 * 프로그램 종료의 지연: 따라서, 이러한 논데몬 스레드가 종료되지 않는 한, 프로그램은 계속 실행 상태에 머무르게 됩니다. 이것은 프로그램이 의도치 않게 종료되지 않는 상황을 만들어, 자원을 계속 소모하고 시스템의 성능을 저하시킬 수 있습니다.
 *
 * 요약
 * 논데몬 스레드는 프로그램의 주요 작업을 담당하며, 이들 스레드가 모두 종료되어야만 JVM이 프로그램을 종료시킵니다.
 * 장시간 실행되는 논데몬 스레드가 있으면, 이 스레드가 종료될 때까지 프로그램도 계속 실행됩니다.
 * 이러한 방식으로, 장시간 실행되는 작업을 수행하는 논데몬 스레드가 있는 경우 프로그램이 종료되지 않는 것은 Java의 스레드 관리 및 프로그램 종료 메커니즘의 일부입니다.
 *
 * 3. 속도 차이가 나는 이유
 * ReentrantLock과 ReadWriteLock은 서로 다른 사용 사례와 성능 특성을 가집니다.
 *
 * ReentrantLock: 이것은 일반적인 상호 배제 잠금(mutual exclusion lock)으로, 한 시점에 하나의 스레드만이 잠금을 획득할 수 있습니다.
 * 이는 읽기와 쓰기 작업 모두에 대해 동시 접근을 방지합니다. 따라서 모든 종류의 작업(읽기 또는 쓰기)에 대해 잠금이 필요할 때 사용됩니다.
 *
 * ReentrantReadWriteLock: 이것은 두 종류의 잠금, 즉 읽기 잠금(read lock)과 쓰기 잠금(write lock)을 제공합니다.
 * 읽기 잠금은 여러 스레드가 동시에 데이터를 읽을 수 있게 해주지만, 쓰기 잠금은 한 번에 하나의 스레드만이 데이터를 쓸 수 있게 합니다.
 * 이 구조는 읽기 작업이 쓰기 작업보다 훨씬 빈번한 경우에 특히 유용하며, 이러한 시나리오에서 성능 향상을 가져올 수 있습니다.
 *
 * 성능 차이가 발생하는 이유는 다음과 같습니다:
 *
 * 병렬 읽기: ReadWriteLock을 사용할 때, 여러 스레드가 동시에 읽기 작업을 수행할 수 있습니다. 이는 읽기 작업이 병렬로 실행될 수 있음을 의미하며, 특히 읽기 작업이 많은 경우 성능을 크게 향상시킵니다.
 * 쓰기 잠금 제한: 쓰기 작업이 진행될 때는 다른 어떤 스레드도 읽기 또는 쓰기 작업을 수행할 수 없습니다. 이는 데이터의 일관성과 무결성을 유지하는 데 필수적입니다.
 * 쓰기 작업에 대한 대기 시간: 읽기 잠금이 많이 사용될 경우, 쓰기 잠금을 획득하려는 스레드는 더 긴 대기 시간을 갖게 될 수 있습니다.
 * 따라서, 애플리케이션이 읽기 중심이며 읽기 작업이 쓰기 작업보다 훨씬 많은 경우 ReadWriteLock이 좋은 선택이 될 수 있습니다. 그러나 쓰기 작업이 상대적으로 더 많거나 읽기와 쓰기 작업이 대략 동일한 빈도로 발생하는 경우, ReentrantLock이 더 적합할 수 있습니다.
 *
 * 성능 측면에서, 적절한 잠금 메커니즘의 선택은 애플리케이션의 특정 요구 사항과 데이터 접근 패턴에 따라 달라집니다.
 *
 * 4. 읽기 작업에도 잠금이 필요한 이유?
 * 읽기 작업에도 잠금(Read Lock)이 필요한 이유는 주로 데이터의 일관성과 무결성을 유지하기 위함입니다. 멀티스레딩 환경에서 데이터에 대한 동시 접근이 발생할 때, 다음과 같은 문제들이 발생할 수 있습니다:
 *
 * 동시 읽기-쓰기 문제: 한 스레드가 데이터를 읽고 있을 때 다른 스레드가 동시에 같은 데이터를 수정하면, 읽는 스레드는 일관성 없는 데이터를 읽게 됩니다. 이는 잘못된 정보를 제공하거나 예기치 않은 동작을 초래할 수 있습니다.
 *
 * 레이스 컨디션(Race Condition): 둘 이상의 스레드가 동시에 데이터에 접근하여 이를 수정하려고 할 때 발생하는 문제입니다. 읽기 작업도 데이터의 상태에 따라 다른 결과를 낼 수 있기 때문에, 쓰기 작업과의 동시 실행은 레이스 컨디션을 일으킬 수 있습니다.
 *
 * 읽기 잠금(Read Lock)은 다음과 같은 이점을 제공합니다:
 *
 * 일관된 데이터 읽기: 읽기 잠금을 사용하면 쓰기 작업이 진행되는 동안 읽기 작업이 대기하게 됩니다. 이는 쓰기 작업에 의해 데이터가 변경되는 동안 잘못된 또는 불완전한 데이터를 읽는 것을 방지합니다.
 * 병렬 읽기 지원: ReentrantReadWriteLock의 경우, 여러 스레드가 동시에 읽기 잠금을 얻을 수 있어, 동시에 여러 읽기 작업을 수행할 수 있습니다. 이는 쓰기 작업이 없는 경우에 효율적입니다.
 * 결국, 읽기 잠금은 멀티스레딩 환경에서 데이터의 일관성을 유지하고, 동시에 여러 읽기 작업의 효율적인 수행을 가능하게 하는 중요한 역할을 합니다.
 *
 * 읽기 잠금(Read Lock)의 주요 목적은 읽는 동안에 쓰기 작업이 이루어지지 않도록 하는 것이며, 이를 통해 동시성과 데이터의 일관성을 유지합니다.
 */
public class ReentrantLockReadWriteMain {
    public static final int HIGHEST_PRICE = 1000;

    public static void main(String[] args) throws InterruptedException {
        InventoryDatabase inventoryDatabase = new InventoryDatabase();

        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
        }
        Thread writer = new Thread(() -> {
            while (true) {
                inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
                inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        writer.setDaemon(true);
        writer.start();

        int numberOfReaderThreads = 7;
        List<Thread> readers = new ArrayList<>();

        for (int readerIndex = 0; readerIndex < numberOfReaderThreads; readerIndex++) {
            Thread reader = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    int upperBoundPrice = random.nextInt(HIGHEST_PRICE);
                    int lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
                    inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
                }
            });
            reader.setDaemon(true);
            readers.add(reader);
        }

        long startReadingTime = System.currentTimeMillis();

        for (Thread reader : readers) {
            reader.start();
        }
        for (Thread reader : readers) {
            reader.join();
        }

        long endReadingTime = System.currentTimeMillis();
        System.out.println(String.format("reading took %d ms", endReadingTime - startReadingTime));
    }

    public static class InventoryDatabase {
        private TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();
        private ReentrantLock lock = new ReentrantLock();
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();

        public int getNumberOfItemsInPriceRange(int lowerBound, int upperBound) {
            readLock.lock();
            try {
                Integer fromKey = priceToCountMap.ceilingKey(lowerBound);
                Integer toKey = priceToCountMap.floorKey(upperBound);

                if (fromKey == null || toKey == null) {
                    return 0;
                }

                NavigableMap<Integer, Integer> rangeOfPrices = priceToCountMap.subMap(fromKey, true, toKey, true);

                int sum = 0;
                for (int numberOfItemsForPrice : rangeOfPrices.values()) {
                    sum += numberOfItemsForPrice;
                }
                return sum;
            } finally {
                readLock.unlock();
            }
        }

        public void addItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null) {
                    priceToCountMap.put(price, 1);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice + 1);
                }
            } finally {
                writeLock.unlock();
            }
        }

        public void removeItem(int price) {
            writeLock.lock();
            try {
                Integer numberOfItemsForPrice = priceToCountMap.get(price);
                if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
                    priceToCountMap.remove(price);
                } else {
                    priceToCountMap.put(price, numberOfItemsForPrice - 1);
                }
            } finally {
                writeLock.unlock();
            }
        }

    }
}
