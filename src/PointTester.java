public class PointTester {
    private boolean prev = false;
    private void assertEquals(double expected, double actual) {
        if (expected == actual) { StdOut.print("."); prev = false; }
        else {
            if (!prev) { StdOut.print("\n"); }
            StdOut.println("Expected " + expected + ", was " + actual);
            prev = true;
        }
    }

    public void testCompareTo() {
        // Equal
        assertEquals(0, new Point(0, 1).compareTo(new Point(0, 1)));
        // Compare Y
        assertEquals(-1, new Point(1, 1).compareTo(new Point(1, 2)));
        assertEquals(1, new Point(1, 2).compareTo(new Point(1, 1)));
        // Compare X
        assertEquals(-1, new Point(1, 1).compareTo(new Point(2, 1)));
        assertEquals(1, new Point(2, 1).compareTo(new Point(1, 1)));

    }

    public void testSlopeTo() {
        // Vertical Line
        assertEquals(Double.POSITIVE_INFINITY, (new Point(8, 5)).slopeTo(new Point(8, 0)));
        // Line Segment
        assertEquals(Double.NEGATIVE_INFINITY, (new Point(5, 5)).slopeTo(new Point(5, 5)));
        // Horizontal Line
        assertEquals(0, (new Point(0, 5)).slopeTo(new Point(8, 5)));
        assertEquals(0, (new Point(8, 5)).slopeTo(new Point(0, 5)));
        // Negative
        assertEquals(-2, (new Point(1, 2)).slopeTo(new Point(3, -2)));
        // Normal
        assertEquals(2.25, (new Point(1, 1)).slopeTo(new Point(5, 10)));
        assertEquals(6.0, (new Point(1, 1)).slopeTo(new Point(5, 25)));
    }

//    public void testSlopeOrderComparator() {
//        Point originPoint = new Point(1, 1);
//
//        // Same slope (straight line)
//        assertEquals(0, originPoint.SLOPE_ORDER.compare(new Point(2, 2), new Point(5, 5)));
//
//        // First slope is smaller
//        assertEquals(-1, originPoint.SLOPE_ORDER.compare(new Point(5, 10), new Point(5, 25)));
//
//        // Second slope is smaller
//        assertEquals(+1, originPoint.SLOPE_ORDER.compare(new Point(5, 25), new Point(5, 10)));
//    }

    public static void main(String[] args) {
        PointTester pt = new PointTester();
        StdOut.println("\nTest compareTo");
        pt.testCompareTo();
        StdOut.println("\nTest slopeTo");
        pt.testSlopeTo();
    }
}
