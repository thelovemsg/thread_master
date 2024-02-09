package future;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CompletableFutureBasic {
    public static void main(String[] args) throws InterruptedException, ExecutionException, UnsupportedEncodingException {
        Future<String> completableFuture = calculateAsync();

        String result = completableFuture.get();
        System.out.println("result = " + result);


        CompletableFuture<String> completableFuture1
                = CompletableFuture.supplyAsync(() -> "Hello");

        CompletableFuture<String> result2 = completableFuture1
                .thenApply(s -> s + " World");

        System.out.println("result2 :: " + result2.get());

        //value를 반환하기 싫으면 consumer function도 사용 가능
        CompletableFuture<Void> result3 = completableFuture1
                .thenAccept(s -> System.out.println("consumer test :: " + s));
        System.out.println("result3 = " + result3);

        //해당 코드같이 이전의 결과값을 가지고 작업을 추가할 수 있다.
        CompletableFuture<String> completableFuture3
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " World"));
        System.out.println("completableFuture3 = " + completableFuture3);

        //thenCombine은 두 개의 CompletableFuture를 결합하고, 두 연산의 결과에 함수를 적용한다.
        CompletableFuture<String> completableFuture4
                = CompletableFuture.supplyAsync(() -> "Hello")
                .thenCombine(CompletableFuture.supplyAsync(
                        () -> " World"), (s1, s2) -> s1 + s2);

        System.out.println("completableFuture4.get() = " + completableFuture4.get());

        //

    }

    public static Future<String> calculateAsync() throws InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(500);
            completableFuture.complete("Hello");
            return null;
        });

        return completableFuture;
    }

    private static Key generateKey(String key) throws UnsupportedEncodingException {
        byte[] keyValue = new byte[16];
        int i = 0;
        for (byte b : key.getBytes("utf-8")) {
            keyValue[i++ % 16] ^= b;
        }
        return new SecretKeySpec(keyValue, "AES");
    }
}
