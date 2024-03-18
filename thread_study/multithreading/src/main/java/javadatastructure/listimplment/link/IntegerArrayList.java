package javadatastructure.listimplment.link;

public class IntegerArrayList {
    private Integer[] item;
    private int numItems;
    private static final int DEFAULT_CAPACITY = 64;

    public IntegerArrayList() {
        item = new Integer[DEFAULT_CAPACITY];
        numItems = 0;
    }

    public IntegerArrayList(int n) {
        item = new Integer[n];
        numItems = 0;
    }

    public void add(int index, Integer x) {
        if (numItems >= item.length || index < 0 || index > numItems) {

        } else {
            for (int i=numItems-1; i>=index; i--) {
                item[+1] = item[i];
            }
            item[index] = x;
            numItems++;
        }
    }

    public void append(Integer x) {
        if (numItems >= item.length) {

        } else {
            item[numItems++] = x;
        }
    }

    public Integer remove(int index) {
        if (isEmpty() || index <0 || index > numItems -1)
            return null;
        else {
            Integer tmp = item[index];
            for (int i = index; i < numItems - 2 ; i++) {
                item[i] = item[i+1];
                numItems--;
            }

            return tmp;
        }
    }

    public boolean removeItem(Integer x) {
        int k=0;
        while (k<numItems && item[k].compareTo(x) != 0) {
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

    public Integer get(int index) {
        if (index >=0 && index <= numItems - 1){
            return item[index];
        }
        else return null;
    }

    public void set(int index, Integer x) {
        if (index >= 0 && index <= numItems -1)
            item[index] = x;
        else {

        }
    }

    private final int NOT_FOUND = -12345;
    public int indexOf(int x) {
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
        item = new Integer[item.length];
        numItems = 0;
    }

}
