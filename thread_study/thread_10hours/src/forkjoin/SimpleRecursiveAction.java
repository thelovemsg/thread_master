package src.forkjoin;

import java.util.concurrent.RecursiveAction;

public class SimpleRecursiveAction extends RecursiveAction {

    private int simulatedWork;

    public SimpleRecursiveAction(int simulatedWork) {
        this.simulatedWork = simulatedWork;
    }

    @Override
    protected void compute() {
        // if the task is too large then we split it an execute the tasks in parallel
        if(simulatedWork > 100) {
            System.out.println("Parallel execution and split the tasks...");
            SimpleRecursiveAction action1 = new SimpleRecursiveAction(simulatedWork/2);
            SimpleRecursiveAction action2 = new SimpleRecursiveAction(simulatedWork/2);

            invokeAll(action1, action2);
        } else {
            System.out.println("The task is rather small so sequential execution is fine ...");
            System.out.println(STR."The size of the task: \{simulatedWork}");

        }
    }
}
