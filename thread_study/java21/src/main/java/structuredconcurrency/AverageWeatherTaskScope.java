package structuredconcurrency;

import threadandscalability.LongRunningTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.StructuredTaskScope;

public class AverageWeatherTaskScope extends StructuredTaskScope<LongRunningTask.TaskResponse> {
    private final List<Subtask<? extends LongRunningTask.TaskResponse>> successSubTasks = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void handleComplete(Subtask<? extends LongRunningTask.TaskResponse> subtask) {
        if(subtask.state() == Subtask.State.SUCCESS)
            add(subtask);
    }

    private void add(Subtask<? extends LongRunningTask.TaskResponse> subtask) {
        int numSuccessful = 0;
        synchronized (successSubTasks) {
            successSubTasks.add(subtask);
            numSuccessful = successSubTasks.size();
        }

        if(numSuccessful == 2)
            this.shutdown();
    }

    public AverageWeatherTaskScope join() throws InterruptedException {
        super.join();
        return this;
    }

    public LongRunningTask.TaskResponse response() {
        super.ensureOwnerAndJoined();
        if (successSubTasks.size() != 2)
            throw new RuntimeException("at least two substasks must be successful");

        LongRunningTask.TaskResponse r1 = successSubTasks.get(0).get();
        LongRunningTask.TaskResponse r2 = successSubTasks.get(1).get();
        Integer temp1 = Integer.valueOf(r1.response());
        Integer temp2 = Integer.valueOf(r2.response());
        return new LongRunningTask.TaskResponse("Weather", "" + (temp1 + temp2) / 2, (r1.timeTaken() + r2.timeTaken()) / 2);
    }
}
