package threadlocal;

import static threadlocal.UserHandler.print;

public class ScopedUserHandler {
    public boolean handle() {
        boolean bound = ScopedValuePlay.user.isBound();
        print("handle - user is Bound => " + bound);
        if(bound) {
            User requestUser = ScopedValuePlay.user.get();
            print("handle - User => " + requestUser);
        }

        return bound;
    }
}
