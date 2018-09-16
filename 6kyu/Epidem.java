import static org.junit.Assert.assertTrue;

import java.text.DecimalFormat;
import org.junit.Test;

/**
 * Disease Spread
 * https://www.codewars.com/kata/disease-spread/java
 */
public class Epidem {

  static int epidemic (int tm, int n, int s0, int i0, double b, double a) {
    double dt = (double) tm / n;
    double sK = s0, iK = i0, sK1, iK1;
    while (true) {
      iK1 = iK + dt * (b * sK * iK - a * iK);
      sK1 = sK - dt * b * sK * iK;
      if (iK1 < iK) break;
      iK = iK1;
      sK = sK1;
    }
    return (int) iK;
  }

  /**
   * (((((((((( TESTING ))))))))))
   */

  private static void testing(long actual, long expected) {
    long r = Math.abs(actual - expected);
    boolean inRange = r <= 1;
    if (!inRange) {
      DecimalFormat df = new DecimalFormat("#.0");
      System.out.println("abs(actual - expected) must be <= 1 but was " + df.format(r));
    }
    assertTrue(inRange);
  }

  @Test
  public void test1() {
    System.out.println("Fixed Tests: Epidemic");
    int tm = 18 ;int n = 432 ;int s0 = 1004 ;int i0 = 1 ;double b = 0.00209 ;double a = 0.51;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 420);
    tm = 12 ;n = 288 ;s0 = 1007 ;i0 = 2 ;b = 0.00206 ;a = 0.45;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 461);
    tm = 13 ;n = 312 ;s0 = 999 ;i0 = 1 ;b = 0.00221 ;a = 0.55;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 409);
    tm = 24 ;n = 576 ;s0 = 1005 ;i0 = 1 ;b = 0.00216 ;a = 0.45;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 474);
    tm = 24 ;n = 576 ;s0 = 982 ;i0 = 1 ;b = 0.00214 ;a = 0.44;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 460);
    tm = 20 ;n = 480 ;s0 = 1000 ;i0 = 1 ;b = 0.00199 ;a = 0.53;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 386);
    tm = 28 ;n = 672 ;s0 = 980 ;i0 = 1 ;b = 0.00198 ;a = 0.44;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 433);
    tm = 14 ;n = 336 ;s0 = 996 ;i0 = 2 ;b = 0.00206 ;a = 0.41;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 483);
    tm = 13 ;n = 312 ;s0 = 993 ;i0 = 2 ;b = 0.0021 ;a = 0.51;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 414);
    tm = 28 ;n = 672 ;s0 = 999 ;i0 = 1 ;b = 0.00197 ;a = 0.55;
    testing(Epidem.epidemic(tm, n, s0, i0, b, a), 368);
  }
}
