package threadlocal;

public class UserHandler {
    public void handle() {
        User requestUser = ThreadLocalSimplePlay.user.get();
        print("handle - User = > " + requestUser);

    }

    public static void print(String m) {
        ThreadLocalSimplePlay.print(m);
    }
}
