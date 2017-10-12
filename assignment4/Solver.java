import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;


public class Solver {
  private boolean solvable;
  private Stack<Board> solution; 

  private final Node bestSolution;

  private class ComparatorBoard implements Comparator<Node> {
    public int compare(Node b1, Node b2) {
      if (b1.getPriority() > b2.getPriority()) {
        return 1;
      } else if(b1.getPriority() < b2.getPriority()) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  private class Node {
    private final Board value;
    private final Node parent;
    private final int moves;
    private final int manhattan;

    public Node(Board value, Node parent, int moves) {
      this.moves = moves;
      this.parent = parent;
      this.value = value;
      manhattan = value.manhattan();
    }

    public Board returnBoard() {
      return value;
    }

    public Node returnParent() {
      return parent;
    }

    public int getMove() {
      return moves;
    }

    public int getPriority() {
      return moves + manhattan ;
    }
  }


  public Solver(Board initial) {
    if (initial == null) {
      throw new java.lang.IllegalArgumentException();
    }

    Node bestSolution = null;

    MinPQ<Node> queue = new MinPQ<Node>(new ComparatorBoard());

    if (initial.dimension() > 0) {
      queue.insert(new Node(initial, null, 0));
    }

    while(!queue.isEmpty()){
      Node current = queue.delMin();

      if (current.returnBoard().isGoal()) { 
        solvable = true;
        if (bestSolution == null || current.getPriority() < bestSolution.getPriority())
          bestSolution = current;
        if (queue.isEmpty())
          break;

        current = queue.delMin();
      }

      queue = recompute(current, bestSolution, queue);
    }

    this.bestSolution = bestSolution;
  }

  private boolean noRepeat(Board b, Node current) {
    Node n = current.returnParent();
    while (n != null ) { 
      if (n.returnBoard().equals(b)) {
        return false;
      }
      n = n.returnParent();
    }
    return true;
  }



  private MinPQ<Node> recompute(Node current, Node bestSolution, MinPQ<Node> queue) {
    for (Board b :current.returnBoard().neighbors()) {
      if (noRepeat(b, current)) {
        if (bestSolution == null  || current.getPriority() + 1 < bestSolution.getPriority()) {
            queue.insert(new Node(b, current, current.getMove() + 1));
        }
      }
    }
    return queue;
  }

  public boolean isSolvable() {
    return solvable;
  } 

  public int moves() {
    return bestSolution.getMove();
  } 

  public Iterable<Board> solution() {
    solution = new Stack<Board>();
    Node n = bestSolution;
    while (n != null) {
      solution.push(n.returnBoard());
      n = n.returnParent();
    }

    return solution;
  }

  public static void main(String [] args) {
    int [][]arr = {{0,1,3},{4,2,5},{7,8,6}};
    Board solvableBoard = new Board(arr);
    Solver solvableSolver = new Solver(solvableBoard);
    for(Board b: solvableSolver.solution()) {
      System.out.println(b);
    }
    
  }
}

