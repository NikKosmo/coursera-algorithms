import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PercolationTest {

    @Test
    public void testStraight() {
        assertTrue(testStraight(3));
        assertTrue(testStraight(5));
    }

    private boolean testStraight(int size) {
        Percolation percolation = createWithOpenRightSide(size);
        print(percolation);
        return percolation.percolates();
    }

    private Percolation createWithOpenRightSide(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 1; i <= size; i++) {
            percolation.open(i, size);
        }
        return percolation;
    }

    @Test
    public void testDiagonal() {
        assertFalse(testDiagonal(3));
        assertFalse(testDiagonal(5));
    }

    private boolean testDiagonal(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 1; i <= size; i++) {
            percolation.open(i, i);
        }
        print(percolation);
        return percolation.percolates();
    }

    @Test
    public void testBottomRow() {
        assertFalse(testBottomRow(3));
        assertFalse(testBottomRow(5));
    }

    private boolean testBottomRow(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 1; i <= size; i++) {
            percolation.open(size, i);
        }
        print(percolation);
        return percolation.percolates();
    }

    @Test
    public void testTopRow() {
        assertFalse(testTopRow(3));
        assertFalse(testTopRow(5));
    }

    private boolean testTopRow(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 1; i <= size; i++) {
            percolation.open(1, i);
        }
        print(percolation);
        return percolation.percolates();
    }

    @Test
    public void testUnlinked() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(3, 3);
        assertFalse(percolation.percolates());
        print(percolation);
    }

    @Test
    public void openIsOpen() {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
        assertFalse(percolation.isOpen(2, 2));
    }

    @Test
    public void topLinkedToTheTopIsFull() {
        int size = 5;
        Percolation withOpenRightSide = createWithOpenRightSide(size);
        for (int i = 1; i <= size; i++) {
            assertTrue(withOpenRightSide.isOpen(1, size));
            assertTrue(withOpenRightSide.isFull(1, size));
        }
    }

    @Test
    public void noOverflow() {
        int size = 5;
        Percolation withOpenRightSide = createWithOpenRightSide(size);
        withOpenRightSide.open(size, 1);
        assertTrue(withOpenRightSide.isOpen(size, 1));
        assertFalse(withOpenRightSide.isFull(size, 1));
    }

    private void print(Percolation percolation) {
        boolean[] sites = (boolean[]) ReflectionUtils.tryToReadFieldValue(Percolation.class, "sites", percolation).toOptional().get();
        int size = (int) ReflectionUtils.tryToReadFieldValue(Percolation.class, "size", percolation).toOptional().get();
        print(sites, size);
    }

    public void print(boolean[] sites, int size) {
        System.out.println("----------");
        int lastColumn = size - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String site = sites[getTargetPlace(i, j, size)] ? "\u25A2" : "\u25A6";
                if (j != lastColumn) {
                    System.out.print(site);
                } else {
                    System.out.println(site);
                }
            }
        }
    }

    private int getTargetPlace(int row, int col, int size) {
        return row * size + col;
    }


}