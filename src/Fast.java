import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * User: Gary Katsevman
 * Date: 9/8/12
 * Time: 5:58 PM
 */
public class Fast {
    private static void findEqualSlopes(Point p, Point[] points, int lo, int hi) {
        int slope;
        for (int i = lo; i < hi; i++) {

        }
    }
    private static void printSlopes(Point p, Point[] points, int lo, int hi) {
        DecimalFormat dc = new DecimalFormat("0.00");
        for (int i = lo; i < hi; i++) {
            StdOut.print(dc.format(p.slopeTo(points[i])) + " ");
        }
        StdOut.println("\n");
    }
    private static void printPoints(Point[] points, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            StdOut.print(points[i].toString());
            StdOut.print(" -> ");
        }
        StdOut.print(points[hi].toString());
        StdOut.println("\n");
    }

    private static Point findLeast(Point[] points, int lo, int hi) {
        Point p = points[lo];
        Point t;
        for (int i = lo + 1; i <= hi; i++) {
            t = points[i];
            if (p.compareTo(t) < 0) { p = t; }
        }
        return p;
    }
    private static Point findMost(Point[] points, int lo, int hi) {
        Point p = points[lo];
        Point t;
        for (int i = lo + 1; i <= hi; i++) {
            t = points[i];
            if (p.compareTo(t) > 0) { p = t; }
        }
        return p;
    }

    private static void drawPoints(Point[] points, int lo, int hi) {
        findLeast(points, lo, hi).drawTo(findMost(points, lo, hi));
    }

    private static void iterate(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            int lo = i + 1;
            int hi = points.length;
            Arrays.sort(points, lo, hi, p.SLOPE_ORDER);
            printSlopes(p, points, lo, hi);
        }
        //Point p = points[0];
        //Arrays.sort(points, 1, points.length, p.SLOPE_ORDER);
        //printPoints(points, 0, points.length-1);
        //printSlopes(p, points, 1, points.length);
    }
    public static void main(String[] args) {
        //StdDraw.setXscale(0, 32768);
        //StdDraw.setYscale(0, 32768);

        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        iterate(points);
    }
}
