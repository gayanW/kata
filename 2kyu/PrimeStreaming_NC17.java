import java.util.BitSet;
import java.util.stream.IntStream;

/**
 * Create an endless stream of prime numbers - a bit like IntStream.of(2,3,5,7,11,13,17), but infinite.
 * The stream must be able to produce 50 million primes in a few seconds.
 */
public class PrimeStreaming_NC17 {

  // 50_000_000 th prime
  private static final int LIMIT = 982451707;
  private static final BitSet primes = new BitSet(LIMIT+1);

  static {
    primes.set(2, primes.size());

    for (int i = 4; i < LIMIT; i += 2)
      primes.clear(i);

    final int SQRT = (int) Math.sqrt(LIMIT);
    for (int i = 3; i < SQRT; i = primes.nextSetBit(i+2)) {
      for (int j = i * i; j < LIMIT; j += 2 * i) {
        primes.clear(j);
      }
    }
  }

  /**
   * TIP: return a lazy stream instead of returning the entire stream
   */
  public static IntStream stream() {
    return primes.stream();
  }
}
