package threadlocal;

import static threadlocal.ThreadLocalSimplePlay.print;

public class ScopedValuePlay {
    public static final ScopedValue<User > user = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        print("user is Bound => " + user.isBound());

        User bob = new User("bob");
        Boolean result = ScopedValue.callWhere(user, bob, ScopedValuePlay::handleUser);

        print("Result => " + result);
        print("user is bound => " + user.isBound());
    }

    private static boolean handleUser() {
        ScopedUserHandler handler = new ScopedUserHandler();
        return handler.handle();
    }

    public static void print(String m){
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }
}
