import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Assertions.assertEquals(pointSET.nearest(point2D), kdTree.nearest(point2D));
    }

    @Test
    public void checkNearestForDiagonalFive() {
        KdTree kdTree = createFiveDiagonalPoints();
        Point2D point2D = new Point2D(0, 4);
        PointSET pointSET = new PointSET();
        for (int i = 0; i < 5; i++) {
            pointSET.insert(new Point2D(i, i));
        }
        Assertions.assertEquals(pointSET.nearest(point2D), kdTree.nearest(point2D));
    }

    @Test
    public void checkRangeForDiagonalFive() {
        KdTree kdTree = createFiveDiagonalPoints();
        PointSET pointSET = new PointSET();
        for (int i = 0; i < 5; i++) {
            pointSET.insert(new Point2D(i, i));
        }
        RectHV rectHV = new RectHV(1, 1, 3, 3);
        Set<Point2D> pointSetResult = new HashSet<>();
        pointSET.range(rectHV).forEach(pointSetResult::add);
        Set<Point2D> kdTreeResult = new HashSet<>();
        kdTree.range(rectHV).forEach(kdTreeResult::add);
        Assertions.assertEquals(pointSetResult.size(), kdTreeResult.size());
        Assertions.assertEquals(pointSetResult, kdTreeResult);
    }

    @Test
    public void checkRangeForRandomlyInserted() {
        List<Point2D> points = IntStream.rangeClosed(0, 100)
                .mapToObj(i -> new Point2D(i, i))
                .collect(Collectors.toList());
        Collections.shuffle(points);
        KdTree kdTree = new KdTree();
        PointSET pointSET = new PointSET();
        points.forEach(kdTree::insert);
        points.forEach(pointSET::insert);

        RectHV rectHV = new RectHV(25, 25, 75, 75);
        Set<Point2D> pointSetResult = new HashSet<>();
        pointSET.range(rectHV).forEach(pointSetResult::add);
        Set<Point2D> kdTreeResult = new HashSet<>();
        kdTree.range(rectHV).forEach(kdTreeResult::add);
        Assertions.assertEquals(pointSetResult.size(), kdTreeResult.size());
        Assertions.assertEquals(pointSetResult, kdTreeResult);
    }

    private KdTree createFiveDiagonalPoints() {
        KdTree kdTree = new KdTree();
        for (int i = 0; i < 5; i++) {
            kdTree.insert(new Point2D(i, i));
        }
        return kdTree;
    }
}