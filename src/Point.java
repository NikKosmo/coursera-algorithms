import edu.princeton.cs.algs4.StdDraw;

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
        System.out.println(Math.round(0.1));
        System.out.println(Math.round(0.6));
        System.out.println(Math.round(0.1));
        System.out.println(Math.round(-0.6));
    }
}