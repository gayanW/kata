import java.util.TreeSet;

/**
 * Twice linear
 * https://www.codewars.com/kata/twice-linear
 */
public class DoubleLinear {

  public static int dblLinear (int n) {
    TreeSet<Integer> u = new TreeSet<>();
    u.add(1);
    for (int i = 0; i < n; i++) {
      int current = u.pollFirst();
      u.add(current * 2 + 1);
      u.add(current * 3 + 1);
    }
    return u.first();
  }
}
