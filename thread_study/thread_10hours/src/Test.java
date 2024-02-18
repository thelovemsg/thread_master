package src;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Test {
    public static void main(String[] args) {
        // Map.Entry 객체들을 value에 따라 오름차순으로 정렬하는 PriorityQueue 생성
        PriorityQueue<Map.Entry<String, Integer>> priorityQueue = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue)
        );

        // Map 생성 및 요소 추가
        Map<String, Integer> map = new HashMap<>();
        map.put("A", 10);
        map.put("B", 5);
        map.put("C", 20);

        // PriorityQueue에 Map.Entry 객체들 추가
        priorityQueue.addAll(map.entrySet());

        // PriorityQueue에서 요소를 하나씩 제거하며 출력
        while (!priorityQueue.isEmpty()) {
            Map.Entry<String, Integer> entry = priorityQueue.poll();
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
