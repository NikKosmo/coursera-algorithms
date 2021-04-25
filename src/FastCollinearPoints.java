import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;


public class FastCollinearPoints {

    private final List<LineSegment> segments = new LinkedList<>();

    public FastCollinearPoints(Point[] points) {
        checkNullability(points);
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        checkSimilarPoints(copy);
        List<ProtoLineSegment> protoLineSegments = new ArrayList<>();
        for (int i = 0; i < copy.length - 3; i++) {
            Point point = copy[i];
            PointWithSlope[] pointsWithSlopes = getSortedPointsWithSlopes(copy, point, i + 1);
            protoLineSegments.addAll(getLineSegments(point, pointsWithSlopes));
        }
        if (!protoLineSegments.isEmpty()) {
            segments.addAll(cleanSubsegments(protoLineSegments));
        }
    }

    private List<LineSegment> cleanSubsegments(List<ProtoLineSegment> protoLineSegments) {
        List<LineSegment> result = new ArrayList<>();
        protoLineSegments.sort(Comparator.naturalOrder());
        ProtoLineSegment previousSegment = protoLineSegments.get(0);
        for (int i = 1; i < protoLineSegments.size(); i++) {
            ProtoLineSegment currentSegment = protoLineSegments.get(i);
            if (!previousSegment.inLine(currentSegment)) {
                result.add(previousSegment.getLineSegment());
                previousSegment = currentSegment;
            }
        }
        result.add(previousSegment.getLineSegment());
        return result;
    }

    private PointWithSlope[] getSortedPointsWithSlopes(Point[] points, Point point, int indent) {
        PointWithSlope[] pointsWithSlopes = new PointWithSlope[points.length - indent];
        int pointer = 0;
        for (int j = indent; j < points.length; j++) {
            Point comparedPoint = points[j];
            pointsWithSlopes[pointer++] = new PointWithSlope(comparedPoint, point.slopeTo(comparedPoint));
        }
        Arrays.sort(pointsWithSlopes);
        return pointsWithSlopes;
    }

    private List<ProtoLineSegment> getLineSegments(Point point, PointWithSlope[] pointsWithSlopes) {
        List<ProtoLineSegment> result = new ArrayList<>();
        List<Point> alignedPoints = new ArrayList<>();
        PointWithSlope firstTuple = pointsWithSlopes[0];
        alignedPoints.add(firstTuple.getPoint());
        double currentSlope = firstTuple.getSlope();
        for (int j = 1; j < pointsWithSlopes.length; j++) {
            PointWithSlope currentTuple = pointsWithSlopes[j];
            if (currentSlope != currentTuple.getSlope()) {
                if (alignedPoints.size() >= 3) {
                    result.add(new ProtoLineSegment(point, alignedPoints.get(alignedPoints.size() - 1)));
                }
                alignedPoints.clear();
            }
            alignedPoints.add(currentTuple.getPoint());
            currentSlope = currentTuple.getSlope();
        }
        if (alignedPoints.size() >= 3) {
            result.add(new ProtoLineSegment(point, alignedPoints.get(alignedPoints.size() - 1)));
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

    private static class PointWithSlope implements Comparable<PointWithSlope> {
        private final Point point;
        private final double slope;

        PointWithSlope(Point point, double slope) {
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
        public int compareTo(PointWithSlope o) {
            return Double.compare(this.getSlope(), o.getSlope());
        }
    }

    private static class ProtoLineSegment implements Comparable<ProtoLineSegment> {
        private final Point first;
        private final Point second;
        private Double slope;

        private ProtoLineSegment(Point first, Point second) {
            this.first = first;
            this.second = second;
        }

        public Point getFirst() {
            return first;
        }

        public Point getSecond() {
            return second;
        }

        public double getSlope() {
            if (slope == null) {
                slope = first.slopeTo(second);
            }
            return slope;
        }

        public boolean inLine(ProtoLineSegment another) {
            return this.second == another.getSecond()
                    && this.getSlope() == another.getSlope();
        }

        public LineSegment getLineSegment() {
            return new LineSegment(first, second);
        }

        @Override
        public int compareTo(ProtoLineSegment o) {
            return Comparator.comparing(ProtoLineSegment::getSecond)
                    .thenComparing(ProtoLineSegment::getSlope)
                    .thenComparing(ProtoLineSegment::getFirst)
                    .compare(this, o);
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