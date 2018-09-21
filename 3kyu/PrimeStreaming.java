import java.util.stream.IntStream;

/**
 * Create an endless stream of prime numbers - a bit like IntStream.of(2,3,5,7,11,13,17), but infinite.
 * The stream must be able to produce a million primes in a few seconds.
 * https://www.codewars.com/kata/prime-streaming-pg-13
 */
public class PrimeStreaming {

  private static final int LIMIT = 100000000;
  private static boolean[] eliminated = new boolean[LIMIT + 1];

  public static IntStream stream() {
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

