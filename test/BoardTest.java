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

    @Test
    public void correctNeighbors() {
        Board exampleBoard = createExampleBoard();
        Board smallBoard = createSmallBoard();
        Assertions.assertEquals(4, exampleBoard.neighbors().size());
        exampleBoard.neighbors().forEach(board -> Assertions.assertEquals(3, board.neighbors().size()));
        Assertions.assertEquals(2, smallBoard.neighbors().size());
    }

    private Board createExampleBoard() {
        int[][] tiles = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        return new Board(tiles);
    }

    private Board createSmallBoard() {
        int[][] tiles = {
                {1, 3},
                {0, 2}
        };
        return new Board(tiles);
    }


}