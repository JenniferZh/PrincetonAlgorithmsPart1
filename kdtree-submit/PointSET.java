/* *****************************************************************************
 *  Name: Jennifer
 *  Date: 2018-11-15
 *  Description: kdTree
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {

    private SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (!contains(p)) points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.contains(p)) return true;
        return false;
    }

    public void draw() {
        for (Point2D p: points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> containedPoints = new Queue<>();
        for (Point2D p: points) {
            if (rect.contains(p)) containedPoints.enqueue(p);
        }
        return containedPoints;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest = null;
        double distance = Double.MAX_VALUE;
        for (Point2D point: points) {
            if (p.distanceSquaredTo(point) < distance) {
                nearest = point;
                distance = p.distanceSquaredTo(point);
            }
        }
        return nearest;
    }

    public static void main(String[] args) {

    }
}
