import java.util.Iterator;

/**
 * User: Gary Katsevman
 * Date: 8/30/12
 * Time: 11:47 PM
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    public RandomizedQueue() {

    }

    public boolean isEmpty() {
        return false;
    }

    public int size() {
        return 0;
    }

    public void enqueue(Item item) {

    }

    public Item dequeue() {
        return null;
    }

    public Item sample() {
        return null;
    }

    public Iterator<Item> iterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        public RandomizedQueueIterator() {

        }

        public boolean hasNext() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public Item next() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        public void remove() {
            throw new UnsupportedOperationException("We do not support the remove operation");
        }
    }
}
