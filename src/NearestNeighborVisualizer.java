import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.List;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {

        List<Point2D> points = Arrays.asList(
                new Point2D(0.7, 0.2),
                new Point2D(0.5, 0.4),
                new Point2D(0.2, 0.3),
                new Point2D(0.4, 0.7),
                new Point2D(0.9, 0.6)
//                ,
//                new Point2D(0.3125, 0.0625),
//                new Point2D(1.0, 0.1875),
//                new Point2D(0.25, 0.875),
//                new Point2D(0.5625, 0.5625),
//                new Point2D(0.75, 0.5),
//                new Point2D(0.1875, 0.8125),
//                new Point2D(0.375, 0.3125),
//                new Point2D(0.4375, 1.0),
//                new Point2D(0.5, 0.6875),
//                new Point2D(0.125, 0.9375)
        );
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        points.forEach(brute::insert);
        points.forEach(kdtree::insert);

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
//        while (true) {

        // the location (x, y) of the mouse
//            double x = StdDraw.mouseX();
//            double y = StdDraw.mouseY();
//        Point2D query = new Point2D(.875, 0);
        Point2D query = new Point2D(0.93, 0.87);


        // draw all of the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();

        // draw in red the nearest neighbor (using brute-force algorithm)
        StdDraw.setPenRadius(0.03);
        StdDraw.setPenColor(StdDraw.RED);
        brute.nearest(query).draw();

        // draw in blue the nearest neighbor (using kd-tree algorithm)
        Point2D nearest = kdtree.nearest(query);
        StdDraw.setPenRadius(0.02);
        StdDraw.setPenColor(StdDraw.BLUE);
        nearest.draw();

        StdDraw.setPenColor(StdDraw.GREEN);
        query.draw();
        StdDraw.show();
        StdDraw.pause(40);
//        }
    }
}