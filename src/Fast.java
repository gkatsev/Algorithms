import java.util.Arrays;

/**
 * User: Gary Katsevman
 * Date: 9/8/12
 * Time: 5:58 PM
 */
public class Fast {
    private static Point findLeast(Point po, Point[] points, int lo, int hi) {
        Point p = po;
        Point t;
        for (int i = lo; i <= hi; i++) {
            t = points[i];
            if (p.compareTo(t) < 0) { p = t; }
        }
        return p;
    }
    private static Point findMost(Point po, Point[] points, int lo, int hi) {
        Point p = po;
        Point t;
        for (int i = lo; i <= hi; i++) {
            t = points[i];
            if (p.compareTo(t) > 0) { p = t; }
        }
        return p;
    }

    private static void printPoints(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            StdOut.print(points[i].toString());
            StdOut.print(" -> ");
        }
        StdOut.print(points[points.length - 1].toString());
        StdOut.println();
    }
    private static void printPoints(Point p, Point[] points, int lo, int hi) {
        Point[] pts = new Point[hi - lo + 1];
        pts[0] = p;
        for (int i = 1; i < pts.length; i++) {
            pts[i] = points[lo + i - 1];
        }

        printPoints(pts);
    }

    private static void drawPoints(Point p, Point[] points, int lo, int hi) {
        findLeast(p, points, lo, hi).drawTo(findMost(p, points, lo, hi));
    }

    private static void doStuff(Point p, Point[] points, int segLo, int segHi) {
        drawPoints(p, points, segLo, segHi);
        printPoints(p, points, segLo, segHi + 1);
    }

    private static void findEqualSlopes(Point p, Point[] points, int lo, int hi) {
        double slope;
        double tempSlope;
        int segLo = lo;
        int segHi = lo;

        if (hi - lo < 3) {
            return;
        }

        for (int i = lo; i < hi - 1; i++) {
            slope = p.slopeTo(points[i]);
            tempSlope = p.slopeTo(points[i + 1]);

            if (tempSlope == slope) {
                segHi++;
                if (i + 1 == hi - 1) {
                    if ((segHi - segLo) >= 2) {
                        doStuff(p, points, segLo, segHi);
                    }
                }
            } else {
                if ((segHi - segLo) >= 2) {
                    doStuff(p, points, segLo, segHi);
                }
                segLo = ++segHi;
            }
        }
    }

    private static void iterate(Point[] points) {
        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            Point p = points[i];
            p.draw();
            int lo = i+1;
            int hi = points.length;
            Arrays.sort(points, lo, hi, p.SLOPE_ORDER);
            //printSlopes(p, points, lo, hi);
            findEqualSlopes(p, points, lo, hi);
        }
    }
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

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
