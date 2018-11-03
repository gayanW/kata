import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;

/**
 * Next bigger number with the same digits
 * https://www.codewars.com/kata/next-bigger-number-with-the-same-digits
 */
public class NextBigNumber {

  private static long nextBiggerNumber(long n) {
    List<Integer> digits = getDigits(n);

    Optional<Integer> minJ = Optional.empty();
    for (int i = digits.size() - 2; i >= 0; i--) {
      for (int j = i; j < digits.size(); j++) {
        if (digits.get(i) < digits.get(j) && (!minJ.isPresent() || digits.get(j) < digits.get(minJ.get()))) {
          minJ = Optional.of(j);
        }
      }
      if (minJ.isPresent()) {
        Collections.swap(digits, i, minJ.get());
        Collections.sort(digits.subList(i + 1, digits.size()));
        break;
      }
    }

    return minJ.isPresent() ? getNumber(digits) : -1;
  }

  private static long getNumber(List<Integer> digits) {
    long result = 0, pow = 1;
    for (int i = digits.size() - 1; i >= 0; i--) {
      result += digits.get(i) * pow;
      pow *= 10;
    }
    return result;
  }

  private static List<Integer> getDigits(long n) {
    List<Integer> digits = new ArrayList<>();
    while (n > 0) {
      digits.add((int) (n % 10));
      n /= 10;
    }
    Collections.reverse(digits);
    return digits;
  }

  @Test
  public void basicTests() {
    assertEquals(21, nextBiggerNumber(12));
    assertEquals(531, nextBiggerNumber(513));
    assertEquals(2071, nextBiggerNumber(2017));
    assertEquals(441, nextBiggerNumber(414));
    assertEquals(414, nextBiggerNumber(144));

    assertEquals(59884848483559L, nextBiggerNumber(59884848459853L));
  }
}
