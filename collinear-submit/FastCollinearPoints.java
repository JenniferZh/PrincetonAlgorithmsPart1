/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {

    private int lineCount = 0;
    private LineSegment[] lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        Point[] tPoints = points.clone();
        Arrays.sort(tPoints);
        for (int i = 0; i < tPoints.length - 1; i++) {
            if (tPoints[i].compareTo(tPoints[i+1]) == 0) throw new IllegalArgumentException();
        }

        List<LineSegment> result = new ArrayList<>();


        for (int i = 0; i < points.length; i++)
        {
            Point base = points[i];
            if (base == null) throw new IllegalArgumentException();

            Comparator<Point> comparator = base.slopeOrder();
            Point[] tmpPoints = Arrays.copyOf(points, points.length);
            Arrays.sort(tmpPoints, comparator);

            int cur = 1;
            int j;
            double rate = Double.NEGATIVE_INFINITY;
            for (j = 1; j < points.length; j++)
            {

                if (base.slopeTo(tmpPoints[j]) != rate) {

                    if (j - cur >= 3) {
                        List<Point> tmpRes = new ArrayList<>();
                        tmpRes.add(base);
                        for (int k = cur; k < j; k++) tmpRes.add(tmpPoints[k]);
                        Collections.sort(tmpRes);

                        LineSegment newLine = new LineSegment(tmpRes.get(0), tmpRes.get(tmpRes.size()-1));

                        if(tmpRes.get(0).compareTo(base) == 0)
                            result.add(newLine);
                    }

                    rate = base.slopeTo(tmpPoints[j]);
                    cur = j;
                }

            }

            if (j - cur >= 3) {
                List<Point> tmpRes = new ArrayList<>();
                tmpRes.add(base);
                for (int k = cur; k < j; k++) tmpRes.add(tmpPoints[k]);
                Collections.sort(tmpRes);

                LineSegment newLine = new LineSegment(tmpRes.get(0), tmpRes.get(tmpRes.size()-1));
                if (tmpRes.get(0).compareTo(base) == 0)
                    result.add(newLine);
            }
        }

        lineCount = result.size();
        lines = new LineSegment[lineCount];
        int p = 0;
        for (LineSegment res: result) {
            lines[p++] = res;
        }

    }
    public int numberOfSegments() {
        return lineCount;
    }
    public LineSegment[] segments() {
        return lines;
    }

    public static void main(String[] args) {
        // read the n points from a file


    }
}
