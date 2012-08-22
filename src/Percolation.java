/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/21/12
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */

public class Percolation
{

    private int N;
    private int size;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF grid;
    private boolean[] openGrid;

    // create N-by-N grid, with all sites blocked
    public Percolation (int N)
    {
        this.size = (N * N);
        this.N = N;
        virtualTop = 0;
        virtualBottom = N * N + 1;

        grid = new WeightedQuickUnionUF(size + 2);
        openGrid = new boolean[size + 2];

        for (int i = 1; i <= N; i++)
        {
            grid.union(virtualTop, xyToId(1, i));
            grid.union(xyToId(N, i), virtualBottom);
        }

        for (int i = 0; i < size + 2; i++)
        {
            openGrid[i] = false;
        }

        openGrid[virtualTop] = true;
        openGrid[virtualBottom] = true;
    }

    // open site (row i, column j) if it is not already
    public void open (int i, int j)
    {
        checkBounds(i, j);

        int current = xyToId(i, j);

        openGrid[current] = true;

        if (j < N) openBottom(i, j, current);
        if (j > 1) openTop(i, j, current);
        if (i < N) openRight(i, j, current);
        if (i > 1) openLeft(i, j, current);
    }

    private void openTop (int i, int j, int current)
    {
        if (this.isOpen(i, j - 1)) grid.union(current, xyToId(i, j - 1));
    }

    private void openBottom (int i, int j, int current)
    {
        if (this.isOpen(i, j + 1)) grid.union(current, xyToId(i, j + 1));
    }

    private void openRight (int i, int j, int current)
    {
        if (this.isOpen(i + 1, j)) grid.union(current, xyToId(i + 1, j));
    }

    private void openLeft (int i, int j, int current)
    {
        if (this.isOpen(i - 1, j)) grid.union(current, xyToId(i - 1, j));
    }

    // is site (row i, column j) open?
    public boolean isOpen (int i, int j)
    {
        checkBounds(i, j);
        return openGrid[xyToId(i, j)];
    }

    public boolean isFull (int i, int j)
    {
        checkBounds(i, j);
        return grid.connected(0, xyToId(i, j));
    }

    // does the system percolate?
    public boolean percolates ()
    {
        return grid.connected(virtualTop, virtualBottom);
    }

    private int xyToId (int i, int j)
    {
        int rVal = (j-1) * N + i;
        if (rVal > size) rVal = size;
        StdOut.println(rVal);
        return rVal;
    }

    private void checkBounds (int i, int j)
    {
        if (i <= 0 || i > N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j <= 0 || j > N) throw new IndexOutOfBoundsException("row index i out of bounds");
    }

    public static void main (String[] args)
    {
        Percolation p = new Percolation(5);
        p.open(1, 1);
        StdOut.println(p.isOpen(1, 1));
        p.open(1, 2);
        StdOut.println(p.isOpen(1, 2));
        p.open(1, 3);
        StdOut.println(p.isOpen(1, 3));

        p.open(1, 4);
        StdOut.println(p.isOpen(1, 4));

        p.open(1, 5);
        StdOut.println(p.isOpen(1, 5));

        StdOut.println(p.grid.connected(p.xyToId(1, 1), p.xyToId(1, 2)));
        StdOut.println(p.grid.connected(p.xyToId(1, 2), p.xyToId(1, 3)));
        StdOut.println(p.grid.connected(p.xyToId(1, 3), p.xyToId(1, 4)));
        StdOut.println(p.grid.connected(p.xyToId(1, 4), p.xyToId(1, 5)));
        StdOut.println(p.isFull(1, 5));
        StdOut.println(p.percolates());
    }
}
