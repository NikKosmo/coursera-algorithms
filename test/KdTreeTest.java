import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class KdTreeTest {


    @Test
    public void insertFiveAndCheckSize() {
        KdTree kdTree = createFiveDiagonalPointsKdTree();
        Assertions.assertFalse(kdTree.isEmpty());
        Assertions.assertEquals(5, kdTree.size());
    }

    @Test
    public void containsAllInserted() {
        KdTree kdTree = createFiveDiagonalPointsKdTree();
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
        KdTree kdTree = createFiveDiagonalPointsKdTree();
        Point2D point2D = new Point2D(0, 4);
        PointSET pointSET = new PointSET();
        for (int i = 0; i < 5; i++) {
            pointSET.insert(new Point2D(i, i));
        }
        Assertions.assertEquals(pointSET.nearest(point2D), kdTree.nearest(point2D));
    }

    @Test
    public void checkRangeForDiagonalFive() {
        KdTree kdTree = createFiveDiagonalPointsKdTree();
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

    @Test
    public void specificTest() {
        Point2D targetPoint = new Point2D(.2, .3);
        List<Point2D> points = Arrays.asList(new Point2D(.7, .2),
                                             new Point2D(.5, .4),
                                             targetPoint,
                                             new Point2D(.4, .7),
                                             new Point2D(.9, .6));
        PointSET pointSET = createPointSetWithPoints(points);
        KdTree kdTree = createKdTreeWithPoints(points);

        Assertions.assertTrue(pointSET.contains(targetPoint));
        Assertions.assertTrue(kdTree.contains(targetPoint));
    }

    @Test
    public void checkSpecificTraversal() {
        List<Point2D> points = Arrays.asList(new Point2D(.7, .2),
                                             new Point2D(.5, .4),
                                             new Point2D(.2, .3),
                                             new Point2D(.4, .7),
                                             new Point2D(.9, .6));

        KdTree kdTree = createKdTreeWithPoints(points);
        RectHV rectHV = new RectHV(.35, .71, .78, .85);
        Assertions.assertFalse(kdTree.range(rectHV).iterator().hasNext());
    }

    @Test
    public void checkSpecificTraversal_2() {
        List<Point2D> points = Arrays.asList(
                new Point2D(0.3125, 0.0625),
                new Point2D(1.0, 0.1875),
                new Point2D(0.25, 0.875),
                new Point2D(0.5625, 0.5625),
                new Point2D(0.75, 0.5),
                new Point2D(0.1875, 0.8125),
                new Point2D(0.375, 0.3125),
                new Point2D(0.4375, 1.0),
                new Point2D(0.5, 0.6875),
                new Point2D(0.125, 0.9375));

        KdTree kdTree = createKdTreeWithPoints(points);
        Point2D point = new Point2D(.875, 0);
        Assertions.assertNotNull(kdTree.nearest(point));
    }

    private KdTree createKdTreeWithPoints(List<Point2D> points) {
        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);
        return kdTree;
    }

    private PointSET createPointSetWithPoints(List<Point2D> points) {
        PointSET pointSET = new PointSET();
        points.forEach(pointSET::insert);
        return pointSET;
    }

    private KdTree createFiveDiagonalPointsKdTree() {
        KdTree kdTree = new KdTree();
        for (int i = 0; i < 5; i++) {
            kdTree.insert(new Point2D(i, i));
        }
        return createKdTreeWithPoints(createFiveDiagonalPoints());
    }

    private List<Point2D> createFiveDiagonalPoints() {
        List<Point2D> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            result.add(new Point2D(i, i));
        }
        return result;
    }
}