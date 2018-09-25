import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Arrays;
import org.junit.Test;

/**
 * Irreducible Sum of Rationals
 * https://www.codewars.com/kata/5517fcb0236c8826940003c9
 */
public class SumFractions {

  public static String sumFracts(int[][] l) {
    final int D = Arrays.stream(l).mapToInt(ar -> ar[1]).reduce(1, (a, b) -> a * b);
    final int N = Arrays.stream(l).mapToInt(ar -> ar[0] * D / ar[1]).sum();

    int gcd = BigInteger.valueOf(D).gcd(BigInteger.valueOf(N)).intValue();
    return (D == gcd) ? String.valueOf(N / D) : String.format("[%d, %d]", N / gcd, D / gcd);
  }

  private static void testing(String actual, String expected) {
    assertEquals(expected, actual);
  }

  @Test
  public void test1() {
    System.out.println("Fixed Tests sumFracts");
    int[][] a = new int[][] { {1,2}, {2,9}, {3,18}, {4,24}, {6,48} };
    String r = "[85, 72]";
    testing(SumFractions.sumFracts(a), r);
    a = new int[][] { {1, 2}, {1, 3}, {1, 4} };
    r = "[13, 12]";
    testing(SumFractions.sumFracts(a), r);
    a = new int[][] { {1, 3}, {5, 3} };
    r = "2";
    testing(SumFractions.sumFracts(a), r);
    a = new int[][] {};
    r = null;
  }
}