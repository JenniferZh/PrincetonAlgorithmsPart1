/* *****************************************************************************
 *  Name: Jennifer
 *  Date: 2018-11-6
 *  Description: kdtree
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

public class KdTree {
    private TreeNode root = null;
    private int size = 0;

    private Point2D nearest;
    private double distance;

    private class TreeNode {
        private final Point2D key;
        private final RectHV rect;
        private TreeNode leftdown;
        private TreeNode rightup;
        private final boolean isVertical;

        TreeNode(Point2D point, RectHV rec, boolean vertical) {
            key = point;
            rect = rec;
            isVertical = vertical;
        }

    }

    public KdTree() {

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        TreeNode current = root;
        while (current != null) {
            if (p.equals(current.key)) return true;

            if (current.isVertical) {
                if (p.x() < current.key.x()) {
                    current = current.leftdown;
                } else {
                    current = current.rightup;
                }
            } else {
                if (p.y() < current.key.y()) {
                    current = current.leftdown;
                } else {
                    current = current.rightup;
                }
            }
        }
        return false;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        RectHV rect = new RectHV(0, 0, 1, 1);
        root = put(root, p, true, rect);
    }

    private TreeNode put(TreeNode node, Point2D p, boolean vertical, RectHV rect) {
        if (node == null) {
            size++;
            return new TreeNode(p, rect, vertical);
        }
        if (node.key.compareTo(p) == 0) return node;
        if (node.isVertical) {
            if (p.x() < node.key.x()) {
                //左下不变，右上左移
                RectHV rectleft = new RectHV(rect.xmin(), rect.ymin(), node.key.x(), rect.ymax());
                node.leftdown = put(node.leftdown, p, false, rectleft);
            } else {
                //右上不变，左下右移
                RectHV rectright = new RectHV(node.key.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.rightup = put(node.rightup, p, false, rectright);
            }
        } else {
            if (p.y() < node.key.y()) {
                //左下不变，右上下移
                RectHV rectdown = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.key.y());
                node.leftdown = put(node.leftdown, p, true, rectdown);
            } else {
                //右上不变，左下上移
                RectHV rectup = new RectHV(rect.xmin(), node.key.y(), rect.xmax(), rect.ymax());
                node.rightup = put(node.rightup, p, true, rectup);
            }
        }
        return node;
    }

    public void draw() {
        drawhelp(root);
    }

    private void drawhelp(TreeNode node) {
        if (node == null) return;

        StdDraw.setPenRadius(0.005);
        if (node.isVertical) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(node.key.x(), node.rect.ymin(), node.key.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(node.rect.xmin(), node.key.y(), node.rect.xmax(), node.key.y());
        }

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(Color.BLACK);
        node.key.draw();

        drawhelp(node.rightup);
        drawhelp(node.leftdown);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range_help(root, rect, queue);
        return queue;
    }

    private void range_help(TreeNode node, RectHV rect, Queue<Point2D> result) {
        if (node == null) return;
        if (rect.contains(node.key)) {
            result.enqueue(node.key);
        }
        if (node.leftdown != null && node.leftdown.rect.intersects(rect)) {
            range_help(node.leftdown, rect, result);
        }
        if (node.rightup != null && node.rightup.rect.intersects(rect)) {
            range_help(node.rightup, rect, result);
        }
    }

    public Point2D nearest(Point2D p) {
        // init
        nearest = null;
        distance = Double.MAX_VALUE;
        if (p == null) throw new IllegalArgumentException();
        nearest_help(root, p);
        return nearest;
    }

    private void nearest_help(TreeNode node, Point2D point) {
        if (node == null) return;
        if (point.distanceSquaredTo(node.key) < distance) {
            distance = point.distanceSquaredTo(node.key);
            nearest = node.key;
        }
        // to pass the autograder, we have to traserve the point's part first, and the other side later
        TreeNode first = null;
        TreeNode second = null;

        if (node.isVertical) {
            if (point.x() < node.key.x()) {
                first = node.leftdown;
                second = node.rightup;
            } else {
                first = node.rightup;
                second = node.leftdown;
            }
        } else {
            if (point.y() < node.key.y()) {
                first = node.leftdown;
                second = node.rightup;
            } else {
                first = node.rightup;
                second = node.leftdown;
            }
        }

        if (first != null) {
            if (distance >= first.rect.distanceSquaredTo(point)) {
                nearest_help(first, point);
            }
        }
        if (second != null) {
            if (distance >= second.rect.distanceSquaredTo(point)) {
                nearest_help(second, point);
            }
        }
    }




    public static void main(String[] args) {

    }
}
