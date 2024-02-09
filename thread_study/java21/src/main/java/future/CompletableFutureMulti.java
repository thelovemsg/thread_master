package future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureMulti {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        System.out.println("future = " + future.get());

        CompletableFuture<String> future1 = future.thenApply(s -> s + " World");
        System.out.println("future1 = " + future1.get());

        CompletableFuture<Void> future2 = future1.thenAccept(s -> System.out.println("Computation returned : " + s));
    }
}
