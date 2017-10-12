import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.ArrayList;

public class PointSET {
  private TreeSet<Point2D> treeSet;

  public PointSET() {              
    treeSet = new TreeSet<Point2D>();
  }

  public boolean isEmpty() {      
    return treeSet.isEmpty();
  }

  public int size() {            
    return treeSet.size();
  }

  public void insert(Point2D p) {   
    if (p == null) {
      throw new java.lang.IllegalArgumentException();
    }
    treeSet.add(p);
  }

  public boolean contains(Point2D p) { 
    return treeSet.contains(p);
  }

  public void draw() {                
    for (Point2D p: treeSet) {
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) { 
    Iterator<Point2D> treeSetIterator = treeSet.iterator();
    Point2D element;
    ArrayList<Point2D> list = new ArrayList<>();

    while (treeSetIterator.hasNext()) { 
      element = treeSetIterator.next();
      if (rect.contains(element)) {
        list.add(element);
      }
    }

    return list;
  }

  public Point2D nearest(Point2D p){
    if (isEmpty()) {
      return null;
    }
    Point2D closest = null;
    double distance = 0.0;

    for (Point2D c: treeSet) {
      if (closest == null) {
        closest = c;
        distance = c.distanceTo(p); 
      } else if (distance > c.distanceTo(p)) {
        closest = c;
        distance = c.distanceTo(p);
      }
    }
    return closest;
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

    PointSET pointSet = new PointSET();
    pointSet.insert(first);
    pointSet.insert(second);
    pointSet.insert(third);
    pointSet.insert(notInRectangle);

    assert pointSet.size() == 4;
    assert pointSet.contains(firstCopy);
    assert !pointSet.contains(notInSet);
    System.out.println("closest " + pointSet.nearest(close));
    PointSET newPointSet  = new PointSET();

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
