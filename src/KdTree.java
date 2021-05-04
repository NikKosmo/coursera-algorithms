import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;
import java.util.TreeSet;

public class KdTree {

    private final TreeSet<Point2D> xSortedPoints;

    public KdTree() {
        this.xSortedPoints = new TreeSet<>(Comparator.comparing(Point2D::x));
    }

    public boolean isEmpty() {
        return xSortedPoints.isEmpty();
    }

    public int size() {
        return xSortedPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNotNull(p);
        xSortedPoints.add(p);
    }

    public boolean contains(Point2D p) {
        checkNotNull(p);
        return xSortedPoints.contains(p);
    }

    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        if (isEmpty()) {
            return null;
        }
        return p;
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }
}
