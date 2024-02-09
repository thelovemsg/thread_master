package future;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {
    public static void main(String[] args) {
        /**
         * ForkJoinPool 생성법
         * 1. commonPool()
         * 2. new ForkJoinPool();
         * 3. PoolUtil.forkJoinPool (org.apache.commons.pool2 사용);
         */
        ForkJoinPool forkJoinPoolAction = ForkJoinPool.commonPool();
        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("abcde qwerkkfgj dsoqomvm qolwlkjdlaskdlaklsd asdfkqwer");
        forkJoinPoolAction.invoke(customRecursiveAction);

        ForkJoinPool forkJoinPoolTask = ForkJoinPool.commonPool();
        int[] arr = {1,0,5,48,4,8,158,18,165,1,58,1,52,5,84,23,1,567,84,21,5,498,6};
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask(arr);
        Integer invoke = forkJoinPoolTask.invoke(customRecursiveTask);
        System.out.println("invoke = " + invoke);
    }
}
