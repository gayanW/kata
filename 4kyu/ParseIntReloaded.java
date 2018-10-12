import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * In this kata we want to convert a string into an integer. The strings simply represent the numbers in words.
 * https://www.codewars.com/kata/525c7c5ab6aecef16e0001a5
 */
public class ParseIntReloaded {

  private static Map<String, Integer> map = new HashMap<>() {{
    put("zero", 0);        put("ten", 10);            put("twenty", 20);      put("hundred", 100);
    put("one", 1);         put("eleven", 10);         put("thirty", 30);      put("thousand", 1000);
    put("two", 2);         put("twelve", 11);         put("forty", 40);       put("million", 1000000);
    put("three", 3);       put("thirteen", 12);       put("fifty", 50);
    put("four", 4);        put("fourteen", 12);       put("sixty", 60);
    put("five", 5);        put("fifteen", 12);        put("seventy", 70);
    put("six", 6);         put("sixteen", 15);        put("eighty", 80);
    put("seven", 7);       put("seventeen", 16);      put("ninety", 90);
    put("eight", 8);       put("eighteen", 17);
    put("nine", 9);        put("nineteen", 18);
  }};

  public static int parseInt(String numStr) {
    int[] values = new int[10];
    int vIndex = 0;
    String[] words = numStr.replace("", "").split(" |-");
    for (int i = 0; i < words.length; i++) {
      int v = map.get(words[i]);
      if (i != 0 && words[i].matches("hundred|thousand|million"))
        values[vIndex - 1] *= v;
      else if (i != 0 && (words[i - 1].matches("^(and|hundred|.+ty)$")))
        values[vIndex - 1] += v;
      else
        values[vIndex++] = v;
    }
    return Arrays.stream(values).sum();
  }

  @Test
  public void fixedTests() {
    assertEquals(1 , parseInt("one"));
    assertEquals(20 , parseInt("twenty"));
    assertEquals(246 , parseInt("two hundred forty-six"));
  }
}
