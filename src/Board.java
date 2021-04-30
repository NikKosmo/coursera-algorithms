import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private final int[][] tiles;
    private final int size;
    private int emptyRow = -1;
    private int emptyCol = -1;

    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        this.size = tiles.length;
    }

    private int[][] deepCopy(int[][] tiles) {
        int[][] result = new int[tiles.length][];
        for (int row = 0; row < tiles.length; row++) {
            result[row] = Arrays.copyOf(tiles[row], tiles.length);
            if (emptyRow < 0) {
                for (int col = 0; col < tiles.length; col++) {
                    if (tiles[row][col] == 0) {
                        emptyRow = row;
                        emptyCol = col;
                        break;
                    }
                }
            }
        }
        return result;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(size);
        for (int row = 0; row < size; row++) {
            stringBuilder.append("\n");
            for (int col = 0; col < size; col++) {
                stringBuilder.append(tiles[row][col]);
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
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result += hammingByPoint(row, col);
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
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                result += manhattanByPoint(row, col);
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

    private Board moveUp() {
        int[][] result = Arrays.copyOf(tiles, size);
        swapVertical(result, emptyRow - 1);
        return new Board(result);
    }

    private Board moveDown() {
        int[][] result = Arrays.copyOf(tiles, size);
        swapVertical(result, emptyRow + 1);
        return new Board(result);
    }

    private void swapVertical(int[][] result, int targetRow) {
        result[targetRow] = Arrays.copyOf(result[targetRow], size);
        result[emptyRow] = Arrays.copyOf(result[emptyRow], size);
        result[targetRow][emptyCol] = tiles[emptyRow][emptyCol];
        result[emptyRow][emptyCol] = tiles[targetRow][emptyCol];
    }

    private Board moveRight() {
        int[][] result = new int[tiles.length][];
        for (int row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], emptyCol + 1);
            } else {
                result[row] = tiles[row];
            }
        }
        return new Board(result);
    }

    private Board moveLeft() {
        int[][] result = new int[tiles.length][];
        for (int row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], emptyCol - 1);
            } else {
                result[row] = tiles[row];
            }
        }
        return new Board(result);
    }

    private int[] swapHorizontal(int[] row, int swappedPlace) {
        int[] result = Arrays.copyOf(row, size);
        result[emptyCol] = row[swappedPlace];
        result[swappedPlace] = row[emptyCol];
        return result;
    }

    // all neighboring boards
    public List<Board> neighbors() {
        // TODO: 30.04.2021 make lazy?
        List<Board> result = new ArrayList<>(4);
        if (emptyRow > 0) {
            result.add(moveUp());
        }
        if (emptyCol > 0) {
            result.add(moveLeft());
        }
        if (emptyRow < size - 1) {
            result.add(moveDown());
        }
        if (emptyCol < size - 1) {
            result.add(moveRight());
        }
        return result;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }
}
