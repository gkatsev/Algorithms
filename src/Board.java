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
        tiles = copyArray(blocks);
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

    private int[][] copyArray(int[][] arr) {
        int iN = arr.length;
        int jN = arr[0].length;
        int[][] newArr = new int[iN][jN];
        for (int i = 0; i < iN; i++) {
            for (int j = 0; j < jN; j++) {
                newArr[i][j] = arr[i][j];
            }
        }
        return newArr;
    }
    private int[] findZero() {
        for (int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    private void exch(int[][] arr, int zi, int zj, int oi, int oj) {
        int temp = tiles[zi][zj];
        arr[zi][zj] = arr[oi][oj];
        arr[oi][oj] = temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<Board>();
        int[][] tilesClone = copyArray(tiles);
        int[] zeroPos = findZero();
        int zi = zeroPos[0];
        int zj = zeroPos[1];
        int oi;
        int oj;

        // up
        oi = zi - 1;
        oj = zj;
        if (oi >= 0) {
            exch(tilesClone, zi, zj, oi, oj);
            neighbors.push(new Board(tilesClone));
            exch(tilesClone, oi, oj, zi, zj);
        }

        // down
        oi = zi + 1;
        oj = zj;
        if (oi < 3) {
            exch(tilesClone, zi, zj, oi, oj);
            neighbors.push(new Board(tilesClone));
            exch(tilesClone, oi, oj, zi, zj);
        }

        // left
        oi = zi;
        oj = zj - 1;
        if (oj >= 0) {
            exch(tilesClone, zi, zj, oi, oj);
            neighbors.push(new Board(tilesClone));
            exch(tilesClone, oi, oj, zi, zj);
        }

        // right
        oi = zi;
        oj = zj + 1;
        if (oj < 3) {
            exch(tilesClone, zi, zj, oi, oj);
            neighbors.push(new Board(tilesClone));
            exch(tilesClone, oi, oj, zi, zj);
        }

        return neighbors;
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


        board = new int[][]{ {1,2,3}, {4,0,6}, {7,8,5}};
        b = new Board(board);
        Iterable<Board> neighbors = b.neighbors();
        StdOut.println(b.toString());
        for (Board bbb : neighbors) {
            StdOut.println(bbb.toString());
        }
    }

    private static void printTest(Board b) {
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.isGoal());
        StdOut.println(b.toString());
        StdOut.println(b.twin().toString());
    }
}
