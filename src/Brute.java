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

    private static void drawPoints(Point a, Point b, Point c, Point d) {
        a.drawTo(d);
    }

    private static void iterate(Point[] points) {
        int n = points.length;
        for (int i = 0; i < n; i++) {
            Point a = points[i];
            a.draw();
            for (int j = 0; j < n; j++) {
                Point b = points[j];
                b.draw();
                for (int k = 0; k < n; k++) {
                    Point c = points[k];
                    c.draw();
                    for (int l = 0; l < n; l++) {
                        Point d = points[l];
                        d.draw();
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
