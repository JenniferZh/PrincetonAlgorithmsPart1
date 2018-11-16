/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private int lineCount;
    private ArrayList<LineSegment> lines = new ArrayList<>();
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }

        Point[] tPoints = points.clone();
        Arrays.sort(tPoints);
        for (int i = 0; i < tPoints.length - 1; i++) {
            if (tPoints[i].compareTo(tPoints[i+1]) == 0) throw new IllegalArgumentException();
        }

        Point[] arr = new Point[4];
        for (int i = 0; i < points.length - 3; i++)
        {
            for (int j = i+1; j < points.length - 2; j++)
            {
                for (int k = j+1; k < points.length - 1; k++)
                {
                    for (int t = k+1; t < points.length; t++)
                    {

                        arr[0] = points[i];
                        arr[1] = points[j];
                        arr[2] = points[k];
                        arr[3] = points[t];

                        Arrays.sort(arr);

                        if (arr[0].slopeTo(arr[1]) == arr[1].slopeTo(arr[2]) && arr[1].slopeTo(arr[2]) == arr[2].slopeTo(arr[3]))
                        {
                            LineSegment line = new LineSegment(arr[0], arr[3]);
                            lines.add(line);
                            lineCount++;
                        }

                    }
                }
            }
        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return lineCount;
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] res = new LineSegment[lineCount];
        for(int i = 0; i < lineCount; i++)
            res[i] = lines.get(i);
        return res;
    }

    public static void main(String[] args) {
        Point[] arr = new Point[2];
        // BruteCollinearPoints B = new BruteCollinearPoints(arr);

    }
}
