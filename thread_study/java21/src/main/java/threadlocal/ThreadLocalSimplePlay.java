package threadlocal;

public class ThreadLocalSimplePlay {
    public static final ThreadLocal<User> user = new ThreadLocal<User>();

    public static void main(String[] args) {
        print(STR."User -> \{user.get()}");

        user.set(new User("anonymous"));
        print(STR."User => \{user.get()}");

        handlerUser();
    }

    private static void handlerUser() {
        UserHandler handler = new UserHandler();
        handler.handle();
    }

    public static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }

}

