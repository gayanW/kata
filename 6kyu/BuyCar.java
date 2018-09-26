import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 * Buying a car
 * https://www.codewars.com/kata/554a44516729e4d80b000012
 */
public class BuyCar {

  private static int[] nbMonths(int startPriceOld, int startPriceNew, int savingperMonth, double percentLossByMonth) {
    float savings = 0, priceOld = (float) startPriceOld, priceNew = (float) startPriceNew;
    int m = 0;
    while (savings + priceOld < priceNew) {
      m++;
      if (m % 2 == 0) percentLossByMonth += 0.5;
      savings += savingperMonth;
      priceOld -= priceOld * percentLossByMonth / 100;
      priceNew -= priceNew * percentLossByMonth / 100;
    }
    return new int[] { m, (int) (savings + priceOld - priceNew) };
  }

  @Test
  public void test1() {
    int[] r = new int[] { 6, 766 };
    assertArrayEquals(r, BuyCar.nbMonths(2000, 8000, 1000, 1.5));
  }
  @Test
  public void test2() {
    int[] r = new int[] { 0, 4000 };
    assertArrayEquals(r, BuyCar.nbMonths(12000, 8000, 1000, 1.5));
  }
}
