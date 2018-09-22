import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Moves in squared strings (III)
 * https://www.codewars.com/kata/56dbeec613c2f63be4000be6
 */
public class SquaredStringsIII {

  public static String diag1Sym(String str) {
    String[] lines = str.split("\n");
    return IntStream.range(0, lines.length)
        .mapToObj(i -> Arrays.stream(lines).map(l -> String.valueOf(l.charAt(i))).collect(joining()))
        .collect(joining("\n"));
  }

  public static String rot90Clock(String str) {
    String[] lines = diag1Sym(str).split("\n");
    return Arrays.stream(lines).map(s -> new StringBuilder(s).reverse().toString()).collect(joining("\n"));
  }

  public static String selfieAndDiag1(String str) {
    String[] lines = str.split("\n");
    String[] diagLines = diag1Sym(str).split("\n");
    return IntStream.range(0, lines.length).mapToObj(i -> lines[i] + "|" + diagLines[i]).collect(joining("\n"));
  }

  public static String oper(Function<String, String> func, String s) {
    return func.apply(s);
  }

}
