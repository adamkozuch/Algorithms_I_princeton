import java.lang.String;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
  private final int [][] blocks; 
  private int freeBlockI, freeBlockJ;

  public Board(int[][] blocks) {
    this.blocks = new int[blocks.length][blocks.length];
      for (int i = 0; i < blocks.length; i++) {
        for (int j = 0; j < blocks.length; j++) {
          this.blocks[i][j] = blocks[i][j];

        }
      }

    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blocks[i][j] == 0) {
          freeBlockI = i;
          freeBlockJ = j;

        }
      }
    }
  }

  public int dimension() {
    return blocks.length;
  }

  public int hamming() { 
    int hammingMetric = 0;
    int blockValue = 1;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        if (blockValue != Math.pow(blocks.length, 2)) {
          if (blocks[i][j] != blockValue) {
            hammingMetric++;
          }
        }
        blockValue++;
      }
    }
    return hammingMetric;
  }

  public int manhattan() {
    int manhattanMetric = 0;
    for (int i = 0; i < blocks.length; i++) {
      for (int j = 0; j < blocks.length; j++) {
        int currentValue = blocks[i][j];
        if (currentValue != 0) {
          int goalI = (currentValue - 1) / blocks.length;
          int goalJ = currentValue - goalI * blocks.length -1;
          int distance = Math.abs(i - goalI) + Math.abs(j - goalJ);
          manhattanMetric += distance;
        }
      }
    }
    return manhattanMetric;
  }


  public boolean isGoal() {
    return hamming() == 0;
  }

  public Board twin() {
    int[][] arr = new int[dimension()][dimension()];
    Board twinBoard;
    for (int i = 0; i < arr.length; i++) {
      for (int j = 0; j < arr.length; j++) {
        arr[i][j] = blocks[i][j];

      }
    }
    int x = 0, y = 0;
    int element = -1; 
    while (element == -1 || element == 0) {
      x = StdRandom.uniform(dimension());
      y = StdRandom.uniform(dimension());
      element = arr[x][y];
    }


    if (x > 0 && arr[x -1][y] != 0) {
      swap(arr, x, x - 1, y, y );
    } else if (y > 0 && arr[x][y-1] != 0) { 
      swap(arr, x, x, y, y - 1);
    } else if (x < arr.length -1 && arr[x + 1][y] != 0) {
      swap(arr, x, x + 1, y, y);
    } else if (y < arr.length -1 && arr[x][y + 1] != 0) {
      swap(arr, x, x , y, y + 1);
    }

    twinBoard = new Board(arr);

    return twinBoard;

  }

  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board boardY = (Board)y;
    return boardY.toString().equals(this.toString());
  }

  public Iterable<Board> neighbors() {
    boolean left =false, right=false, up = false, down = false;


    if (freeBlockI == 0) {
      left = true;
    }

    if (freeBlockI == dimension()-1) {
      right = true;
    }

    if (freeBlockJ == 0) {
      up = true;
    }
    if (freeBlockJ == dimension() - 1) {
      down = true;
    }

    List<Board> neighborsList = new ArrayList<Board>();

    while (!left || !right || !up || !down) {

      if (!left) {
        int[][] arr = new int [dimension()][dimension()]; 
        for (int i = 0; i < dimension(); i++) {
          for (int j = 0; j < dimension(); j++) {
            arr[i][j] = blocks[i][j];
          }
        }
        swap(arr, freeBlockI, freeBlockI - 1, freeBlockJ, freeBlockJ);
        left = true;
        Board b = new Board(arr);
        neighborsList.add(b);
      }

      if (!right) {
        int[][] arr = new int [dimension()][dimension()]; 
        for (int i = 0; i < dimension(); i++) {
          for (int j = 0; j < dimension(); j++) {
            arr[i][j] = blocks[i][j];
          }
        }
        swap(arr, freeBlockI, freeBlockI + 1, freeBlockJ, freeBlockJ);
        right = true;
        Board b = new Board(arr);
        neighborsList.add(b);
      }

      if (!up) {
        int[][] arr = new int [dimension()][dimension()]; 
        for (int i = 0; i < dimension(); i++) {
          for (int j = 0; j < dimension(); j++) {
            arr[i][j] = blocks[i][j];
          }
        }
        up = true;
        swap(arr, freeBlockI, freeBlockI, freeBlockJ, freeBlockJ - 1);
        Board b = new Board(arr);
        neighborsList.add(b);
      }

      if (!down) {
        int[][] arr = new int [dimension()][dimension()]; 
        for (int i = 0; i < dimension(); i++) {
          for (int j = 0; j < dimension(); j++) {
            arr[i][j] = blocks[i][j];
          }
        }
        down = true;
        swap(arr, freeBlockI, freeBlockI, freeBlockJ, freeBlockJ + 1);
        Board b = new Board(arr);
        neighborsList.add(b);
      }
    }
    return neighborsList;
  }

  private void swap(int[][] arr, int i1, int i2, int j1, int j2) {
    int tmp = arr[i1][j1]; 
    arr[i1][j1] = arr[i2][j2]; 
    arr[i2][j2] = tmp;
  }


  public String toString() {               // string representation of this board (in the output format specified below)
    StringBuilder s = new StringBuilder();
    s.append(dimension() + "\n");
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        s.append(String.format("%2d ",blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  public static void main(String[] args) { // unit tests (not graded)
    int[][] arr = {{8,1,3}, {4,0,2}, {7,6,5}};
    int[][] arr1 = {{8,1,3}, {4,0,2}, {7,6,5}};
    int [][] twinarr = {{3,0}, {1,2}};
    Board b = new Board(arr);
    Board c = new Board(arr1);
    Board twinBoard = new Board(twinarr);
    Board second = twinBoard.twin();
    System.out.println(second);
    assert b.hamming() == 5;
    assert b.manhattan() == 10;
    assert b.equals(c);
    //for(Board i: b.neighbors()) {
      //System.out.println(i);
    //}
  }
}
