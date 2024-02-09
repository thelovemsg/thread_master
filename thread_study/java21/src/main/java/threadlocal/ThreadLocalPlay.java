package threadlocal;

import static threadlocal.ThreadLocalSimplePlay.print;

public class ThreadLocalPlay {

    public static final ThreadLocal<User> user = new ThreadLocal<User>();

    public static void main(String[] args) throws InterruptedException {
        print(STR."User => \{user.get()}");
        user.set(new User("Main"));
        print(STR."Modified user => \{user.get()}");
        Thread thread = Thread.ofVirtual().start(() -> {
            Thread.currentThread().setName("bob-thread");
            print(STR."User => \{user.get()}");

            user.set(new User("bob"));
            print(STR."Modified user => \{user.get()}");
        });
        thread.join();
        print(STR."User => \{user.get()}");
    }
}
