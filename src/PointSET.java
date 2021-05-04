import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PointSET {

    private final Set<Point2D> points;

    public PointSET() {
        this.points = new HashSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNotNull(p);
        points.add(p);
    }

    public boolean contains(Point2D p) {
        checkNotNull(p);
        return points.contains(p);
    }

    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);
        List<Point2D> result = new ArrayList<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                result.add(point);
            }
        }
        return result;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        Point2D result = null;
        double distanceToResult = 0;
        for (Point2D point : points) {
            double distance = point.distanceTo(p);
            if (result == null) {
                result = point;
            } else if (distance < distanceToResult) {
                result = point;
            }
        }
        return result;
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {

    }
}