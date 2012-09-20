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
    MinPQ<SearchNode> minNodes;
    MinPQ<SearchNode> minTwins;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        this.twin = initial.twin();
        solve();
    }

    private void solve() {
        SearchNode node = new SearchNode(0, initial, null);
        SearchNode twin = new SearchNode(0, this.twin, null);
        minNodes = new MinPQ<SearchNode>();
        minTwins = new MinPQ<SearchNode>();
        minNodes.insert(node);
        minTwins.insert(twin);
        while (true) {
            if (iterate(false)) break;
            if (iterate(true)) break;
        }

    }

    private boolean iterate(boolean twins) {
        MinPQ<SearchNode> nodes = twins ? minTwins : minNodes;
        SearchNode node;
        node = nodes.delMin();
        if (node.board.isGoal()) {
            if (!twins) {
                solution = node;
            }
            return true;
        } else {
            Iterable<Board> neighbors = node.board.neighbors();
            for (Board b : neighbors) {
                if (node.previous != null && !b.equals(node.previous.board)) {
                   nodes.insert(new SearchNode(node.moves + 1, b, node));
                } else if (node.previous == null) {
                    nodes.insert(new SearchNode(node.moves + 1, b, node));
                }
            }
        }
        return false;
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

    private class SearchNode implements Comparable<SearchNode> {
        int moves;
        Board board;
        SearchNode previous;

        public SearchNode(int moves, Board board, SearchNode previous) {
            this.moves = moves;
            this.board = board;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.board.manhattan() - that.board.manhattan();
        }
    }
}
