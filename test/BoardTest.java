import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    public void testManhattan() {
        Board uniformBoard = createUniformBoard(3, 5);
        Assertions.assertEquals(3, uniformBoard.dimension());
        Assertions.assertEquals(8, uniformBoard.hamming());
        Assertions.assertEquals(12, uniformBoard.manhattan());
    }

    @Test
    public void checkTargetBoard() {
        Board exampleBoard = createExampleBoard();
        Assertions.assertEquals(3, exampleBoard.dimension());
        Assertions.assertEquals(5, exampleBoard.hamming());
        Assertions.assertEquals(10, exampleBoard.manhattan());
    }

    private Board createUniformBoard(int size, int tile) {
        int[][] tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = tile;
            }
        }
        return new Board(tiles);
    }

//    @Test
//    public void correctNeighbors() {
//        Board exampleBoard = createExampleBoard();
//        Board smallBoard = createSmallBoard();
//        Assertions.assertEquals(4, exampleBoard.neighbors().size());
//        exampleBoard.neighbors().forEach(board -> Assertions.assertEquals(3, board.neighbors().size()));
//        Assertions.assertEquals(2, smallBoard.neighbors().size());
//    }

    @Test
    public void twinCreation() {
        Board exampleBoard = createSmallBoard();
        Board twin = exampleBoard.twin();
        System.out.println(twin);
        System.out.println(exampleBoard);
        System.out.println(exampleBoard.equals(twin));
    }

    private Board createExampleBoard() {
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5},

//                {1, 2, 3},
//                {4, 6, 5},
//                {7, 8, 0},
        };
        return new Board(tiles);
    }

    private Board createSmallBoard() {
        int[][] tiles = {
                {0, 2},
                {1, 3},

//                {2, 1},
//                {3, 0},

        };
        return new Board(tiles);
    }

    @Test
    public void solverForSolvable() {
        Board exampleBoard = createExampleBoard();
        Solver solver = new Solver(exampleBoard);
        for (Board board : solver.solution()) {
            System.out.println(board);
            System.out.println("----->");
        }
        Assertions.assertTrue(solver.isSolvable());
    }

    @Test
    public void solverForUnsolvable() {
        Board exampleBoard = createExampleBoard().twin();
        System.out.println(exampleBoard);
        Solver solver = new Solver(exampleBoard);
        Assertions.assertFalse(solver.isSolvable());
    }


}