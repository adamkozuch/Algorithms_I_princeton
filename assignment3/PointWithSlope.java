import java.util.Arrays;

  public class PointWithSlope implements Comparable<PointWithSlope> {   
    private Point p;
    private double slope;
    public PointWithSlope(Point p, double slope) {
      this.p = p;
      this.slope = slope;
    }

    public int compareTo(PointWithSlope q) {
      if (this.slope < q.slope) {
        return -1;
      } else if (this.slope > q.slope) {
        return 1;
      } else {
        return 0;
      }
    }

    public Point returnPoint() {
      return p;
    }

    public double returnSlope() {
      return slope;
    }
}
