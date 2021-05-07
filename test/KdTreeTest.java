import edu.princeton.cs.algs4.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KdTreeTest {


    @Test
    public void insertFiveAndCheckSize() {
        KdTree kdTree = createFiveDiagonalPoints();
        Assertions.assertFalse(kdTree.isEmpty());
        Assertions.assertEquals(5, kdTree.size());
    }

    private KdTree createFiveDiagonalPoints() {
        KdTree kdTree = new KdTree();
        for (int i = 0; i < 5; i++) {
            kdTree.insert(new Point2D(i, i));
        }
        return kdTree;
    }
}