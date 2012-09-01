import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: Gary Katsevman
 * Date: 8/30/12
 * Time: 11:47 PM
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;

    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        N = 0;
    }

    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException("We do not support empty items");
        }
    }
    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no items in the RandomizedQueue");
        }
    }

    public void enqueue(Item item) {
        checkNull(item);

        if (N == a.length) resize(a.length*2);
        a[N++]  = item;
    }

    public Item dequeue() {
        checkEmpty();

        int r = StdRandom.uniform(N);
        Item item = a[r];
        a[r] = a[--N];
        a[N] = null;
        if (N > 0 && N  == a.length/4) resize(a.length/2);
        return item;
    }

    public Item sample() {
        checkEmpty();

        return a[StdRandom.uniform(N)];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        int[] order;
        int current;

        public RandomizedQueueIterator() {
            order = new int[N];
            for (int i = 0; i < N; i++) {
                int r = StdRandom.uniform(i+1);
                order[i] = order[r];
                order[r] = i;
            }
            current = 0;
        }

        public boolean hasNext() {
            return current != order.length;
        }

        public Item next() {
            if (current == order.length) { throw new NoSuchElementException("We got to the end and found nothing."); }
            return a[order[current++]];
        }

        public void remove() {
            throw new UnsupportedOperationException("We do not support the remove operation");
        }
    }

    public static void main(String[] args) {

        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] freq = new int[arr1.length];


        RandomizedQueue<Integer> aQueueObj = new RandomizedQueue<Integer>();

        // Stress Test (Insert 10 element 1 by 1 and then take Number of Elements existing x 1 million samples. Every Element should appear roughly 1 Million times

        System.out.println("\n******************* Now Inserting Elements 1 by 1 ");
        System.out.println("All non-zero values should be close to 1000000. The single number will be length of the Array. It'll always be " + freq.length + " \n");
        for (int element : arr1) {
            aQueueObj.enqueue(element);
            for (int k = 0 ; k < freq.length; ++k){
                freq[k] = 0;
            }
            for (int j = 0; j < aQueueObj.size() * 1000000; ++j) {
                freq[aQueueObj.sample()-1]++;
            }
            StdArrayIO.print(freq);

        }

        System.out.print("Checking for size = 10 : " );
        if (!aQueueObj.isEmpty() && aQueueObj.size() == 10)
            System.out.println("Assertion Passed");
        else
            System.out.println("Assertion Failed");

        // Stress Test (Delete 10 elements 1 by 1 and then take Number of Elements remaining x 1 million samples. Every Remaining Element should appear roughly 1 Million times

        System.out.println("\n******************* Now Removing Elements 1 by 1 ");
        System.out.println("All non-zero values should be close to 1000000  The single  number will length of Array. It'll always be " + freq.length + " \n");
        for (int element : arr1) {
            aQueueObj.dequeue();
            for (int k = 0 ; k < freq.length; ++k){
                freq[k] = 0;
            }
            for (int j = 0; j < aQueueObj.size() * 1000000; ++j) {
                freq[aQueueObj.sample()-1]++;
            }
            StdArrayIO.print(freq);
        }

        System.out.println("\n******************* Now Checking for various assertions");

        System.out.print("Checking for empty List : ");
        if (aQueueObj.isEmpty() && aQueueObj.size() == 0)
            System.out.println("Assertion Passed");
        else
            System.out.println("Assertion Failed");

        try {
            System.out.print("Checking for dequeue on an empty list : ");
            aQueueObj.dequeue();
            System.out.println("Assertion Failed. Dequeue on Empty list allowed!");
        } catch (java.util.NoSuchElementException ex) {
            System.out.println("Assertion Passed");
        } catch (Exception ex) {
            System.out.println("Assertion Failed, Incorrect Exception Object Support Found");
        }

        try {
            System.out.print("Checking for Enqueing a null Item : ");
            aQueueObj.enqueue(null);
            System.out.println("Assertion Failed. Enqueing an Null Element Succeded!");
        } catch (java.lang.NullPointerException ex) {
            System.out.println("Assertion Passed");
        } catch (Exception ex) {
            System.out.println("Assertion Failed. Incorrect Exception Object Support Found.");
        }

    }
}
