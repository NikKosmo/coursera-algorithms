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

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return false;
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
