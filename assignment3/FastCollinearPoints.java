import java.util.Arrays;
import java.util.ArrayList;

public class FastCollinearPoints {
  private LineSegment[] segments;
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new java.lang.IllegalArgumentException();
    }

    for (Point p: points) {
      if (p == null) {
        throw new java.lang.IllegalArgumentException();
      }
    }

    for (int i = 0; i < points.length; i++) {
      for (int j = 0; j < points.length; j++) {
        if (i != j && points[i].compareTo(points[j]) == 0) {
          throw new java.lang.IllegalArgumentException();
        }
      }
    }

    segments = returnListOfSegments(points);
  }
  private LineSegment [] returnListOfSegments(Point[] points) {
    ArrayList<LineSegment> lineSegmenstList = new ArrayList<LineSegment>();

    int numberOfSegments = 0;

    for (int i = 0; i < points.length; i++) {
      Arrays.sort(points, points[i].slopeOrder());
      ArrayList<Point> segPoints = new ArrayList<Point>();
      Point pivot = points[i];

      for (int j = i + 1; j < points.length - 1; j++) {
        if (Double.compare(pivot.slopeTo(points[j]), pivot.slopeTo(points[j + 1])) == 0) {

          double slope = pivot.slopeTo(points[j]);
          segPoints.add(points[j]);

          while (j < points.length - 1) {
            j++;
            i++;
            if (slope == pivot.slopeTo(points[j])) {
              segPoints.add(points[j]);
            }
          }
          break;
        }

      }

      if (segPoints.size() >= 3) {
        segPoints.add(pivot);
        Point [] pointArray = segPoints.toArray(new Point[segPoints.size()]);
        Arrays.sort(pointArray);
        lineSegmenstList.add(new LineSegment(pointArray[0], pointArray[pointArray.length - 1]));
      }
    }

    return lineSegmenstList.toArray(new LineSegment[lineSegmenstList.size()]);
  }

  public int numberOfSegments() {
    return segments.length;
  }

  public LineSegment[] segments() {
    LineSegment [] copyLineSegments = new LineSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      copyLineSegments[i] = segments[i];  
    }

    return copyLineSegments;
  }

  public static void main(String [] args) { 
    //(2000, 29000) -> (4000, 29000) -> (22000, 29000) -> (28000, 29000)
    Point a  = new Point(2000, 29000);
    Point b  = new Point(4000, 29000);
    Point c  = new Point(22000, 29000);
    Point d  = new Point(28000, 29000);
    //(30000, 0) -> (20000, 10000) -> (10000, 20000) -> (0, 30000)
    Point [] arr ={ new Point(1000, 26000) , new Point(9000, 26000), new Point(11000, 26000),  new Point(18000, 26000), a, b, c, d};
    Point [] secArr = {new Point(30000, 0), new Point(20000, 10000), new Point(10000, 20000), new Point(0, 30000), d, a, b , c};
    Point a1 = new Point(1000, 1000);
    Point a2 = new Point(2000, 2000);
    Point a3 = new Point(3000, 3000);
    Point a4 = new Point(4000, 4000);
    Point a5 = new Point(5000, 5000);
    Point a6 = new Point(6000, 6000);
    Point a7 = new Point(7000, 7000);
    Point a8 = new Point(8000, 8000);
    Point a9 = new Point(9000, 9000);
    Point [] myArr = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a, b, c, d};
    FastCollinearPoints f = new FastCollinearPoints(myArr);
    System.out.println(f.segments()[0]);
  }
}
