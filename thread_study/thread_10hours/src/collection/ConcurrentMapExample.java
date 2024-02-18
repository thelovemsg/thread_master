package src.collection;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class MapFirstWorker implements Runnable {

    private ConcurrentMap<String, Integer> map;

    public MapFirstWorker(ConcurrentMap<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            map.put("B", 12);
            Thread.sleep(1000);
            map.put("Z", 5);
            map.put("A", 25);
            Thread.sleep(2000);
            map.put("D", 19);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MapSecondWorker implements Runnable {

    private ConcurrentMap<String, Integer> map;

    public MapSecondWorker(ConcurrentMap<String, Integer> map) {
        this.map = map;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            System.out.println(map.get("A"));
            Thread.sleep(2000);
            System.out.println(map.get("Z"));
            System.out.println(map.get("B"));
            System.out.println(map.get("D"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ConcurrentMapExample {
    public static void main(String[] args) {
        ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
        MapFirstWorker first = new MapFirstWorker(map);
        MapSecondWorker second = new MapSecondWorker(map);

        new Thread(first).start();
        new Thread(second).start();
    }
}
