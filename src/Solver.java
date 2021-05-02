import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Solver {

    private SearchNode finalNode;
    private List<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        MinPQ<SearchNode> minPQ = new MinPQ<>(Comparator.comparingInt(SearchNode::getPriority));
        SearchNode initialNode = SearchNode.of(initial);
        minPQ.insert(initialNode);
        solve(minPQ);
    }

    private void solve(MinPQ<SearchNode> minPQ) {
        SearchNode currentNode = minPQ.delMin();
        boolean solvable = true;
        while (!currentNode.isSolved() && solvable) {
            currentNode.getNeighbours()
                    .forEach(minPQ::insert);
            Board currentBoard = currentNode.board;
            if (currentBoard.hamming() == 2
                    && currentBoard.manhattan() == 2) {
                solvable = !currentBoard.twin().isGoal();
            }
            currentNode = minPQ.delMin();
        }
        if (currentNode.isSolved()) {
            finalNode = currentNode;
            solution = currentNode.getSolution();
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solution != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? finalNode.getMoves() : -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }


    private static class SearchNode {

        private Board board;
        private SearchNode previousNode;
        private int moves;
        private int score;
        private int priority;


        private static SearchNode of(Board currentBoard) {
            return of(currentBoard, null, 0);
        }

        private static SearchNode of(Board board, SearchNode previousNode, int moves) {
            SearchNode searchNode = new SearchNode();
            searchNode.board = board;
            searchNode.previousNode = previousNode;
            searchNode.moves = moves;
            searchNode.score = board.manhattan();
            searchNode.priority = moves + searchNode.score;
            return searchNode;
        }

        private int getPriority() {
            return priority;
        }

        private int getMoves() {
            return moves;
        }

        private List<SearchNode> getNeighbours() {
            List<SearchNode> result = new ArrayList<>(3);
            int moves = this.moves + 1;
            for (Board neighbor : board.neighbors()) {
                if (previousNode == null || !neighbor.equals(previousNode.board)) {
                    result.add(SearchNode.of(neighbor, this, moves));
                }
            }
            return result;
        }

        private boolean isSolved() {
            return board.isGoal();
        }

        public List<Board> getSolution() {
            List<Board> solution = new ArrayList<>(moves);
            SearchNode currentNode = this;
            while (currentNode != null) {
                solution.add(0, currentNode.board);
                currentNode = currentNode.previousNode;
            }
            return solution;
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
