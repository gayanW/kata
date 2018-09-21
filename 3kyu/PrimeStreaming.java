import java.util.stream.IntStream;

/**
 * Create an endless stream of prime numbers - a bit like IntStream.of(2,3,5,7,11,13,17), but infinite.
 * The stream must be able to produce a million primes in a few seconds.
 * https://www.codewars.com/kata/prime-streaming-pg-13
 */
public class PrimeStreaming {

  public static IntStream stream() {
    return IntStream.range(2, Integer.MAX_VALUE).filter(PrimeStreaming::isPrime);
  }

  private static boolean isPrime(int n) {
    return IntStream.rangeClosed(2, (int) Math.sqrt(n)).noneMatch(i -> n % i == 0);
  }

  /**
   * (((((((((( Sieve of Eratosthenes approach ))))))))))
   */

  static class Sieve {

    private static final int LIMIT = Integer.MAX_VALUE - 1;
    private static boolean[] eliminated = new boolean[LIMIT + 1];

    public static IntStream streamSieve() {
      final int RANGE = (int) Math.sqrt(LIMIT);
      for (int i = 2; i <= RANGE; i++) {
        if (!eliminated[i]) {
          for (int eliminate = i * 2; eliminate <= LIMIT; eliminate += i)
            eliminated[eliminate] = true;
        }
      }
      return IntStream.range(2, LIMIT).filter(i -> !eliminated[i]);
    }

  }

}

