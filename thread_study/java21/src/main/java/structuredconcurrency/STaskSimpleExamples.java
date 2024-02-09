package structuredconcurrency;

import threadandscalability.LongRunningTask;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;

public class STaskSimpleExamples {
    public static void main(String[] args) throws Exception {
        System.out.println("Main : Started");
//        interruptMain();
//        exampleCompleteAllTasks();
//        exampleShutdownOnFailure();
//        exampleShutdownOnSuccess();
        exampleCustomTaskScope();
        System.out.println("Main : Completed");
    }

    private static void interruptMain() {

        Thread mainThread = Thread.currentThread();

        Thread.ofPlatform().start(() -> {
            try {
                Thread.sleep(Duration.ofSeconds(2));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThread.interrupt();
        });
    }

    private static void exampleCompleteAllTasks() throws Exception {
        try (var scope = new StructuredTaskScope<>()) {
            var expTask = new LongRunningTask("expedia-task", 3, "100$", true);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", true);

            //Start running the tasks in parallel
            Subtask<LongRunningTask.TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<LongRunningTask.TaskResponse> hotSubTask = scope.fork(hotTask);

            if(true) {
                Thread.sleep(Duration.ofSeconds(2));
                throw new RuntimeException("Some Exception");
            }

//            scope.joinUntil(Instant.now().plus(Duration.ofSeconds(2)));
            scope.join();

            State expState = expSubTask.state();
            if (expState == State.SUCCESS)
                System.out.println(expSubTask.get());
            else if(expState == State.FAILED)
                System.out.println(expSubTask.exception());

            State hotState = hotSubTask.state();
            if (hotState == State.SUCCESS)
                System.out.println(hotSubTask.get());
            else if(hotState == State.FAILED)
                System.out.println(hotSubTask.exception());
        }
    }

    private static void exampleShutdownOnFailure() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var dataTask = new LongRunningTask("expedia-task", 3, "100$", true);
            var restTask = new LongRunningTask("hotwire-task", 10, "110$", false);

            //Start running the tasks in parallel
            Subtask<LongRunningTask.TaskResponse> dataSubTask = scope.fork(dataTask);
            Subtask<LongRunningTask.TaskResponse> restSubTask = scope.fork(restTask);

            scope.join();
            scope.throwIfFailed(t -> new Exception(t));

            System.out.println(dataSubTask.get());
            System.out.println(restSubTask.get());
        }
    }

    private static void exampleShutdownOnSuccess() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<LongRunningTask.TaskResponse>()) {
            var wthr1Task = new LongRunningTask("Weather-1", 3, "32", true);
            var wthr2Task = new LongRunningTask("Weather-2", 10, "30", true);

            Subtask<LongRunningTask.TaskResponse> subTask1 = scope.fork(wthr1Task);
            Subtask<LongRunningTask.TaskResponse> subTask2 = scope.fork(wthr2Task);

            scope.join();

            LongRunningTask.TaskResponse result = scope.result(t -> new Exception(t));
            System.out.println(result);
        }
    }

    private static void exampleCustomTaskScope() throws InterruptedException {
        try (var scope = new AverageWeatherTaskScope()) {
            var w1Task = new LongRunningTask("Weather-1", 3, "30", false);
            var w2Task = new LongRunningTask("Weather-2", 4, "32", false);
            var w3Task = new LongRunningTask("Weather-3", 5, "34", false);
            var w4Task = new LongRunningTask("Weather-4", 6, "34", false);
            var w5Task = new LongRunningTask("Weather-5", 9, "30", false);

            // start running the weather tasks in parellel
            Subtask<LongRunningTask.TaskResponse> w1SubTask = scope.fork(w1Task);
            Subtask<LongRunningTask.TaskResponse> w2SubTask = scope.fork(w2Task);
            Subtask<LongRunningTask.TaskResponse> w3SubTask = scope.fork(w3Task);
            Subtask<LongRunningTask.TaskResponse> w4SubTask = scope.fork(w4Task);
            Subtask<LongRunningTask.TaskResponse> w5SubTask = scope.fork(w5Task);

            scope.join();
            LongRunningTask.TaskResponse response = scope.response();
            System.out.println(response);
        }
    }
}
