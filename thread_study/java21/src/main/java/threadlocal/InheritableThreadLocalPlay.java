package thread_study.java21.src.main.java.threadlocal;

import static java.lang.StringTemplate.STR;

public class InheritableThreadLocalPlay {
    public static final InheritableThreadLocal<threadlocal.User> user = new InheritableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        print(STR."User => \{user.get()}");
        Thread thread = Thread.ofVirtual().start(() -> {
            Thread.currentThread().setName("bob-thread");
            print(STR."User => \{user.get()}");

            user.get().setId("bobby");
            print(STR."Modified User => \{user.get()}");
        });

        thread.join();
        print(STR."User => \{user.get()}");
    }

    public static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }
}
