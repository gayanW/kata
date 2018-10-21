import static java.lang.Math.abs;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ConvexHull {

  private static class Point {
    private double x, y;

    Point(double x, double y) {
      this.x = x;
      this.y = y;
    }
  }

  static double getArea(double[][] points) {
    if (points.length <= 2) return 0;
    List<Point> pointList = Arrays.stream(points).map(ar -> new Point(ar[0], ar[1])).collect(Collectors.toList());
    return round(getAreaPolygon(convexHull(pointList)) * 100) / 100d;
  }

  /**
   * @return area of a polygon with ordered vertices
   */
  static double getAreaPolygon(List<Point> points) {
    double area = 0d;
    for (int i = 0, j = points.size() - 1; i < points.size(); j = i++)
    {
      area += (points.get(j).x + points.get(i).x) * (points.get(j).y - points.get(i).y);
    }
    return abs(area / 2d);
  }

  private static List<Point> convexHull(List<Point> points) {
    points.sort(Comparator.comparingDouble(p -> p.x));
    List<Point> hull = new ArrayList<>();

    points.forEach(p -> {
      while (hull.size() >= 2 && !isCounterClockWise(hull.get(hull.size() - 2), hull.get(hull.size() - 1), p)) {
        hull.remove(hull.size() - 1);
      }
      hull.add(p);
    });

    int t = hull.size() + 1;
    for (int i = points.size() - 1; i >= 0; i--) {
      Point pt = points.get(i);
      while (hull.size() >= t && !isCounterClockWise(hull.get(hull.size() - 2), hull.get(hull.size() - 1), pt)) {
        hull.remove(hull.size() - 1);
      }
      hull.add(pt);
    }

    hull.remove(hull.size() - 1);
    return hull;
  }

  private static boolean isCounterClockWise(Point a, Point b, Point c) {
    return ((b.x - a.x) * (c.y - a.y)) > ((b.y - a.y) * (c.x - a.x));
  }
}
