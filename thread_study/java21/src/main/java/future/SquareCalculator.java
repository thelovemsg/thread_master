package future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class SquareCalculator {

    private ExecutorService executors = Executors.newFixedThreadPool(2);

    public Future<Integer> calculate(Integer input) {
        System.out.println("calculating square for :: " + input);
        return executors.submit(() -> {
            Thread.sleep(1000);
            return input * input;
        });
    }

    public void shutdown() {
        executors.shutdown();
    }
}

