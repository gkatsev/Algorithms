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
    private WeightedQuickUnionUF grid;
    private byte[] openGrid;
    private boolean percolates;

    // create N-by-N grid, with all sites blocked
    public Percolation (int N) {
        this.size = N * N + 1;
        this.N = N;
        virtualTop = 0;

        grid = new WeightedQuickUnionUF(size);
        openGrid = new byte[size];

        for (int i = 0; i < size; i++) {
            openGrid[i] = 0;
        }

        openGrid[virtualTop] = setOpen(openGrid[virtualTop]);

        percolates = false;
    }

    // open site (row i, column j) if it is not already
    public void open (int i, int j) {
        checkBounds(i, j);

        int current = xyToId(i, j);

        openGrid[current] = setOpen(openGrid[current]);

        int[] neighbors = getNeighbors(j, current);

        for (int k = 0; k < neighbors.length; k++) {
            int neighbor = neighbors[k];
            if (neighbor != -1) openNeighbor(current, neighbor);
        }
        if (i == N) {
            openGrid[current] = setBottom(openGrid[current]);
            if (isFull(i, j)) percolates = true;
        }
        if (isConnectedToBottom(current)) {
            int root = grid.find(current);
            openGrid[root] = setBottom(openGrid[root]);
        }
    }

    private void openNeighbor (int current, int neighbor) {
        if (isOpen(neighbor)) {
            int root = grid.find(neighbor);

            grid.union(current, root);

            if (isConnectedToBottom(root)) {
                openGrid[neighbor] = setBottom(openGrid[neighbor]);
            }
        }
    }

    private int[] getNeighbors (int j, int current) {
        int[] neighbors = new int[4];

        neighbors[0] = Math.max(0, current - N);
        neighbors[1] = Math.min(size - 1, current + N);
        neighbors[2] = (j > 1) ? Math.max(0, current - 1) : -1;
        neighbors[3] = (j < N) ? Math.min(size, current + 1) : -1;
        return neighbors;
    }

    // is site (row i, column j) open?
    public boolean isOpen (int i, int j) {
        checkBounds(i, j);
        return isOpen(xyToId(i, j));
    }

    private boolean isOpen (int current) {
        return 1 == getOpen(openGrid[current]);
    }

    private boolean isConnectedToBottom (int current) {
        return getBottom(openGrid[current]) == 1;
    }

    public boolean isFull (int i, int j) {
        checkBounds(i, j);
        return grid.connected(virtualTop, xyToId(i, j));
    }

    // does the system percolate?
    public boolean percolates () {
        return percolates;
    }

    private int xyToId (int i, int j) {
        int rVal = (i - 1) * N + j;
        return rVal;
    }

    private void checkBounds (int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }

    private byte getBottom (byte b) {
        return getBit(b, 1);
    }

    private byte setBottom (byte b) {
        return setBit(b, 1);
    }

    private byte getOpen (byte b) {
        return getBit(b, 0);
    }

    private byte setOpen (byte b) {
        return setBit(b, 0);
    }

    private byte getBit (byte b, int n) {
        return (byte) (b & (1 << n));
    }

    private byte setBit (byte b, int n) {
        return (byte) (b | (1 << n));
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

        boolean b = isOpen(0);
        if (b) {
            StdOut.println("o ");
        } else {
            StdOut.println("x ");
        }
        for (int i = 1; i < openGrid.length - 1; i++) {
            b = isOpen(i);
            if (b) {
                StdOut.print("o ");
            } else {
                StdOut.print("x ");

            }
            if (i % N == 0) StdOut.println();
        }
        b = isOpen(openGrid.length - 1);
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
