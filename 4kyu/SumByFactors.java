import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Sum By Factors
 * https://www.codewars.com/kata/sum-by-factors/java
 */
public class SumByFactors {

  private static Map<Integer, List<Integer>> primeFactCache = new HashMap<>();

  private static String sumOfDivided(int[] ints) {
    List<Integer> primes = getPrimes(Arrays.stream(ints).map(Math::abs).max().getAsInt());
    List<Integer> primeFactors = Arrays.stream(ints).mapToObj(i -> getPrimeFactors(Math.abs(i), primes)).flatMap(List::stream).distinct().sorted().collect(Collectors.toList());
    StringBuilder sb = new StringBuilder();
    for (int prime : primeFactors) {
      int sum = 0;
      for (int i : ints)
        if (primeFactCache.get(Math.abs(i)).contains(prime)) sum += i;
      sb.append(String.format("(%d %d)", prime, sum));
    }
    return sb.toString();
  }

  private static List<Integer> getPrimeFactors(int i, List<Integer> primes) {
    return primeFactCache.computeIfAbsent(i, k ->
        primes.stream().filter(p -> p <= i && i % p == 0).collect(toList()));
  }

  private static List<Integer> getPrimes(final int limit) {
    List<Integer> primes = new ArrayList<>();
    boolean[] eliminated = new boolean[limit+1];
    for (int i = 2; i <= limit; i++) {
      if (eliminated[i]) continue;
      primes.add(i);
      for (int j = i + i; j <= limit; j += i) {
        eliminated[j] = true;
      }
    }
    return primes;
  }
}
