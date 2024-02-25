package src.stream;

import java.util.Comparator;
import java.util.stream.Stream;

public class StreamString {
    public static void main(String[] args) {
        String[] names = {"Adam", "Daniel", "Mertha", "Kevin", "Ben", "Joe", "Brad", "Susan"};

        Stream.of(names).sorted(Comparator.reverseOrder()).forEach(System.out::println);
    }
}
