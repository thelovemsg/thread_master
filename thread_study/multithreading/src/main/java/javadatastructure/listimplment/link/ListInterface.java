package javadatastructure.listimplment.link;

public interface ListInterface<E> {
    public void add(int i, E x);
    public void append(E x);
    E remove(int i);
    boolean removeItem(E x);
    E get(int i);
    void set(int i, E x);
    int indexOf(E x);
    int len();
    boolean isEmpty();
    void clear();
}
