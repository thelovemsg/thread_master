package javadatastructure.listimplment.heap;

public class Heap<E extends Comparable> implements PQInterface<E>{

    private E[] A;
    private int numItems;

    public Heap(int arraySize) {
        A = (E[]) new Comparable[arraySize];
        numItems  = 0;
    }

    public Heap(E[] B, int numElements) {
        A = B;
        numItems = numElements;
    }

    private void percolateUp(int i) {
        int parent = (i-1) / 2;
        if (parent >= 0 && A[i].compareTo(A[parent]) > 0) {
            E tmp = A[i];
            A[i] = A[parent];
            A[parent] = tmp;
            percolateUp(parent);
        }

    }

    @Override
    public void insert(E newItem) throws Exception {

    }

    @Override
    public E deleteMax() throws Exception {
        return null;
    }

    @Override
    public E max() throws Exception {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {

    }
}
