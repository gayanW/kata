import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Directions Reduction
 * https://www.codewars.com/kata/directions-reduction
 */
public class DirReduction {

  private static Map<String, String> opposite = new HashMap<>(4);
  static {
    opposite.put("NORTH", "SOUTH");
    opposite.put("SOUTH", "NORTH");
    opposite.put("WEST", "EAST");
    opposite.put("EAST", "WEST");
  }

  private static String[] reduce(String[] arr) {
    outer_loop:
    for (int i = 0; i < arr.length - 1; i++) {
      if (arr[i].equals("R")) continue;

      for (int j = i + 1; j < arr.length; j++) {
        if (isOpposite(arr[i], arr[j])) {
          arr[i] = arr[j] = "R";
          continue outer_loop;
        }
      }
    }
    return Arrays.stream(arr).filter(s -> !s.equals("R")).toArray(String[]::new);
  }

  private static boolean isOpposite(String d1, String d2) {
    return opposite.get(d1).equals(d2);
  }
}

