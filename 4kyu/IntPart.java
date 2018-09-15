import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.Test;

/**
 * Getting along with Integer Partitions
 * https://www.codewars.com/kata/getting-along-with-integer-partitions
 */
public class IntPart {

  private static Map<Long, List<Long>> prodMemo = new HashMap<>();
  static {
    prodMemo.put(1L, Collections.singletonList(1L));
  }

  private static List<Long> prod(long n) {
    List<Long> prodList = prodMemo.get(n);
    if (prodList != null) return prodList;

    prodList = LongStream.range(1, n + 1).boxed().collect(Collectors.toList());
    for (long i = 2; i < n; i++) {
      List<Long> prev = prod(n - i);
      for (Long l : prev) {
        Long val = l * i;
        if (val > n) prodList.add(l * i);
      }
    }
    prodList = prodList.stream().distinct().sorted().collect(Collectors.toList());
    prodMemo.put(n, prodList);
    return prodList;
  }

  private static String part(long n) {
    List<Long> prod = prod(n);
    final int size = prod.size();
    long range = prod.get(size - 1) - prod.get(0);
    double average = prod.stream().mapToLong(l -> l).sum() / (double) prod.size();
    double median = prod.size() % 2 == 0 ?
        (prod.get(size / 2) + prod.get(size / 2 - 1)) / 2d :
        prod.get(size / 2);
    return String.format("Range: %d Average: %.2f Median: %.2f", range, average, median);
  }

  @Test
  public void Test_Prod() {
    assertEquals(Collections.singletonList(1L), prod(1));
    assertEquals(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L), prod(5));
    assertEquals(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 12L, 15L, 16L, 18L), prod(8));
  }

  @Test
  public void Numbers_Small() {
    assertEquals("Range: 1 Average: 1.50 Median: 1.50", IntPart.part(2));
    assertEquals("Range: 2 Average: 2.00 Median: 2.00", IntPart.part(3));
    assertEquals("Range: 3 Average: 2.50 Median: 2.50", IntPart.part(4));
    assertEquals("Range: 5 Average: 3.50 Median: 3.50", IntPart.part(5));
  }
}

