import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {

    @Test
    public void testStraight() {
        assertTrue(testStraight(3));
        assertTrue(testStraight(5));
    }

    private boolean testStraight(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 0; i < size; i++) {
            percolation.open(i, size - 1);
        }
        return percolation.percolates();
    }

    @Test
    public void testDiagonal() {
        assertFalse(testDiagonal(3));
        assertFalse(testDiagonal(5));
    }

    private boolean testDiagonal(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 0; i < size; i++) {
            percolation.open(i, i);
        }
        return percolation.percolates();
    }

    @Test
    public void testBottomRow() {
        assertFalse(testBottomRow(3));
        assertFalse(testBottomRow(5));
    }

    private boolean testBottomRow(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 0; i < size; i++) {
            percolation.open(size - 1, i);
        }
        percolation.print();
        return percolation.percolates();
    }

    @Test
    public void testTopRow() {
        assertFalse(testTopRow(3));
        assertFalse(testTopRow(5));
    }

    private boolean testTopRow(int size) {
        Percolation percolation = new Percolation(size);
        for (int i = 0; i < size; i++) {
            percolation.open(0, i);
        }
        percolation.print();
        return percolation.percolates();
    }

    @Test
    public void testUnlinked() {
        Percolation percolation = new Percolation(3);
            percolation.open( 0, 0);
            percolation.open( 1, 0);
            percolation.open( 2, 2);
        assertFalse(percolation.percolates());
    }

}