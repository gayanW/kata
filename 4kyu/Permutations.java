import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Permutations
 * In this kata you have to create all permutations of an input string and remove duplicates, if present.
 * This means, you have to shuffle all letters from the input in all possible orders.
 * https://www.codewars.com/kata/permutations/java
 */
public class Permutations {

  public static List<String> singlePermutations(String s) {
    if (s.length() == 1) return Collections.singletonList(s);
    List<String> permutations = new ArrayList<>();
    for (int i = 0; i < s.length(); i++) {
      String rest = s.substring(0, i) + s.substring(i + 1, s.length());
      int finalI = i;
      permutations.addAll(singlePermutations(rest).stream().map(p -> s.charAt(finalI) + p).collect(toList()));
    }
    return permutations.stream().distinct().collect(toList());
  }
}
