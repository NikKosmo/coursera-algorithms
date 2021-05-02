import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private short[][] tiles;
    private byte size;
    private byte emptyRow = -1;
    private byte emptyCol = -1;
    private short hamming = -1;
    private int manhattan = -1;
    private List<Board> neighbours;

    private Board() {
    }

    public Board(int[][] tiles) {
        this.tiles = deepCopy(tiles);
        this.size = (byte) tiles.length;
    }

    private short[][] deepCopy(int[][] tiles) {
        short[][] result = new short[tiles.length][];
        for (byte row = 0; row < tiles.length; row++) {
            result[row] = new short[tiles.length];
            for (byte col = 0; col < tiles.length; col++) {
                int tile = tiles[row][col];
                result[row][col] = (short) tile;
                if (tile == 0) {
                    emptyRow = row;
                    emptyCol = col;
                }
            }
        }
        return result;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(size);
        for (int row = 0; row < size; row++) {
            stringBuilder.append("\n");
            for (int col = 0; col < size; col++) {
                stringBuilder.append(" ");
                stringBuilder.append(tiles[row][col]);
            }
        }
        return stringBuilder.toString();
    }

    public int dimension() {
        return size;
    }

    public int hamming() {
        if (hamming < 0) {
            int result = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    result += hammingByPoint(row, col);
                }
            }
            hamming = (short) result;
        }
        return hamming;
    }

    private int hammingByPoint(int row, int col) {
        int tile = tiles[row][col];
        return tile == 0 || (getTargetRow(tile) == row && getTargetCol(tile) == col) ?
                0 :
                1;
    }

    public int manhattan() {
        if (manhattan < 0) {
            int result = 0;
            for (int row = 0; row < size; row++) {
                for (int col = 0; col < size; col++) {
                    result += manhattanByPoint(row, col);
                }
            }
            manhattan = result;
        }
        return manhattan;
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

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return size == board.size
                && emptyRow == board.emptyRow
                && emptyCol == board.emptyCol
                && Arrays.deepEquals(tiles, board.tiles);
    }

    private Board moveUp() {
        short[][] result = Arrays.copyOf(tiles, size);
        byte resultEmptyRow = (byte) (emptyRow - 1);
        swapVertical(result, resultEmptyRow);
        return createNewBoard(result, resultEmptyRow, emptyCol);
    }

    private Board moveDown() {
        short[][] result = Arrays.copyOf(tiles, size);
        byte resultEmptyRow = (byte) (emptyRow + 1);
        swapVertical(result, resultEmptyRow);
        return createNewBoard(result, resultEmptyRow, emptyCol);
    }

    private void swapVertical(short[][] result, int targetRow) {
        result[targetRow] = Arrays.copyOf(result[targetRow], size);
        result[emptyRow] = Arrays.copyOf(result[emptyRow], size);
        result[targetRow][emptyCol] = tiles[emptyRow][emptyCol];
        result[emptyRow][emptyCol] = tiles[targetRow][emptyCol];
    }

    private Board moveRight() {
        byte resultEmptyCol = (byte) (emptyCol + 1);
        short[][] result = new short[tiles.length][];
        for (byte row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], resultEmptyCol);
            } else {
                result[row] = tiles[row];
            }
        }
        return createNewBoard(result, emptyRow, resultEmptyCol);
    }

    private Board moveLeft() {
        byte resultEmptyCol = (byte) (emptyCol - 1);
        short[][] result = new short[tiles.length][];
        for (byte row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], resultEmptyCol);
            } else {
                result[row] = tiles[row];
            }
        }
        return createNewBoard(result, emptyRow, resultEmptyCol);
    }

    private short[] swapHorizontal(short[] row, int swappedPlace) {
        return swapHorizontal(row, emptyCol, swappedPlace);
    }

    private short[] swapHorizontal(short[] row, int one, int another) {
        short[] result = Arrays.copyOf(row, size);
        result[one] = row[another];
        result[another] = row[one];
        return result;
    }

    public Iterable<Board> neighbors() {
        if (neighbours == null) {
            neighbours = new ArrayList<>(4);
            if (emptyRow > 0) {
                neighbours.add(moveUp());
            }
            if (emptyCol > 0) {
                neighbours.add(moveLeft());
            }
            if (emptyRow < size - 1) {
                neighbours.add(moveDown());
            }
            if (emptyCol < size - 1) {
                neighbours.add(moveRight());
            }
        }
        return neighbours;
    }

    public Board twin() {
        short[][] result = Arrays.copyOf(tiles, size);
        int targetCol = emptyCol == 0 ? 1 : emptyCol - 1;
        int targetRow = emptyRow == 0 ? 1 : emptyRow - 1;
        result[targetRow] = swapHorizontal(result[targetRow], emptyCol, targetCol);
        return createNewBoard(result, emptyRow, emptyCol);
    }

    private Board createNewBoard(short[][] board, byte newEmptyRow, byte newEmptyCol) {
        Board result = new Board();
        result.tiles = board;
        result.size = (byte) board.length;
        result.emptyRow = newEmptyRow;
        result.emptyCol = newEmptyCol;
        return result;
    }

    public static void main(String[] args) {
        // do nothing
    }
}
