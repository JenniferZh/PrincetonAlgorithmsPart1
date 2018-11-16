/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final double[] ratios;
    private final int trialCount;
    private final static double z = 1.96;
    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException();

        ratios = new double[trials];
        trialCount = trials;

        int trial = 0;
        while (trial < trials)
        {
            Percolation percolation = new Percolation(n);
            int siteCount = 0;
            while (!percolation.percolates())
            {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);

                if (!percolation.isOpen(i, j)) {
                    percolation.open(i, j);
                    siteCount++;
                }
            }
            ratios[trial++] = (double) siteCount / (n*n);
        }

    }

    /**
     * Sample mean of percolation threshold.
     */
    public double mean() {
        return StdStats.mean(ratios);
    }

    /**
     * Sample standard deviation of percolation threshold.
     */
    public double stddev() {
        return StdStats.stddev(ratios);
    }

    /**
     * Returns lower bound of the 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - ((z * stddev()) / Math.sqrt(trialCount));
    }

    /**
     * Returns upper bound of the 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + ((z * stddev()) / Math.sqrt(trialCount));
    }
    public static void main(String[] args) {
        int numSites = Integer.parseInt(args[0]);
        int numTrials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(numSites, numTrials);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
