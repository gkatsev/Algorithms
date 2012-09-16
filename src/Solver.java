import java.util.Stack;

/**
 * User: Gary Katsevman
 * Date: 9/15/12
 * Time: 11:41 PM
 */
public class Solver {
    private Board initial;
    private Board twin;
    private SearchNode solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        this.twin = initial.twin();
    }

    private void solve() {

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        return solution == null ? -1 : solution.moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        SearchNode node = solution;
        Stack<Board> solution = new Stack<Board>();
        while (node.previous != null) {
            solution.add(0, node.board);
            node = node.previous;
        }
        return solution;
    }

    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    private class SearchNode {
        int moves;
        Board board;
        SearchNode previous;

        public SearchNode(int moves, Board board, SearchNode previous) {
            this.moves = moves;
            this.board = board;
            this.previous = previous;
        }
    }
}
