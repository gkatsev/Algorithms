/**
 * User: Gary KAtsevman
 * Date: 8/21/12
 * Time: 10:31 PM
 */

public class Percolation {

    private int N;
    private int size;
    private int virtualTop;
    private WeightedQuickUnionUF grid;
    private byte[] openGrid;
    private boolean percolates;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
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
    public void open(int i, int j) {
        checkBounds(i, j);

        int current = xyToId(i, j);

        if (isOpen(current)) return;

        openGrid[current] = setOpen(openGrid[current]);

        int[] neighbors = getNeighbors(j, current);

        for (int k = 0; k < neighbors.length; k++) {
            int neighbor = neighbors[k];
            if (neighbor != -1) openNeighbor(current, neighbor);
        }
        int root = grid.find(current);
        if (i == N) {
            openGrid[current] = setBottom(openGrid[current]);
        }
        if (isConnectedToBottom(current)) {
            openGrid[root] = setBottom(openGrid[root]);
        }

        if (isConnectedToBottom(current) && isFull(i, j)) percolates = true;
    }

    private void openNeighbor(int current, int neighbor) {
        if (isOpen(neighbor)) {
            int root = grid.find(neighbor);

            grid.union(current, root);

            if (isConnectedToBottom(root)) {
                openGrid[current] = setBottom(openGrid[current]);
            }
        }
    }

    private int[] getNeighbors(int j, int current) {
        int[] neighbors = new int[4];

        neighbors[0] = Math.max(0, current - N);
        neighbors[1] = Math.min(size - 1, current + N);
        if (j > 1) {
            neighbors[2] = Math.max(0, current - 1);
        } else {
            neighbors[2] = -1;
        }
        if (j < N) {
            neighbors[3] = Math.min(size, current + 1);
        } else {
            neighbors[3] = -1;
        }
        return neighbors;
    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return isOpen(xyToId(i, j));
    }

    private boolean isOpen(int current) {
        return 1 == getOpen(openGrid[current]);
    }

    private boolean isConnectedToBottom(int current) {
        return getBottom(openGrid[current]) == 2;
    }

    public boolean isFull(int i, int j) {
        checkBounds(i, j);
        int current = xyToId(i, j);
        return isOpen(current) && grid.connected(virtualTop, current);
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private int xyToId(int i, int j) {
        return (i - 1) * N + j;
    }

    private void checkBounds(int i, int j) {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    private byte getBottom(byte b) {
        return getBit(b, 1);
    }

    private byte setBottom(byte b) {
        return setBit(b, 1);
    }

    private byte getOpen(byte b) {
        return getBit(b, 0);
    }

    private byte setOpen(byte b) {
        return setBit(b, 0);
    }

    private byte getBit(byte b, int n) {
        return (byte) (b & (1 << n));
    }

    private byte setBit(byte b, int n) {
        return (byte) (b | (1 << n));
    }
}
