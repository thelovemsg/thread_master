package structuredconcurrency;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class StructuredCodingExample {
    public static void main(String[] args) {
        List<Integer> result = getNumbersDivisibleBy5(51);
    }

    private static List<Integer> getNumbersDivisibleBy5(int num) {

        //블록은 우리가 프로램을 동기적 프로그램으로 잘 이해하도록 돕는다.
        if(num < 1){
            throw new RuntimeException("Invalid Input");
        }

        var result = new ArrayList<Integer>();
        for (int j = 0; j <= num; j++) {

            if(j%5==0)
                result.add(j);
        }
        return result;
    }

    /**
     * The method ends but the submitted task may still be running
     * The worker Thread has leaked
     * @throws Exception
     */
    private void handleBusinessLogic() throws Exception {
        ExecutorService pool = ForkJoinPool.commonPool();
        Future<String> future = pool.submit(() -> {
            System.out.println(">> Starting worker thread .. ");

            doPartOfBusinessLogic();
            return "done";
        });
    }

    private void doPartOfBusinessLogic() {
        System.out.println("do part of business logic .. ");
    }
}
