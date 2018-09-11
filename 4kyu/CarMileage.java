import static org.junit.Assert.assertEquals;

import java.util.stream.IntStream;
import org.junit.Test;

/**
 * Catching Car Mileage Numbers
 * https://www.codewars.com/kata/catching-car-mileage-numbers/java
 */
public class CarMileage {

  public static int isInteresting(int number, int[] awesomePhrases) {
    if (interesting(number, awesomePhrases))
      return 2;
    if (interesting(number + 1, awesomePhrases) || interesting(number + 2, awesomePhrases))
      return 1;

    return 0;
  }

  private static boolean interesting(int number, int[] awesomePhrases) {
    // 3-or-more digits
    if (number / 100 < 1) return false;

    String s = String.valueOf(number);
    // any digit followed by all zeros:
    if (s.matches("\\d0+")) return true;
    // every digit is the same
    if (s.replace(String.valueOf(s.charAt(0)), "").length() == 0)  return true;
    // digits are sequential, incrementing
    final String INCREMENTING = "1234567890";
    if (INCREMENTING.contains(s)) return true;
    // digits are sequential, decrementing
    final String DECREMENTING = "9876543210";
    if (DECREMENTING.contains(s)) return true;
    // palindrome
    if (new StringBuilder(s).reverse().toString().equals(s)) return true;
    // one of the values in the awesomePhrases array
    return IntStream.of(awesomePhrases).anyMatch(i -> i == number);
  }

  @Test
  public void testTooSmall() {
    assertEquals(0, CarMileage.isInteresting(3, new int[]{1337, 256}));
  }

  @Test
  public void testAlmostAwesome() {
    assertEquals(1, CarMileage.isInteresting(1336, new int[]{1337, 256}));
  }

  @Test
  public void testAwesome() {
    assertEquals(2, CarMileage.isInteresting(1337, new int[]{1337, 256}));
  }

  @Test
  public void testFarNotInteresting() {
    assertEquals(0, CarMileage.isInteresting(11208, new int[]{1337, 256}));
  }

  @Test
  public void testAlmostInteresting() {
    assertEquals(1, CarMileage.isInteresting(11209, new int[]{1337, 256}));
  }

  @Test
  public void testInteresting() {
    assertEquals(2, CarMileage.isInteresting(11211, new int[]{1337, 256}));
  }
}
