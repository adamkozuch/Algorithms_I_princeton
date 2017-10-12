import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private final double [] results;
  private final double trials;
  private final double mean;
  private final double stddev;

   public PercolationStats(int n, int trials) {
     if (n <= 0 || trials <= 0) {
      throw new java.lang.IllegalArgumentException();
     }
     results = new double[trials];
     this.trials = (double) trials;
     for (int i = 0; i < trials; i++) {
       int res = startTrial(n);
       results[i] = (double) res/(double) (n*n);
     }
     this.mean = StdStats.mean(results);
     this.stddev = StdStats.stddev(results);

   }

   private int startTrial(int n) {
     Percolation p = new Percolation(n);
     String []arr = new String[n*n];
     for (int i = 0; i < n; i++) {
       for (int j = 0; j < n; j++) {
         arr[i * n + j] = i + 1 + "#" + (j + 1);
       }
     }

     StdRandom.shuffle(arr);

     for (String s: arr) {
       String [] parts = s.split("#");
       int i = Integer.parseInt(parts[0]);
       int j = Integer.parseInt(parts[1]);
       if (!p.isOpen(i, j)) {
         p.open(i, j);
         if (p.percolates())
           return p.numberOfOpenSites();
       }
     }
     return -1;
   }

   public double mean() {
     return mean;
   }
   public double stddev() {
     return stddev;
   }
   public double confidenceLo() {
      return mean - (1.96 * stddev)/ Math.sqrt(trials);
   }
   public double confidenceHi() {
      return mean + (1.96 * stddev)/ Math.sqrt(trials);
   }

   public static void main(String[] args) {       // test client (described below)
     int t = Integer.parseInt(args[0]);
     int n = Integer.parseInt(args[1]);
     PercolationStats p = new PercolationStats(n, t);
     System.out.println("mean                    = " + p.mean());
     System.out.println("stddev                    = " + p.stddev());
     System.out.println("mean                    = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
   }
}

