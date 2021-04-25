import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class FastCollinearPoints {

    private final List<LineSegment> segments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        checkNullability(points);
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        checkSimilarPoints(copy);
        for (int i = 0; i < copy.length - 3; i++) {
            Point point = copy[i];
            Tuple[] pointsWithSlopes = getSortedPointsWithSlopes(copy, point, i + 1);
            segments.addAll(getLineSegments(point, pointsWithSlopes));
        }
    }

    private Tuple[] getSortedPointsWithSlopes(Point[] points, Point point, int indent) {
        Tuple[] pointsWithSlopes = new Tuple[points.length - indent];
        int pointer = 0;
        for (int j = indent; j < points.length; j++) {
            Point comparedPoint = points[j];
            pointsWithSlopes[pointer++] = new Tuple(comparedPoint, point.slopeTo(comparedPoint));
        }
        Arrays.sort(pointsWithSlopes);
        return pointsWithSlopes;
    }

    private List<LineSegment> getLineSegments(Point point, Tuple[] pointsWithSlopes) {
        List<LineSegment> result = new ArrayList<>();
        List<Point> alignedPoints = new ArrayList<>();
        Tuple firstTuple = pointsWithSlopes[0];
        alignedPoints.add(firstTuple.getPoint());
        double currentSlope = firstTuple.getSlope();
        for (int j = 1; j < pointsWithSlopes.length; j++) {
            Tuple currentTuple = pointsWithSlopes[j];
            if (currentSlope != currentTuple.getSlope()) {
                if (alignedPoints.size() > 3) {
                    result.add(new LineSegment(point, alignedPoints.get(alignedPoints.size() - 1)));
                }
                alignedPoints.clear();
            }
            alignedPoints.add(currentTuple.getPoint());
            currentSlope = currentTuple.getSlope();
        }
        if (alignedPoints.size() > 3) {
            result.add(new LineSegment(point, alignedPoints.get(alignedPoints.size() - 1)));
        }
        return result;
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void checkNullability(Point[] points) {
        checkArgument(points);
        for (Point point : points) {
            checkArgument(point);
        }
    }

    private void checkSimilarPoints(Point[] points) {
        Point previousPoint = null;
        for (Point point : points) {
            if (Objects.equals(previousPoint, point)) {
                throw new IllegalArgumentException();
            }
            previousPoint = point;
        }
    }

    private void checkArgument(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Tuple implements Comparable<Tuple> {
        private final Point point;
        private final double slope;

        Tuple(Point point, double slope) {
            this.point = point;
            this.slope = slope;
        }

        public Point getPoint() {
            return point;
        }

        public double getSlope() {
            return slope;
        }

        @Override
        public int compareTo(Tuple o) {
            return Double.compare(this.getSlope(), o.getSlope());
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}