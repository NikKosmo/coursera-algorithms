import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int trials;
    private final double[] percolationTrials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.percolationTrials = new double[trials];
        int size = n * n;
        for (int i = 0; i < trials; i++) {
            percolationTrials[i] = (double) trial(n) / size;
        }
    }

    private int trial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open((int) (StdRandom.uniform() * n) + 1,
                             (int) (StdRandom.uniform() * n) + 1);
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationTrials);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationTrials);
    }

    public double confidenceLo() {
        return mean() - confidenceDeviation();
    }

    public double confidenceHi() {
        return mean() + confidenceDeviation();
    }

    private double confidenceDeviation() {
        return 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(gridSize, trials);
        System.out.println("mean = " + percolationStats.stddev());
        System.out.println("stddev = " + percolationStats.mean());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", "
                                   + percolationStats.confidenceHi() + "]");
    }
}
