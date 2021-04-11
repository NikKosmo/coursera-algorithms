import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private final int trials;
    private final double[] percolation;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.percolation = new double[n];
        int size = n * n;
        for (int i = 0; i < trials; i++) {
            percolation[i] = (double) trial(n) / size;
        }
    }

    private int trial(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            percolation.open((int) (StdRandom.uniform() * n),
                             (int) (StdRandom.uniform() * n));
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolation);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolation);
    }

    public double confidenceLo() {
        return mean() - stddev() / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + stddev() / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int gridSize = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(gridSize, trials);
        System.out.println(percolationStats.stddev());
        System.out.println(percolationStats.mean());
        System.out.println(percolationStats.confidenceHi());
        System.out.println(percolationStats.confidenceLo());
    }
}
