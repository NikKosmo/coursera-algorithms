import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        int yComparison = Integer.compare(this.y, that.y);
        return yComparison != 0 ?
                yComparison :
                Integer.compare(this.x, that.x);
    }

    public double slopeTo(Point that) {
        if (compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        }
        if (that.y == this.y) {
            return 0;
        }
        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    public Comparator<Point> slopeOrder() {
        return Comparator.comparingDouble(this::slopeTo);
    }

    public static void main(String[] args) {
        // Do nothing

        Point[] points = new Point[]{
                new Point(11553, 5931),
                new Point(9161, 5178),
                new Point(5143, 6373),
                new Point(8763, 3575),
                new Point(8060, 5480),
                new Point(9341, 3284),
                new Point(5791, 12853),
                new Point(8060, 4760),
                new Point(9161, 5550),
                new Point(10248, 4829),
                new Point(8060, 4772),
                new Point(9332, 3231),
                new Point(9161, 342),
                new Point(9161, 5085),
                new Point(9431, 3814),
                new Point(5422, 9163),
                new Point(8060, 5492),
                new Point(9260, 2807),
                new Point(5476, 9703),
                new Point(11193, 5627)
        };
        printPoints(points);
    }

    private static void printPoints(Point[] points) {
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