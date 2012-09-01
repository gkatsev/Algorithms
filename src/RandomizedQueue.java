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
}
