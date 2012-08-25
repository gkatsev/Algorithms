/**
 * User: Gary Katsevman
 * Date: 8/24/12
 * Time: 11:01 PM
 */

public class PercolationStats {

    private double[] x;
    private int N;
    private int T;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats (int N, int T) {
        if (N <= 0 || T <=0) throw new IllegalArgumentException("either input was zero or below");
        this.N = N;
        this.T = T;

        x = new double[T];

        run();
    }

    private void run() {
        Percolation p;

        for (int k = 0; k < T; k++) {
            p = new Percolation(N);

            double t = 0;
            while(true) {
                int[] site = getSite();
                if(!p.isOpen(site[0], site[1])) {
                    p.open(site[0], site[1]);
                    t++;
                    if(p.percolates()) break;
                }

            }
            x[k] = t/(N*N);
        }
    }

    private int[] getSite() {
        int[] site = new int[2];
        site[0] = StdRandom.uniform(1, N + 1);
        site[1] = StdRandom.uniform(1, N + 1);

        return site;
    }

    // sample mean of percolation threshold
    public double mean () {
        double mean = 0;
        for (int i = 0; i < x.length; i++) {
            mean += x[i];
        }
        return mean / T;
    }

    // sample standard deviation of percolation threshold
    public double stddev () {
        double mean = mean();
        double num = 0;
        double denom = T -1;
        for (int i = 0; i < x.length; i++) {
            num += Math.pow(x[i] - mean, 2);
        }
        return num/denom;
    }

    private double[] confidenceInterval() {
        double[] ci = new double[2];
        double mean = mean();
        double stddev = stddev();
        double sqrtT = Math.sqrt(T);
        double alpha = 1.956;
        double error = (alpha * stddev) / sqrtT;
        ci[0] = mean - error;
        ci[1] = mean + error;
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
