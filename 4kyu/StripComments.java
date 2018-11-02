import static java.util.stream.Collectors.joining;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Strip Comments
 * https://www.codewars.com/kata/strip-comments
 */
public class StripComments {

  public static String stripComments(String text, String[] commentSymbols) {
    return Arrays.stream(text.split("\n"))
        .map(str -> {
          for (String symbol : commentSymbols)
            str = stripComment(str, symbol);
          return trimRight(str);
        })
        .collect(joining("\n"));
  }

  private static String stripComment(String text, String symbol) {
    return text.replaceFirst(Pattern.quote(symbol) + ".*", "");
  }

  private static String trimRight(String text) {
    return text.replaceFirst("\\s+$", "");
  }
}
