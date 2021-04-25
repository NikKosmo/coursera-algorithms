import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BruteCollinearPoints {

    private final List<LineSegment> segments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points) {
        checkNullability(points);
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        checkSimilarPoints(copy);
        for (int i = 0; i < copy.length - 3; i++) {
            for (int j = i + 1; j < copy.length - 2; j++) {
                for (int k = j + 1; k < copy.length - 1; k++) {
                    for (int l = k + 1; l < copy.length; l++) {
                        Point first = copy[i];
                        Point second = copy[j];
                        Point third = copy[k];
                        Point fourth = copy[l];
                        double targetSlope = first.slopeTo(second);
                        if (first.slopeTo(third) == targetSlope && first.slopeTo(fourth) == targetSlope) {
                            segments.add(new LineSegment(first, fourth));
                        }
                    }
                }
            }
        }
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

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
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