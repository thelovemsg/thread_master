package javadatastructure.listimplment.heap;

public interface PQInterface<E>{
    void insert(E newItem) throws Exception;
    E deleteMax() throws Exception;
    E max() throws Exception;
    boolean isEmpty();
    void clear();
}
