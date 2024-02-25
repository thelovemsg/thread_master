package src.forkjoin;

import java.util.concurrent.ForkJoinPool;

public class App {
    /**
     * fork() - asynchronously executes the given tasks in the poll
     *          We can call it when using RecursiveTask<> or RecursiveAction
     * join() - returns the result of the computation when it is finished
     * invoke() - executes the given task + wait + return the result upon completion
     *
     */

    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());
        ForkJoinPool pool = new ForkJoinPool();
        SimpleRecursiveAction action = new SimpleRecursiveAction(200);
        action.invoke();

    }
}
