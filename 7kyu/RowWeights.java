import static org.junit.Assert.assertArrayEquals;

import java.util.stream.IntStream;
import org.junit.Test;

/**
 * Row Weights
 * https://www.codewars.com/kata/row-weights
 */
public class RowWeights {

  private static int[] rowWeights(final int[] weights)
  {
    int[] result = new int[2];
    IntStream.range(0, weights.length).forEach(i -> result[i % 2] += weights[i]);
    return result;
  }

  /**
   * ((((( TESTS )))))
   */

  @Test
  public void Basic_Tests()
  {
    assertArrayEquals(new int[]{80,0}, rowWeights(new int[]{80}));
    assertArrayEquals(new int[]{100,50}, rowWeights(new int[]{100,50}));
    assertArrayEquals(new int[]{120,140}, rowWeights(new int[]{50,60,70,80}));
  }
  @Test
  public void Odd_Vector_Length()
  {
    assertArrayEquals(new int[]{62,27}, rowWeights(new int[]{13,27,49}));
    assertArrayEquals(new int[]{236,92}, rowWeights(new int[]{70,58,75,34,91}));
    assertArrayEquals(new int[]{211,164}, rowWeights(new int[]{29,83,67,53,19,28,96}));
  }
  @Test
  public void Even_Vector_Length()
  {
    assertArrayEquals(new int[]{100,50}, rowWeights(new int[]{100,50}));
    assertArrayEquals(new int[]{150,151}, rowWeights(new int[]{100,51,50,100}));
    assertArrayEquals(new int[]{207,235}, rowWeights(new int[]{39,84,74,18,59,72,35,61}));
  }
}
