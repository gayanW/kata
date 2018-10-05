import java.math.BigInteger;

public class ProperFractions {

  public static long properFractions(long d) {
    BigInteger bigD = BigInteger.valueOf(d);
    if (bigD.isProbablePrime(30)) return d - 1;
    long count = 0;
    for (BigInteger bigN = BigInteger.ONE; bigN.compareTo(bigD) == -1; bigN = bigN.add(BigInteger.ONE)) {
      if (bigN.gcd(bigD).equals(BigInteger.ONE)) count++;
    }
    return count;
  }

}
