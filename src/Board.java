import java.util.Arrays;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiles;
    private final int size;

    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        this.size = tiles.length;
    }

    private int[][] deepCopy(int[][] tiles) {
        int[][] result = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i++) {
            result[i] = Arrays.copyOf(tiles[i], tiles.length);
        }
        return result;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(size);
        for (int i = 0; i < size; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < size; j++) {
                stringBuilder.append(tiles[i][j]);
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    public int dimension() {
        return size;
    }

    public int hamming() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += hammingByPoint(i, j);
            }
        }
        return result;
    }

    private int hammingByPoint(int row, int col) {
        int tile = tiles[row][col];
        return tile == 0 || (getTargetRow(tile) == row && getTargetCol(tile) == col) ?
                0 :
                1;
    }

    public int manhattan() {
        int result = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result += manhattanByPoint(i, j);
            }
        }
        return result;
    }

    private int manhattanByPoint(int row, int col) {
        int tile = tiles[row][col];
        if (tile == 0) {
            return 0;
        }
        int targetRow = getTargetRow(tile);
        int targetCol = getTargetCol(tile);
        return Math.abs(row - targetRow) + Math.abs(col - targetCol);
    }

    private int getTargetCol(int tile) {
        return (tile - 1) % size;
    }

    private int getTargetRow(int tile) {
        return (tile - 1) / size;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return size == board.size && Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
