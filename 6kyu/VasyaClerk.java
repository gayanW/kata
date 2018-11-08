import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Vasya - Clerk
 * https://www.codewars.com/kata/vasya-clerk
 */
public class VasyaClerk {

  private static String Tickets(int[] peopleInLine)
  {
    Map<Integer, Integer> billCount = new HashMap<Integer, Integer>() {{
      put(25, 0); put(50, 0); put(100, 0);
    }};

    for (int bill : peopleInLine) {
      billCount.put(bill, billCount.get(bill) + 1);
      int change = bill - 25;

      if (change == 25) {
        if (billCount.get(25) > 0)
          billCount.put(25, billCount.get(25) - 1);
        else return "NO";
      }
      if (change == 75) {
        if (billCount.get(50) > 0 && billCount.get(25) > 0) {
          billCount.put(50, billCount.get(50) - 1);
          billCount.put(25, billCount.get(25) - 1);
        }
        else if (billCount.get(25) >= 3) {
          billCount.put(25, billCount.get(25) - 3);
        }
        else return "NO";
      }
    }
    return "YES";
  }

  @Test
  public void test1() {
    assertEquals("YES", Tickets(new int[] {25, 25, 50}));
  }
  @Test
  public void test2() {
    assertEquals("NO", Tickets(new int []{25, 100}));
  }

}
