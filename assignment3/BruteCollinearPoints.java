import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
  private final int  numberOfSegments;
  private final LineSegment [] segments;

  public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
    ArrayList<LineSegment> arr = new ArrayList<LineSegment>();

    if (points == null) {
      throw new java.lang.IllegalArgumentException();
    }

    for (Point p: points) {
      if (p == null) {
        throw new java.lang.IllegalArgumentException();
      }
    }

    Point [] copyPoints = new Point[points.length];
    for (int i = 0; i < points.length; i++) {
      copyPoints[i] = points[i];  
    }

    for (int i = 0; i < copyPoints.length; i++) {
      for (int j = 0; j < copyPoints.length; j++) {
        if (i != j && copyPoints[i].compareTo(copyPoints[j]) == 0) {
          throw new java.lang.IllegalArgumentException();
        }
      }
    }

    int countSegments = 0;

    for (int p = 0; p < copyPoints.length; p++) {
      for (int q = p + 1; q < copyPoints.length; q++) {
        double slopeToQ = copyPoints[p].slopeTo(copyPoints[q]);
        for (int r = q + 1; r < copyPoints.length; r++) {
          double slopeToR = copyPoints[p].slopeTo(copyPoints[r]);
          for (int s = r + 1; s < copyPoints.length; s++) {
            double slopeToS = copyPoints[p].slopeTo(copyPoints[s]);
            if (slopeToQ == slopeToR && slopeToR == slopeToS) {
              Point []seg = new Point[4];
              seg[0] = copyPoints[p];
              seg[1] = copyPoints[q];
              seg[2] = copyPoints[r];
              seg[3] = copyPoints[s];
              Arrays.sort(seg);
              arr.add(new LineSegment(seg[0], seg[3]));
              countSegments++;
            }
          }
        }
      }
    }
    numberOfSegments = countSegments;

    segments = arr.toArray(new LineSegment[arr.size()]);
  }

  public int numberOfSegments() {        // the number of line segments
    return numberOfSegments;
  }

  public LineSegment[] segments() {                // the line segments
    LineSegment [] copyLineSegments = new LineSegment[segments.length];
    for (int i = 0; i < segments.length; i++) {
      copyLineSegments[i] = segments[i];  
    }
    return copyLineSegments;
  }
}
