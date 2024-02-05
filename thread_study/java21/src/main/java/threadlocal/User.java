package threadlocal;

public class User {
    private String id;

    public User(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s]", super.toString(), this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
