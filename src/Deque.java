import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * User: Gary Katsevman
 * Date: 8/30/12
 * Time: 9:37 PM
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    public Deque() {
        first = new Node();
        last = new Node();
        first.next = last;
        last.prev = first;
        N = 0;
    }

    public boolean isEmpty() { return N == 0; }
    public int size() { return N; }

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException("We do not support empty items");
        }
    }
    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("There are no items in the Deque");
        }
    }
    public void addFirst(Item item) {
        checkNull(item);

        Node trueFirst = first.next;
        Node node = new Node(item, trueFirst, first);
        trueFirst.prev = node;
        first.next = node;
        N++;
    }

    public void addLast(Item item) {
        checkNull(item);

        Node trueLast = last.prev;
        Node node = new Node(item, last, trueLast);
        trueLast.next = node;
        last.prev = node;
        N++;
    }

    public Item removeFirst() {
        checkEmpty();

        Node trueFirst = first.next;
        Item item = (Item) trueFirst.item;
        first.next = trueFirst.next;
        trueFirst.next.prev = first;
        N--;
        return item;
    }

    public Item removeLast() {
        checkEmpty();

        Node trueLast = last.prev;
        Item item = (Item) trueLast.item;
        last.prev = trueLast.prev;
        trueLast.prev.next = last;
        N--;
        return item;
    }

    public Iterator<Item> iterator() {
       return new DequeIterator<Item>();
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        Node node;

        public DequeIterator() {
            node = first.next;
        }

        public boolean hasNext() {
            return !node.isSentinal();
        }

        public Item next() {
            if (node.isSentinal()) { throw new NoSuchElementException("We got to the end and found nothing."); }
            Item item = (Item) node.item;
            node = node.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("We do not support the remove operation");
        }
    }

    private class Node<Item> {
        public Item item;
        public Node next;
        public Node prev;

        public Node() {
            item = null;
            next = prev = null;
        }
        public Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public boolean isSentinal() { return item == null; }
    }

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        try {
            d.removeFirst();
        } catch (NoSuchElementException e) {
            StdOut.println("remove empty");
        }
        int[] a = new int[] {1,2,3,4};
        d.addFirst(a[0]);
        d.addFirst(a[1]);
        d.addLast(a[2]);
        d.addLast(a[3]);
        Integer i = d.removeFirst();
        if (i == a[1]) {
            StdOut.println(i);
        } else {
            StdOut.println(i + " " + a[1]);
        }
        i = d.removeFirst();
        if (i == a[0]) {
            StdOut.println(i);
        } else {
            StdOut.println(i + " " + a[0]);
        }
        i = d.removeLast();
        if (i == a[3]) {
            StdOut.println(i);
        } else {
            StdOut.println(i + " " + a[3]);
        }
        i = d.removeLast();
        if (i == a[2]) {
            StdOut.println(i);
        } else {
            StdOut.println(i + " " + a[2]);
        }

        for (int j = 0; j < a.length; j++) {
            d.addLast(a[j]);
            StdOut.print(a[j] + " ");
        }
        StdOut.println();
        Iterator it = d.iterator();
        int j = 0;
        while(it.hasNext()) {
            i = (Integer) it.next();
            if (i == a[j]) {
                StdOut.println(i);
            } else {
                StdOut.println(i + " " + a[j]);
            }
            j++;
        }
    }
}
