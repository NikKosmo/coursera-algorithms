import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private int[][] tiles;
    private int size;
    private int emptyRow = -1;
    private int emptyCol = -1;
    private int hamming = -1;
    private int manhattan = -1;
    private List<Board> neighbours;

    private Board() {
    }

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
            hamming = result;
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
        int[][] result = Arrays.copyOf(tiles, size);
        int resultEmptyRow = emptyRow - 1;
        swapVertical(result, resultEmptyRow);
        return createNewBoard(result, resultEmptyRow, emptyCol);
    }

    private Board moveDown() {
        int[][] result = Arrays.copyOf(tiles, size);
        int resultEmptyRow = emptyRow + 1;
        swapVertical(result, resultEmptyRow);
        return createNewBoard(result, resultEmptyRow, emptyCol);
    }

    private void swapVertical(int[][] result, int targetRow) {
        result[targetRow] = Arrays.copyOf(result[targetRow], size);
        result[emptyRow] = Arrays.copyOf(result[emptyRow], size);
        result[targetRow][emptyCol] = tiles[emptyRow][emptyCol];
        result[emptyRow][emptyCol] = tiles[targetRow][emptyCol];
    }

    private Board moveRight() {
        int resultEmptyCol = emptyCol + 1;
        int[][] result = new int[tiles.length][];
        for (int row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], resultEmptyCol);
            } else {
                result[row] = tiles[row];
            }
        }
        return createNewBoard(result, emptyRow, resultEmptyCol);
    }

    private Board moveLeft() {
        int resultEmptyCol = emptyCol - 1;
        int[][] result = new int[tiles.length][];
        for (int row = 0; row < size; row++) {
            if (row == emptyRow) {
                result[row] = swapHorizontal(tiles[row], resultEmptyCol);
            } else {
                result[row] = tiles[row];
            }
        }
        return createNewBoard(result, emptyRow, resultEmptyCol);
    }

    private int[] swapHorizontal(int[] row, int swappedPlace) {
        return swapHorizontal(row, emptyCol, swappedPlace);
    }

    private int[] swapHorizontal(int[] row, int one, int another) {
        int[] result = Arrays.copyOf(row, size);
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
        int[][] result = Arrays.copyOf(tiles, size);
        int targetCol = emptyCol == 0 ? 1 : emptyCol - 1;
        int targetRow = emptyRow == 0 ? 1 : emptyRow - 1;
        result[targetRow] = swapHorizontal(result[targetRow], emptyCol, targetCol);
        return createNewBoard(result, emptyRow, emptyCol);
    }

    private Board createNewBoard(int[][] board, int newEmptyRow, int newEmptyCol) {
        Board result = new Board();
        result.tiles = board;
        result.size = board.length;
        result.emptyRow = newEmptyRow;
        result.emptyCol = newEmptyCol;
        return result;
    }

    public static void main(String[] args) {

    }
}
