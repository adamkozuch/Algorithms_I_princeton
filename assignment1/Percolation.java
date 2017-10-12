import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
   private boolean [][] grid;
   private int openSites = 0;
   private final WeightedQuickUnionUF qu;
   private final int n;
   private final int topSite;
   private final int bottomSite;

   public Percolation(int n) { 
     if (n <= 0) {
       throw new java.lang.IllegalArgumentException();
     }

     this.n = n;
     this.grid = new boolean[n + 1][n + 1];
     this.qu = new WeightedQuickUnionUF(n*n + 2);
     this.topSite = n*n;
     this.bottomSite = n*n + 1;

     for (int i = 1; i <= n; i++) {
       qu.union(topSite, xTo1D(1, i));
     }

     for (int i = 1; i <= n; i++) {
       qu.union(bottomSite, xTo1D(n, i));
     }
   }

   private int xTo1D(int row, int col) {
     return n * (row - 1) + col - 1;
   }

   private boolean validate(int row, int col) {
     if (row <= 0 || col <= 0 || row > n || col > n) {
       return false;
     }
     return true;
   }

   public void open(int row, int col) {
     if (!validate(row, col)) {
       throw new java.lang.IllegalArgumentException();
     }

     if (!grid[row][col]) {
       grid[row][col] = true;
       openSites++;

       if (row >= 2 && grid[row - 1][col]) {
         qu.union(xTo1D(row, col), xTo1D(row - 1, col));
       }

       if (row < n && grid[row + 1][col]) {
         qu.union(xTo1D(row, col), xTo1D(row + 1, col));
       }

       if (col >= 2 && grid[row][col - 1]) {
         qu.union(xTo1D(row, col), xTo1D(row, col - 1));
       }

       if (col < n && grid[row][col + 1]) {
         qu.union(xTo1D(row, col), xTo1D(row, col + 1));
       }
     }
   }

   public boolean isOpen(int row, int col) { 
     if (!validate(row, col)) {
       throw new java.lang.IllegalArgumentException();
     }

     return grid[row][col];
   }

   public boolean isFull(int row, int col) {

     if (!validate(row, col)) {
       throw new java.lang.IllegalArgumentException();
     }

     if (isOpen(row, col))
       return qu.connected(topSite, xTo1D(row, col));
     return false;
   }
 
   public int numberOfOpenSites() { 
     return openSites;
   }

   public boolean percolates() {
     if (n == 1) {
       return grid[1][1];
     } else if (n == 2) {
       if ((grid[1][1] && grid[2][1]) || (grid[1][2] && grid[2][2])) return true;
       return false;
     } else {
       return qu.connected(topSite, bottomSite);
     }
   }
}

