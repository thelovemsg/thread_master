package src.collection.copyonwritearray;

import java.util.List;
import java.util.Random;

public class CopyWriteTask implements Runnable{

    private List<Integer> list;
    private Random random;

    public CopyWriteTask(List<Integer> list) {
        this.list = list;
        this.random = new Random();
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list.set(random.nextInt(list.size()), random.nextInt(10));
        }
    }
}
