/**
 * Created with IntelliJ IDEA.
 * User: mycroft
 * Date: 8/24/12
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class PercolationStats {

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats (int N, int T) {
    }

    // sample mean of percolation threshold
    public double mean () {
        return 0;
    }

    // sample standard deviation of percolation threshold
    public double stddev () {
        return 0;
    }

    private double[] confidenceInterval() {
        double[] ci = new double[2];
        ci[0] = 0;
        ci[1] = 1;
        return ci;
    }

    private void print(){
        StringBuilder sb = new StringBuilder();

        sb.append("mean");
        sb.append("\t\t\t\t\t= ");
        sb.append(mean());
        sb.append("\n");

        sb.append("stddev");
        sb.append("\t\t\t\t\t= ");
        sb.append(stddev());
        sb.append("\n");

        sb.append("95% confidence interval");
        sb.append("\t= ");
        double[] ci = confidenceInterval();
        sb.append(ci[0]);
        sb.append(", ");
        sb.append(ci[1]);

        StdOut.println(sb.toString());
    }

    // test client, described below
    public static void main (String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);
        ps.print();
    }
}
