import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private double maxY;
    private double maxX;

    public KdTree() {
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        checkNotNull(p);
        root = insert(p, root, true);
    }

    private Node insert(Point2D point, Node node, boolean vertical) {
        if (node != null) {
            if (!node.point.equals(point)) {
                int i = vertical ?
                        Comparator.comparingDouble(Point2D::x).compare(point, node.point) :
                        Comparator.comparingDouble(Point2D::y).compare(point, node.point);
                if (i < 0) {
                    node.leftNode = insert(point, node.leftNode, !vertical);
                } else {
                    node.rightNode = insert(point, node.rightNode, !vertical);
                }
            }
            return node;
        }
        size++;
        updateMaxes(point);
        return Node.of(point, vertical);
    }

    private void updateMaxes(Point2D point) {
        maxY = Math.max(point.y(), maxY);
        maxX = Math.max(point.x(), maxX);
    }

    public boolean contains(Point2D p) {
        checkNotNull(p);
        return contains(p, root);
    }

    private boolean contains(Point2D point, Node node) {
        if (node != null) {
            if (!node.point.equals(point)) {
                int i = node.vertical ?
                        Comparator.comparingDouble(Point2D::x).compare(point, node.point) :
                        Comparator.comparingDouble(Point2D::y).compare(point, node.point);
                if (i < 0) {
                    return contains(point, node.leftNode);
                } else {
                    return contains(point, node.rightNode);
                }
            }
            return true;
        }
        return false;
    }

    public void draw() {
        draw(root);
    }

    private void draw(Node node) {
        if (node != null) {
            node.point.draw();
            draw(node.rightNode);
            draw(node.leftNode);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);
        if (isEmpty()) {
            return null;
        }
        List<Point2D> result = new ArrayList<>();
        addPointsInRange(root, rect, getBasicPlain(), result);
        return result;
    }

    private void addPointsInRange(Node node, RectHV rect, RectHV area, List<Point2D> result) {
        if (rect.contains(node.point)) {
            result.add(node.point);
        }
        if (node.leftNode != null) {
            RectHV leftSubNodeArea = node.getLeftSubNodeArea(area);
            if (leftSubNodeArea.intersects(rect)) {
                addPointsInRange(node.leftNode, rect, leftSubNodeArea, result);
            }
        }
        if (node.rightNode != null) {
            RectHV rightSubNodeArea = node.getRightSubNodeArea(area);
            if (rightSubNodeArea.intersects(rect)) {
                addPointsInRange(node.rightNode, rect, rightSubNodeArea, result);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        if (isEmpty()) {
            return null;
        }
        return nearest(p, root.point, root, getBasicPlain());
    }

    private Point2D nearest(Point2D p, Point2D currentChampion, Node node, RectHV area) {
        currentChampion = getChampion(p, currentChampion, node.point);
        double championDistance = p.distanceSquaredTo(currentChampion);
        RectHV rightSubNodeArea = node.getRightSubNodeArea(area);
        RectHV leftSubNodeArea = node.getLeftSubNodeArea(area);
        double squaredDistanceToRightNode = rightSubNodeArea.distanceSquaredTo(p);
        double squaredDistanceToLeftNode = leftSubNodeArea.distanceSquaredTo(p);
        Node closerNode;
        double closerNodeDistance;
        RectHV closerNodeArea;
        Node furtherNode;
        double furtherNodeDistance;
        RectHV furtherNodeArea;
//        draw(node, area);
        if (squaredDistanceToRightNode < squaredDistanceToLeftNode) {
            closerNode = node.rightNode;
            closerNodeDistance = squaredDistanceToRightNode;
            closerNodeArea = rightSubNodeArea;
            furtherNode = node.leftNode;
            furtherNodeDistance = squaredDistanceToLeftNode;
            furtherNodeArea = leftSubNodeArea;
        } else {
            closerNode = node.leftNode;
            closerNodeDistance = squaredDistanceToLeftNode;
            closerNodeArea = leftSubNodeArea;
            furtherNode = node.rightNode;
            furtherNodeDistance = squaredDistanceToRightNode;
            furtherNodeArea = rightSubNodeArea;
        }
        if (closerNode != null && championDistance >= closerNodeDistance) {
            Point2D closerNodeChampion = nearest(p, currentChampion, closerNode, closerNodeArea);
            currentChampion = getChampion(p, currentChampion, closerNodeChampion);
            championDistance = p.distanceSquaredTo(currentChampion);
        }
        if (furtherNode != null && championDistance >= furtherNodeDistance) {
            Point2D furtherNodeChampion = nearest(p, currentChampion, furtherNode, furtherNodeArea);
            currentChampion = getChampion(p, currentChampion, furtherNodeChampion);
        }
        return currentChampion;
    }

    private Point2D getChampion(Point2D target, Point2D a, Point2D b) {
        if (a != b) {
            double distanceToA = target.distanceSquaredTo(a);
            double distanceToB = target.distanceSquaredTo(b);
            return distanceToA < distanceToB ?
                    a :
                    b;
        }
        return a;
    }

    private RectHV getBasicPlain() {
        return new RectHV(0, 0, 1, 1);
//        return new RectHV(0, 0, maxX, maxY);
    }

//    private void draw(Node node, RectHV area) {
//        StdDraw.setPenRadius(0.005);
//        if (node.vertical) {
//            StdDraw.setPenColor(StdDraw.RED);
//            StdDraw.line(node.point.x(), area.ymin(), node.point.x(), area.ymax());
//        } else {
//            StdDraw.setPenColor(StdDraw.BLUE);
//            StdDraw.line(area.xmin(), node.point.y(), area.xmax(), node.point.y());
//        }
//    }

    private void checkNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Node {

        private Point2D point;
        private boolean vertical;
        private Node leftNode;
        private Node rightNode;

        public static Node of(Point2D point, boolean vertical) {
            Node node = new Node();
            node.point = point;
            node.vertical = vertical;
            return node;
        }

        private RectHV getLeftSubNodeArea(RectHV area) {
            return vertical ?
                    new RectHV(area.xmin(), area.ymin(), point.x(), area.ymax()) :
                    new RectHV(area.xmin(), area.ymin(), area.xmax(), point.y());
        }

        private RectHV getRightSubNodeArea(RectHV area) {
            return vertical ?
                    new RectHV(point.x(), area.ymin(), area.xmax(), area.ymax()) :
                    new RectHV(area.xmin(), point.y(), area.xmax(), area.ymax());
        }
    }

}
