import java.util.Arrays;
import java.util.Stack;

/**
 * User: Gary Katsevman
 * Date: 9/15/12
 * Time: 11:41 PM
 */
public class Board {
    private int N;
    private int[][] tiles;
    private Board twin;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        tiles = blocks;
        N = blocks.length;
    }

    // board dimension N
    public int dimension() {
        return N;
    }

    // number of blocks out of place
    public int hamming() {
        int val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];
                int pos = N * i + j + 1;
                if (tile != 0 && tile != pos) {
                    val++;
                }
            }
        }
        return val;
    }

    private int mod(int x, int n) {
        return ((x % n) + n) % n;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int val = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];
                int pos = N * i + j + 1;
                if (tile != 0 && tile != pos) {
                    int xi = mod(tile, N) - 1;
                    int xj = mod(xi, N);
                    val += Math.abs(xi - i);
                    val += Math.abs(xj - j);
                }
            }
        }
        return val;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        if (twin == null) {
            int[][] twinTiles = tiles.clone();
            int i = 0;
            int j1 = 0;
            int j2 = 1;
            boolean switched = false;
            while(!switched) {
                if(twinTiles[i][j1] == 0 || twinTiles[i][j2] == 0) {
                    j1++;
                    j2++;
                    if (j2 == 3) {
                        i++;
                        j1 = 0;
                        j2 = 1;
                    }
                } else {
                    switched = true;
                    int temp = twinTiles[i][j1];
                    twinTiles[i][j1] = twinTiles[i][j2];
                    twinTiles[i][j2] = temp;
                }
            }
            twin = new Board(twinTiles);
        }
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object x) {
        if (x == this) return true;
        if (x == null) return false;
        if (x.getClass() != this.getClass()) return false;
        Board that = (Board) x;
        return Arrays.deepEquals(tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return new Stack<Board>();
    }

    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }


    public static void main(String[] args) {
        int[][] board = new int[][]{ {1,2,3}, {4,5,6}, {7,8,0}};
        Board b = new Board(board);
        printTest(b);

        board = new int[][]{ {8,1,3}, {4,0,2}, {7,6,5}};
        b = new Board(board);
        printTest(b);

        board = new int[][]{ {0,1,3}, {4,8,2}, {7,6,5}};
        // hamming 5
        // manhattan 8
        b = new Board(board);
        printTest(b);

        board = new int[][]{ {8,0,3}, {4,1,2}, {7,6,5}};
        // hamming 5
        // manhattan 11
        b = new Board(board);
        printTest(b);

        StdOut.println(b.equals(b));
        Board bb = new Board(board);
        StdOut.println(b.equals(bb));
        board = new int[][]{ {8,1,3}, {4,0,2}, {7,6,5}};
        bb = new Board(board);
        StdOut.println(!b.equals(bb));
    }

    private static void printTest(Board b) {
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.isGoal());
        StdOut.println(b.toString());
        StdOut.println(b.twin().toString());
    }
}
