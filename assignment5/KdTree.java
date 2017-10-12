import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Point2D;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
  private Node root;
  private int size;


  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree
  }

  public KdTree() {
    size = 0;
  }

  public boolean isEmpty() {
    if (root == null) {
      return true;
    }
    return false;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {   

    if (root == null) {
      root = new Node();
      root.p = p;
      root.rect = new RectHV(0.0, 0.0, 1.0, 1.0);
      size++;
      return;
    }

    Node previous;
    Node head = root;
    boolean horizontal = true;
    while (head != null) {
      if (horizontal) {
        if (head.p.equals(p)) {
          return;
        } else if (head.p.x() > p.x()) {
          if (head.lb == null) {
            //vertical line to left
            double ymin = head.rect.ymin();
            double ymax= head.rect.ymax();
            double xmin = head.rect.xmin();


            head.lb = new Node();
            head.lb.p = p;
            head.lb.rect = new RectHV(xmin, ymin, head.p.x(), ymax);
            break;
          } else {
            head = head.lb;
          }
        } else {
          if (head.rt == null) {
            double ymin = head.rect.ymin();
            double ymax= head.rect.ymax();
            double xmax = head.rect.xmax();
            //vertical line to right
            head.rt = new Node();
            head.rt.p = p;
            head.rt.rect = new RectHV(head.p.x(), ymin ,xmax ,ymax);
            break;
          } else {
            head = head.rt;
          }
        }
        horizontal = false;
      } else { 
        if (head.p.equals(p)) {
          return;
        } else if (head.p.y() > p.y()) {
          if (head.lb == null) {
            double xmax = head.rect.xmax();
            double xmin = head.rect.xmin();
            double ymin = head.rect.ymin();
            // horizontal line to bottom
            head.lb = new Node();
            head.lb.p = p;
            head.lb.rect = new RectHV(xmin, ymin, xmax, head.p.y());
            break;
          } else {
            head = head.lb;
          }
        } else {
          if (head.rt == null) {
            double xmax = head.rect.xmax();
            double xmin = head.rect.xmin();
            double ymax = head.rect.ymax();
            // horizontal line to top
            head.rt = new Node();
            head.rt.p = p;
            head.rt.rect = new RectHV(xmin, head.p.y(),xmax , ymax);
            break;
          } else {
            head = head.rt;
          }
        }
        horizontal = true;
      }
    }
    size++;
  }

  public boolean contains(Point2D p) {

    Node head = root;

    boolean horizontal = true;
    while (head != null) {
      if (head.p.equals(p)) {
        return true;
      } else if (horizontal) {
        if (head.p.x() > p.x()) {
          head = head.lb;
        } else {
          head = head.rt;
        }
        horizontal = false;
      } else { 
        if (head.p.y() > p.y()) {
          head = head.lb;
        } else {
          head = head.rt;
        }
        horizontal = true;
      }
    }
    return false;
  }

  public void draw() {

  }

  public Iterable<Point2D> range(RectHV rect) {
    Node head = root;

    ArrayList<Point2D> arr = new ArrayList<Point2D>();
    recursive(rect, root, arr);

    return arr;
  }

  private void recursive(RectHV rect, Node node, List<Point2D> list) {
    if (node != null) {

      if (node.rect.intersects(rect)) 
      {
        if (rect.contains(node.p)) {
          list.add(node.p);
        }
        recursive(rect, node.lb, list);
        recursive(rect, node.rt, list);
      }
    }
  }

  public Point2D nearest(Point2D p) {
    

    return calculateNeighbor(root, p, root.p);
  }
  private Point2D calculateNeighbor(Node n,Point2D p, Point2D minPoint) {

    if (n != null && n.rect.distanceTo(p) < minPoint.distanceTo(p)) {

      Point2D left = calculateNeighbor(n.lb, p, minPoint);
      if (n.p.distanceTo(p) < minPoint.distanceTo(p)) {
        minPoint = n.p;
      }

      if (left.distanceTo(p) < minPoint.distanceTo(p)) {
        minPoint = left;
      }

      Point2D right = calculateNeighbor(n.rt, p, minPoint);


      if (right.distanceTo(p) < minPoint.distanceTo(p)) {
        minPoint  = right;
      }
    }

    return minPoint;
  }

  public static void main(String[] args) {
    RectHV rect = new RectHV(0.0, 0.0, 2.0, 2.0);
    Point2D first = new Point2D(1.0, 0.0);
    Point2D second = new Point2D(1.0, 1.0);
    Point2D third = new Point2D(0.0, 0.1);
    Point2D firstCopy = new Point2D(0.0, 0.0);
    Point2D close = new Point2D(0.25, 0.25);
    Point2D notInSet = new Point2D(20.0, 0.0);
    Point2D notInRectangle = new Point2D(0.0, 0.0);

    KdTree pointSet = new KdTree();
    pointSet.insert(first);
    pointSet.insert(second);
    pointSet.insert(third);
    pointSet.insert(notInRectangle);

    assert pointSet.size() == 4;
    assert pointSet.contains(firstCopy);
    assert !pointSet.contains(notInSet);
    System.out.println("closest " + pointSet.nearest(close));
    KdTree newPointSet  = new KdTree();

    while (!StdIn.isEmpty()) { 
      String s = StdIn.readString();
      String m = StdIn.readString();
      double one = Double.parseDouble(s);
      double two = Double.parseDouble(m);
      newPointSet.insert(new Point2D(one, two));
    }
    for(Point2D p : newPointSet.range(rect)) {
      System.out.println( p);
    }
  }
}
