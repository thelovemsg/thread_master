package src.collection.copyonwritearray;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConcurrentArray {
    private List<Integer> list;

    public ConcurrentArray() {
        this.list = new CopyOnWriteArrayList<>();
        this.list.addAll(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
    }

    public void simulate() {
        Thread t1 = new Thread(new CopyWriteTask(list));
        Thread t2 = new Thread(new CopyWriteTask(list));
        Thread t3 = new Thread(new CopyWriteTask(list));
        Thread t4 = new Thread(new CopyReadTask(list));

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
