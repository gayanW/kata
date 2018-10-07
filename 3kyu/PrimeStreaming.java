import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Create an endless stream of prime numbers - a bit like IntStream.of(2,3,5,7,11,13,17), but infinite.
 * The stream must be able to produce a million primes in a few seconds.
 * https://www.codewars.com/kata/prime-streaming-pg-13
 */
public class PrimeStreaming {

  /**
   * (((((((((( Sieve of Eratosthenes approach ))))))))))
   */

  static List<Integer> getPrimes(final int limit) {
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

  static IntStream streamSieve(final int limit) {
    boolean[] eliminated = new boolean[limit + 1];
    final int RANGE = (int) Math.sqrt(limit);
    for (int i = 2; i <= RANGE; i++) {
      if (!eliminated[i]) {
        for (int eliminate = i + i; eliminate <= limit; eliminate += i)
          eliminated[eliminate] = true;
      }
    }
    return IntStream.range(2, limit).filter(i -> !eliminated[i]);
  }
}

