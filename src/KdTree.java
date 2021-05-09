import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;

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
        return Node.of(point, !vertical);
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
        return nearest(p, root);
    }

    // TODO: 08.05.2021  
    private Point2D nearest(Point2D p, Node node) {
        double distance = p.distanceSquaredTo(node.point);
        Node closerNode = node.leftNode;
        Node furtherNode = node.rightNode;
        if (closerNode != null && distance > p.distanceSquaredTo(closerNode.point)) {
            Point2D nearestFromNode = nearest(p, closerNode);
            double distanceToOne = p.distanceSquaredTo(nearestFromNode);
            if (furtherNode != null && distanceToOne > p.distanceSquaredTo(furtherNode.point)) {
                Point2D nearestFromAnother = nearest(p, furtherNode);
                double distanceToAnother = p.distanceSquaredTo(nearestFromAnother);
                return distanceToOne < distanceToAnother ?
                        nearestFromNode :
                        nearestFromAnother;
            }
        } else if (furtherNode != null && distance > p.distanceSquaredTo(furtherNode.point)) {
            return nearest(p, furtherNode);
        }
        return node.point;
    }

    private void checkNotNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException();
        }
    }

    private static class Node implements Comparable<Node> {

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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public int compareTo(Node that) {
            return vertical ?
                    Comparator.comparingDouble(Point2D::x).compare(this.point, that.point) :
                    Comparator.comparingDouble(Point2D::y).compare(this.point, that.point);
        }
    }

}
