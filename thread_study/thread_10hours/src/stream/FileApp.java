package src.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileApp {
    public static void main(String[] args) throws IOException {
        String path = "D:/IntelliJ_IDEA/udemy/thread/thread_study/thread_10hours/src/stream/names";

        Stream<String> namesStream = Files.lines(Paths.get(path));

        List<String> names = namesStream.collect(Collectors.toList());

    }
}
