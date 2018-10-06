import java.math.BigInteger;

/**
 * Precise Fractions pt. 1
 * https://www.codewars.com/kata/54cf4fc26b85dc27bf000a6b
 */
public class Fraction {

  private int numerator, denominator;

  private Fraction(int numerator, int denominator) {
    BigInteger gcd = BigInteger.valueOf(numerator).gcd(BigInteger.valueOf(denominator));
    this.numerator = numerator / gcd.intValue();
    this.denominator = denominator / gcd.intValue();
  }

  @Override
  public String toString() {
    String sign = ((double) numerator / denominator < 0) ? "-" : "";
    int whole = Math.abs(numerator / denominator);
    int remainder = Math.abs(numerator % denominator);
    int den = Math.abs(denominator);
    return (sign + (whole != 0 ? whole + " " : "") + (remainder != 0 ? remainder : "") + (den != 1 ? "/" + den : "")).trim();
  }

  public double toDecimal() {
    return (double) numerator / denominator;
  }

  public Fraction add(int value) {
    return new Fraction(value * denominator + numerator, denominator);
  }

  public Fraction add(Fraction fract) {
    int num = numerator * fract.denominator + fract.numerator * denominator;
    int den = denominator * fract.denominator;
    return new Fraction(num, den);
  }

  public Fraction substract(int value) {
    return new Fraction(numerator - value * denominator, denominator);
  }

  public Fraction substract(Fraction fract) {
    int num = numerator * fract.denominator - fract.numerator * denominator;
    int den = denominator * fract.denominator;
    return new Fraction(num, den);
  }

  public Fraction multiply(int value) {
    return new Fraction(numerator * value, denominator);
  }

  public Fraction multiply(Fraction fract) {
    return new Fraction(numerator * fract.numerator, denominator * fract.denominator);
  }

  public Fraction divide(int value) {
    return new Fraction(numerator, denominator * value);
  }

  public Fraction divide(Fraction fract) {
    return new Fraction(numerator * fract.denominator, denominator * fract.numerator);
  }
}
