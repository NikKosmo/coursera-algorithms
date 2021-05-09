import edu.princeton.cs.algs4.Point2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class KdTreeTest {


    @Test
    public void insertFiveAndCheckSize() {
        KdTree kdTree = createFiveDiagonalPoints();
        Assertions.assertFalse(kdTree.isEmpty());
        Assertions.assertEquals(5, kdTree.size());
    }

    @Test
    public void containsAllInserted() {
        KdTree kdTree = createFiveDiagonalPoints();
        for (int i = 0; i < 5; i++) {
            Assertions.assertTrue(kdTree.contains(new Point2D(i, i)));
        }
    }

    @Test
    public void containsUniqueOnly() {
        KdTree kdTree = new KdTree();
        IntStream.range(0, 15).map(i -> i % 5)
                .mapToObj(i -> new Point2D(i, i))
                .forEach(kdTree::insert);
        for (int i = 0; i < 5; i++) {
            Assertions.assertTrue(kdTree.contains(new Point2D(i, i)));
        }
        Assertions.assertEquals(5, kdTree.size());
    }

    @Test
    public void checkRandomlyInserted() {
        List<Point2D> points = IntStream.range(0, 100)
                .mapToObj(i -> new Point2D(i, i))
                .collect(Collectors.toList());
        Collections.shuffle(points);
        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);
        Collections.shuffle(points);
        points.forEach(point -> Assertions.assertTrue(kdTree.contains(point)));
    }

    @Test
    public void checkNearestForRandomlyInserted() {
        List<Point2D> points = IntStream.rangeClosed(0, 100)
                .mapToObj(i -> new Point2D(i, i))
                .collect(Collectors.toList());
        Collections.shuffle(points);
        KdTree kdTree = new KdTree();
        PointSET pointSET = new PointSET();
        points.forEach(kdTree::insert);
        points.forEach(pointSET::insert);

        Point2D point2D = new Point2D(0, 100);
        Point2D expected = pointSET.nearest(point2D);
        Assertions.assertEquals(expected, kdTree.nearest(point2D));
    }

    @Test
    public void checkNearestForInserted() {
        KdTree kdTree = createFiveDiagonalPoints();
        Point2D point2D = new Point2D(0, 4);
        PointSET pointSET = new PointSET();
        for (int i = 0; i < 5; i++) {
            pointSET.insert(new Point2D(i, i));
        }
        Point2D expected = pointSET.nearest(point2D);
        Assertions.assertEquals(new Point2D(2, 2), kdTree.nearest(point2D));
        Assertions.assertEquals(new Point2D(2, 2), pointSET.nearest(point2D));
    }

    private KdTree createFiveDiagonalPoints() {
        KdTree kdTree = new KdTree();
        for (int i = 0; i < 5; i++) {
            kdTree.insert(new Point2D(i, i));
        }
        return kdTree;
    }
}