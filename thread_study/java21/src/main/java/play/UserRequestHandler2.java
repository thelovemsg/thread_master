package play;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class UserRequestHandler2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            String output = CompletableFuture.supplyAsync(this::dbCall, service)
                    .thenCombine(CompletableFuture.supplyAsync(this::restCall, service), (result1, result2) -> {
                        return "[" + result1 + "," + result2 + "]";
                    })
                    .thenApply(result -> {
                        //both dbCall and restCall have completed
                        String r = externalCall();
                        return "[" + result + "," + r + "]";
                    })
                    .join();
            System.out.println("output = " + output);
            return output;
        }
//        return concurrentCallFunctional();
//        return concurrentCallWithFutures();
    }

    private String concurrentCallFunctional() throws InterruptedException {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {
            String result = service.invokeAll(Arrays.asList(this::dbCall, this::restCall))
                    .stream()
                    .map(f -> {
                        try {
                            return (String) f.get();
                        } catch (Exception e) {
                            return null;
                        }
                    }).collect(Collectors.joining(","));
            return "[" + result + "]";
        }
    }

    private String concurrentCallWithFutures() throws InterruptedException, ExecutionException {
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()) {

            long start = System.currentTimeMillis();

            Future<String> dbFuture = service.submit(this::dbCall);
            Future<String> restFuture = service.submit(this::restCall);
            String result = String.format("[%s %s]", dbFuture.get(), restFuture.get());

            long end = System.currentTimeMillis();
            System.out.println("time = " + (end - start));
            System.out.println(result);

            return result;
        }
    }

    private String sequentialCall() throws InterruptedException {
        long start = System.currentTimeMillis();

        //sequential coding
        String result1 = dbCall();
        String result2 = restCall();

        Thread.sleep(Duration.ofMinutes(10));
        String result = String.format("[%s %s]", result1, result2);

        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - start));

        System.out.println(result);
        return result;
    }

    private String dbCall() {
        try {
            NetworkCaller caller = new NetworkCaller("data");
            return caller.makeCall(2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String restCall() {
        try {
            NetworkCaller caller = new NetworkCaller("rest");
            return caller.makeCall(5);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String externalCall() {
        try {
            NetworkCaller caller = new NetworkCaller("extn");
            return caller.makeCall(4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
