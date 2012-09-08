/**
 * User: Gary Katsevman
 * Date: 9/8/12
 * Time: 12:40 AM
 */
public class Brute {
    private static boolean collinear(Point a, Point b, Point c) {
        double sAB = a.slopeTo(b);
        double sAC = a.slopeTo(c);
        double sBC = b.slopeTo(c);

        return sAB == sAC && sAB == sBC;
    }

    private static void printPoints(Point a, Point b, Point c, Point d) {
        StdOut.print(a.toString());
        StdOut.print(" -> ");
        StdOut.print(b.toString());
        StdOut.print(" -> ");
        StdOut.print(c.toString());
        StdOut.print(" -> ");
        StdOut.print(d.toString());
        StdOut.println();
    }

    private static Point findLeast(Point[] points) {
        Point p = points[0];
        Point t;
        for (int i = 1; i < points.length; i++) {
            t = points[i];
           if (p.compareTo(t) < 0) { p = t; }
        }
        return p;
    }
    private static Point findMost(Point[] points) {
        Point p = points[0];
        Point t;
        for (int i = 1; i < points.length; i++) {
            t = points[i];
            if (p.compareTo(t) > 0) { p = t; }
        }
        return p;
    }

    private static void drawPoints(Point a, Point b, Point c, Point d) {
        Point[] points = new Point[] {a, b, c, d};
        findLeast(points).drawTo(findMost((points)));
    }

    private static void iterate(Point[] points) {
        int n = points.length;
        boolean first = true;
        for (int i = 0; i < n; i++) {
            Point a = points[i];
            if (first) { a.draw(); }
            for (int j = i + 1; j < n; j++) {
                Point b = points[j];
                if (first) { b.draw(); }
                for (int k = j + 1; k < n; k++) {
                    Point c = points[k];
                    if (first) { c.draw(); }
                    for (int l = k + 1; l < n; l++) {
                        Point d = points[l];
                        if (first) { d.draw(); first = false; }
                        if (collinear(a, b, c)) {
                            if (collinear(b, c, d)) {
                                printPoints(a, b, c, d);
                                drawPoints(a, b, c, d);
                            }
                        }
                    }
                }
            }
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
