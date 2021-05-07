import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Comparator;

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
                    contains(point, node.leftNode);
                } else {
                    contains(point, node.rightNode);
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
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        checkNotNull(p);
        if (isEmpty()) {
            return null;
        }
        return p;
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
