import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {

    private final Set<Point2D> points;

    public PointSET() {
        this.points = new TreeSet<>();
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
        points.forEach(Point2D::draw);
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
            double distance = point.distanceSquaredTo(p);
            if (result == null) {
                result = point;
                distanceToResult = distance;
            } else if (distance < distanceToResult) {
                result = point;
                distanceToResult = distance;
            }
        }
        return result;
    }

    private void checkNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    public static void main(String[] args) {
        // do nothing
    }
}
