package src.collection.copyonwritearray;

import java.util.List;

public class CopyReadTask implements Runnable{

    private List<Integer> list;

    public CopyReadTask(List<Integer> list) {
        this.list = list;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(list);
        }
    }
}

