import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private double maxY = 0;
    private double maxX = 0;

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
        root = insert(p, root, false);
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
        return Node.of(point, !vertical);
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

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        checkNotNull(rect);
        if (isEmpty()) {
            return null;
        }
        List<Point2D> result = new ArrayList<>();
        addPointsInRange(root, rect, result);
        return result;
    }

    private void addPointsInRange(Node root, RectHV rect, List<Point2D> result) {
        if (root.leftNode != null) {

        }
        if (root.leftNode != null) {

        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        if (isEmpty()) {
            return null;
        }
        return nearest(p, root, new RectHV(0, 0, maxX, maxY));
    }

    // TODO: 08.05.2021  
    private Point2D nearest(Point2D p, Node node, RectHV area) {
        double distanceToNodePoint = p.distanceSquaredTo(node.point);

        double squaredDistanceToRightNode = node.getSquaredDistanceToRightNode(p);
        double squaredDistanceToLeftNode = node.getSquaredDistanceToLeftNode(p);
        Node closerNode;
        double closerNodeDistance;
        Node furtherNode;
        double furtherNodeDistance;
        if (squaredDistanceToRightNode < squaredDistanceToLeftNode) {
            closerNode = node.rightNode;
            closerNodeDistance = squaredDistanceToRightNode;
            furtherNode = node.leftNode;
            furtherNodeDistance = squaredDistanceToLeftNode;
        } else {
            closerNode = node.leftNode;
            closerNodeDistance = squaredDistanceToLeftNode;
            furtherNode = node.rightNode;
            furtherNodeDistance = squaredDistanceToRightNode;
        }
        if (closerNode != null && distanceToNodePoint > closerNodeDistance) {
            Point2D nearestFromNode = nearest(p, closerNode);
            double distanceToOne = p.distanceSquaredTo(nearestFromNode);
            if (furtherNode != null && distanceToOne > furtherNodeDistance) {
                Point2D nearestFromAnother = nearest(p, furtherNode);
                double distanceToAnother = p.distanceSquaredTo(nearestFromAnother);
                return distanceToOne < distanceToAnother ?
                        nearestFromNode :
                        nearestFromAnother;
            }
        } else if (furtherNode != null && distanceToNodePoint > furtherNodeDistance) {
            return nearest(p, furtherNode);
        }
        return node.point;
    }

    private double distanceToAreaSquared(Point2D p, Point2D nodePoint, Point2D subNodePoint) {
        return 0;
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Node {

        private Point2D point;
        private int size;
        private boolean vertical;
        private Node leftNode;
        private Node rightNode;

        public static Node of(Point2D point, boolean vertical) {
            Node node = new Node();
            node.point = point;
            node.vertical = vertical;
            return node;
        }

        public double getSquaredDistanceToRightNode(Point2D point) {
            return getSquaredDistanceToNode(point, rightNode);
        }

        public double getSquaredDistanceToLeftNode(Point2D point) {
            return getSquaredDistanceToNode(point, leftNode);
        }

        private double getSquaredDistanceToNode(Point2D point, Node subNode) {
            return subNode != null ?
                    0 :
                    Double.MAX_VALUE;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

}
