package javadatastructure.listimplment.stack;

public interface StackInterface<E>{
    void push(E newItem);
    E pop();
    E top();
    boolean isEmpty();
    void popAll();
}
