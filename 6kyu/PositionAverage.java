import java.util.stream.IntStream;

/**
 * Positions Average
 * https://www.codewars.com/kata/positions-average/train/java
 */
public class PositionAverage {

  private static double posAverage(String s) {
    String[] strs = s.split(", ");
    long common = 0;
    for (int i = 0; i < strs.length - 1; i++)
      for (int j = i + 1; j < strs.length; j++)
        common += common(strs[i], strs[j]);

    long totalPos = (strs.length * (strs.length - 1) / 2) * strs[0].length();
    return (double) common / totalPos * 100;
  }

  private static long common(String s1, String s2) {
    return IntStream.range(0, s1.length()).filter(i -> s1.charAt(i) == s2.charAt(i)).count();
  }
}
