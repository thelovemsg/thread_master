package play;

import java.time.Duration;
import java.util.concurrent.Callable;

public class UserRequestHandler implements Callable<String> {
    @Override
    public String call() throws Exception {

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
}
