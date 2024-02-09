package future;

import java.util.concurrent.*;

public class FutureBasic {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        SquareCalculator squareCalculator = new SquareCalculator();
        Future<Integer> calculate = squareCalculator.calculate(100);

        System.out.println(calculate.get());

        Future<Integer> future = new SquareCalculator().calculate(10);

        while(!future.isDone()) {
            System.out.println("Calculating...");
            Thread.sleep(300);
        }

        // get => Waits if necessary for the computation to complete, and then retrieves its result.
        // join vs get? join은 반환값 없이 작업이 끝낼 때 까지 기다린다.
        Integer result = future.get();
        System.out.println("result :: " + result);


        // 500 ms안에 응답이 없으면 TimeoutException을 던짐.
        Integer result1 = future.get(500, TimeUnit.MILLISECONDS);
        System.out.println("result1 :: " + result1);

        Future<Integer> cancelFuture = new SquareCalculator().calculate(4);
        boolean isCanceled = cancelFuture.cancel(true);
        //cancelFuture.get(); 취소 후 get을 통해 결과값을 가져오려 하면 CancellationException을 던짐.

    }
}