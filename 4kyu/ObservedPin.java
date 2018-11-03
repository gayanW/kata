import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The observed PIN
 * https://www.codewars.com/kata/the-observed-pin
 */
public class ObservedPin {

  private static List<String> combinations;

  public static List<String> getPINs(String observed) {
    combinations = new ArrayList<>();
    DFS(observed, 0, "");
    return combinations;
  }

  private static void DFS(final String observed, int idx, String combination) {
    if (idx == observed.length()) {
      combinations.add(combination);
      return;
    }

    int digit = Integer.valueOf("" + observed.charAt(idx));
    DFS(observed, idx + 1, combination + digit);
    ObservedPinHelper.adjacentDigits.get(digit)
        .forEach(adjacentDigit -> DFS(observed, idx + 1, combination + adjacentDigit));
  }

  static class ObservedPinHelper {
    static List<List<Integer>> adjacentDigits = new ArrayList<>(10);
    static {
      IntStream.range(0, 10).forEach(i -> adjacentDigits.add(new ArrayList<>()));
      for (int i = 1; i <= 9; i++) {
        int finalI = i;
        IntStream.of(-3, -1, 1, 3)
            .filter(dx -> !(finalI % 3 == 0 && dx == 1))
            .filter(dx -> !(finalI % 3 == 1 && dx == -1))
            .map(dx -> finalI + dx)
            .filter(adjacentDigit -> adjacentDigit >= 1 && adjacentDigit <= 9)
            .forEach(adjacentDigit -> adjacentDigits.get(finalI).add(adjacentDigit));
      }
      adjacentDigits.get(8).add(0);
      adjacentDigits.get(0).add(8);
    }
  }
}
