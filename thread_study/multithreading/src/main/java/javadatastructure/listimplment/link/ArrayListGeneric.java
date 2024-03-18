package javadatastructure.listimplment.link;

public class ArrayListGeneric<E> implements ListInterface<E> {
    private E[] item;
    private int numItems;
    private static final int DEFAULT_CAPACITY = 64;

    public ArrayListGeneric () {
        item = (E[]) new Object[DEFAULT_CAPACITY];
        numItems = 0;
    }

    public ArrayListGeneric(int n) {
        item = (E[]) new Object[n];
        numItems = 0;
    }

    public void add(int index, E x) {
        if (numItems >= item.length || index < 0 || index > numItems) {

        } else {
            for (int i=numItems-1; i>=index; i--) {
                item[+1] = item[i];
            }
            item[index] = x;
            numItems++;
        }
    }

    public void append(E x) {
        if (numItems >= item.length) {

        } else {
            item[numItems++] = x;
        }
    }

    public E remove(int index) {
        if (isEmpty() || index <0 || index > numItems -1)
            return null;
        else {
            E tmp = item[index];
            for (int i = index; i < numItems - 2 ; i++) {
                item[i] = item[i+1];
                numItems--;
            }

            return tmp;
        }
    }

    public boolean removeItem(E x) {
        int k=0;
        while (k<numItems && ((Comparable)item[k]).compareTo(x) != 0) {
            k++;
        }
        if(k == numItems)
            return false;
        else {
            for (int i = k; i <=numItems-2 ; i++) {
                item[i] = item[i+1];
            }
            numItems--;
            return true;
        }
    }

    public E get(int index) {
        if (index >=0 && index <= numItems - 1){
            return item[index];
        }
        else return null;
    }

    public void set(int index, E x) {
        if (index >= 0 && index <= numItems -1)
            item[index] = x;
        else {

        }
    }

    private final int NOT_FOUND = -12345;
    public int indexOf(E x) {
        for (int i = 0; i < numItems; i++) {
            if(((Comparable)item[i]).compareTo(x) == 0){
                return i;
            }
        }

        return NOT_FOUND;
    }

    public int len() {
        return numItems;
    }

    public boolean isEmpty() {
        return numItems == 0;
    }

    public void clear() {
        item = (E[]) new Object[item.length];
        numItems = 0;
    }

}
