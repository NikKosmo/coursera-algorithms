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
            RectHV leftSubNodeArea = getLeftSubNodeArea(node, area);
            if (leftSubNodeArea.intersects(rect)) {
                addPointsInRange(node.leftNode, rect, leftSubNodeArea, result);
            }
        }
        if (node.rightNode != null) {
            RectHV rightSubNodeArea = getRightSubNodeArea(node, area);
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
        return nearest(p, root, getBasicPlain());
    }

    private RectHV getBasicPlain() {
        return new RectHV(0, 0, maxX, maxY);
    }

    private Point2D nearest(Point2D p, Node node, RectHV area) {
        double distanceToNodePoint = p.distanceSquaredTo(node.point);
        RectHV rightSubNodeArea = getRightSubNodeArea(node, area);
        RectHV leftSubNodeArea = getLeftSubNodeArea(node, area);
        double squaredDistanceToRightNode = rightSubNodeArea.distanceSquaredTo(p);
        double squaredDistanceToLeftNode = leftSubNodeArea.distanceSquaredTo(p);
        Node closerNode;
        double closerNodeDistance;
        RectHV closerNodeArea;
        Node furtherNode;
        double furtherNodeDistance;
        RectHV furtherNodeArea;
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
        if (closerNode != null && distanceToNodePoint > closerNodeDistance) {
            Point2D nearestFromCloserNode = nearest(p, closerNode, closerNodeArea);
            double distanceToNearestFromNode = p.distanceSquaredTo(nearestFromCloserNode);
            if (furtherNode != null && distanceToNearestFromNode > furtherNodeDistance) {
                Point2D nearestFromFurtherNode = nearest(p, furtherNode, furtherNodeArea);
                double distanceToAnother = p.distanceSquaredTo(nearestFromFurtherNode);
                double distanceToNearestFromSubNodes = Math.min(distanceToNearestFromNode, distanceToAnother);
                Point2D nearestFromSubNodes = distanceToNearestFromNode < distanceToAnother ?
                        nearestFromCloserNode :
                        nearestFromFurtherNode;
                return distanceToNearestFromSubNodes < distanceToNodePoint ?
                        nearestFromSubNodes :
                        node.point;
            } else {
                return distanceToNearestFromNode < distanceToNodePoint ?
                        nearestFromCloserNode :
                        node.point;
            }
        } else if (furtherNode != null && distanceToNodePoint > furtherNodeDistance) {
            Point2D nearestFromNode = nearest(p, furtherNode, furtherNodeArea);
            double distanceToNearestFromNode = p.distanceSquaredTo(nearestFromNode);
            return distanceToNearestFromNode < distanceToNodePoint ?
                    nearestFromNode :
                    node.point;
        }
        return node.point;
    }

    private RectHV getLeftSubNodeArea(Node node, RectHV area) {
        return node.vertical ?
                new RectHV(area.xmin(), area.ymin(), node.point.x(), area.ymax()) :
                new RectHV(area.xmin(), area.ymin(), area.xmax(), node.point.y());
    }

    private RectHV getRightSubNodeArea(Node node, RectHV area) {
        return node.vertical ?
                new RectHV(node.point.x(), area.ymin(), area.xmax(), area.ymax()) :
                new RectHV(area.xmin(), node.point.y(), area.xmax(), area.ymax());
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

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

}
