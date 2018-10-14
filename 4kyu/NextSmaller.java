import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;
import org.junit.Test;

/**
 * Write a function that takes a positive integer and returns the next smaller positive integer containing the same digits.
 * https://www.codewars.com/kata/5659c6d896bc135c4c00021e
 */
public class NextSmaller
{
  public static long nextSmaller(long n) {
    List<Long> digits = new ArrayList<>();
    while (n > 0) {
      digits.add(n % 10);
      n /= 10;
    }
    // iterate over digits in reverse order
    for (int i = 1; i < digits.size(); i++) {
      final long digit = digits.get(i);
      List<Long> left = digits.subList(0, i).stream().filter(d -> d < digit).collect(toList());
      OptionalLong maxOpt = left.stream().mapToLong(Long::longValue).max();
      if (maxOpt.isPresent() && digit > maxOpt.getAsLong()) {
        long max = maxOpt.getAsLong();
        if (max == 0 && i == digits.size() - 1) break;
        digits.set(i, max);
        digits.set(digits.indexOf(max), digit);
        Collections.sort(digits.subList(0, i));
        return getValue(digits);
      }
    }
    return -1;
  }

  private static long getValue(List<Long> digits) {
    long result = 0, pow = 1;
    for (long digit : digits) {
      result += digit * pow;
      pow *= 10;
    }
    return result;
  }

  @Test
  public void basicTests() {
    assertEquals(12, nextSmaller(21));
    assertEquals(513, nextSmaller(531));
    assertEquals(-1, nextSmaller(1027));
    assertEquals(414, nextSmaller(441));
    assertEquals(790, nextSmaller(907));
    assertEquals(123456789, nextSmaller(123456798));

  }

  @Test
  public void largeTests() {
    assertEquals(32567951992449050L, nextSmaller(32567951992449500L));
    assertEquals(75284730098904285L, nextSmaller(75284730098904528L));
  }
}
