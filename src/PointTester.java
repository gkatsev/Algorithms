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
    private void assertZero(double actual) {
        double expected = 0;
        if (expected == actual) { StdOut.print("."); prev = false; }
        else {
            if (!prev) { StdOut.print("\n"); }
            StdOut.println("Expected " + expected + ", was " + actual);
            prev = true;
        }
    }
    private void assertPositive(double actual) {
        double expected = 0;
        if (expected < actual) { StdOut.print("."); prev = false; }
        else {
            if (!prev) { StdOut.print("\n"); }
            StdOut.println("Expected positive, was " + actual);
            prev = true;
        }
    }
    private void assertNegative(double actual) {
        double expected = 0;
        if (expected > actual) { StdOut.print("."); prev = false; }
        else {
            if (!prev) { StdOut.print("\n"); }
            StdOut.println("Expected negative, was " + actual);
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

    public void testSlopeOrderComparator() {
        Point originPoint = new Point(1, 1);

        // same point
        assertZero(originPoint.SLOPE_ORDER.compare(new Point(1, 1), new Point(1, 1)));

        // Same slope (straight line)
        assertZero(originPoint.SLOPE_ORDER.compare(new Point(2, 2), new Point(5, 5)));

        // First slope is smaller
        assertNegative(originPoint.SLOPE_ORDER.compare(new Point(5, 10), new Point(5, 25)));

        // Second slope is smaller
        assertPositive(originPoint.SLOPE_ORDER.compare(new Point(5, 25), new Point(5, 10)));

        // first slope is horizontal
        assertNegative(originPoint.SLOPE_ORDER.compare(new Point(5, 1), new Point(5, 5)));

        // second slope is horizontal
        assertPositive(originPoint.SLOPE_ORDER.compare(new Point(5, 5), new Point(5, 1)));

        // first slope is vertical
        assertPositive(originPoint.SLOPE_ORDER.compare(new Point(1, 5), new Point(5, 5)));

        // second slope is vertical
        assertNegative(originPoint.SLOPE_ORDER.compare(new Point(5, 5), new Point(1, 5)));

        // both slopes vertical
        assertZero(originPoint.SLOPE_ORDER.compare(new Point(1, 5), new Point(1, 8)));

        // both slopes is horizontal
        assertZero(originPoint.SLOPE_ORDER.compare(new Point(5, 1), new Point(8, 1)));
    }

    public static void main(String[] args) {
        PointTester pt = new PointTester();
        StdOut.println("\nTest compareTo");
        pt.testCompareTo();
        StdOut.println("\nTest slopeTo");
        pt.testSlopeTo();
        StdOut.println("\nTest comparator");
        pt.testSlopeOrderComparator();
    }
}
