/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/21/12
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class Percolation {

    private int N;
    private int size;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF grid;
    private boolean[] openGrid;

    // create N-by-N grid, with all sites blocked
    public Percolation (int N) {
        this.size = N * N + 2 * N + 2;
        this.N = N;
        virtualTop = 0;
        virtualBottom = N * N + 2 * N + 1;

        grid = new WeightedQuickUnionUF(size);
        openGrid = new boolean[size];

        for (int i = 0; i < size; i++) {
            openGrid[i] = false;
        }

        for (int i = 1; i <= N; i++) {
            grid.union(i, virtualTop);
            openGrid[i] = true;
            grid.union(size - N + i - 2, virtualBottom);
            openGrid[N * N + N + i] = true;
        }

        openGrid[virtualTop] = true;
        openGrid[virtualBottom] = true;
    }

    // open site (row i, column j) if it is not already
    public void open (int i, int j) {
        checkBounds(i, j);

        int current = xyToId(i, j);

        openGrid[current] = true;

        if (j == N && isFull(i, j)) {
            openBottom(i, j, current);
        } else {
            openBottom(i, j, current);
        }
        openTop(i, j, current);
        if (j > 1) openRight(i, j, current);
        if (j < N) openLeft(i, j, current);
    }

    private void openTop (int i, int j, int current) {
        int top = Math.max(0, current - N);
        if (openGrid[top]) grid.union(current, top);
    }

    private void openBottom (int i, int j, int current) {
        int bottom = Math.min(size - 1, current + N);
        if (openGrid[bottom]) grid.union(current, bottom);
    }

    private void openRight (int i, int j, int current) {
        int right = Math.max(0, current - 1);
        if (openGrid[right]) grid.union(current, right);
    }

    private void openLeft (int i, int j, int current) {
        int left = Math.min(size, current + 1);
        if (openGrid[left]) grid.union(current, left);
    }

    // is site (row i, column j) open?
    public boolean isOpen (int i, int j) {
        checkBounds(i, j);
        return openGrid[xyToId(i, j)];
    }

    private boolean isConnected (int a, int b, int x, int y) {
        checkBounds(a, b);
        checkBounds(x, y);
        return grid.connected(xyToId(a, b), xyToId(x, y));
    }

    public boolean isFull (int i, int j) {
        checkBounds(i, j);
        return grid.connected(virtualTop, xyToId(i, j));
    }

    // does the system percolate?
    public boolean percolates () {
        return grid.connected(virtualTop, virtualBottom);
    }

    private int xyToId (int i, int j) {
        int rVal = i * N + j;
        return rVal;
    }

    private void checkBounds (int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }

    private void printGrid () {
        StdOut.println();

        StdOut.println(grid.find(0));
        for (int i = 1; i < openGrid.length - 1; i++) {
            StdOut.print(grid.find(i) + "\t");

            if (i % N == 0) StdOut.println();
        }
        StdOut.println(grid.find(openGrid.length - 1));
    }

    private void printOpenGrid () {
        StdOut.println();

        boolean b = openGrid[0];
        if (b) {
            StdOut.println("o ");
        } else {
            StdOut.println("x ");
        }
        for (int i = 1; i < openGrid.length - 1; i++) {
            b = openGrid[i];
            if (b) {
                StdOut.print("o ");
            } else {
                StdOut.print("x ");

            }
            if (i % N == 0) StdOut.println();
        }
        b = openGrid[openGrid.length - 1];
        if (b) {
            StdOut.println("o ");
        } else {
            StdOut.println("x ");
        }
    }

    public static void main (String[] args) {
        Percolation p = new Percolation(5);
        p.printGrid();
        p.open(1, 1);
        StdOut.println(p.isOpen(1, 1));
        p.printGrid();

        p.open(1, 2);
        StdOut.println(p.isOpen(1, 2));
        p.printGrid();

        p.open(1, 3);
        StdOut.println(p.isOpen(1, 3));
        p.printGrid();

        p.open(1, 4);
        StdOut.println(p.isOpen(1, 4));
        p.printGrid();

        p.open(1, 5);
        StdOut.println(p.isOpen(1, 5));
        p.printGrid();

        StdOut.println(p.isFull(1, 5));
        StdOut.println(p.percolates());

        p.printOpenGrid();
        p.printGrid();

        StdOut.println("\n\nTest 2");

        p = new Percolation(5);
        p.printGrid();
        p.open(1, 1);
        StdOut.println(p.isOpen(1, 1));
        p.printGrid();

        p.open(2, 1);
        StdOut.println(p.isOpen(2, 1));
        p.printGrid();

        p.open(3, 1);
        StdOut.println(p.isOpen(3, 1));
        p.printGrid();

        p.open(4, 1);
        StdOut.println(p.isOpen(4, 1));
        p.printGrid();

        p.open(5, 1);
        StdOut.println(p.isOpen(5, 1));
        p.printGrid();

        StdOut.println(p.isFull(5, 1));
        StdOut.println(p.percolates());
    }
}
