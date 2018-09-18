import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import org.junit.Test;

public class CheckFlush {

  private static boolean checkIfFlush(String[] cards){
    final char suit = suit(cards[0]);
    return Arrays.stream(cards).allMatch(c -> suit(c) == suit);
  }

  private static char suit(String card) {
    return card.charAt(card.length() - 1);
  }

  @Test
  public void basicTests() {
    assertTrue(checkIfFlush(new String[]{"AS", "3S", "9S", "KS", "4S"}));
    assertFalse(checkIfFlush(new String[]{"AD", "4S", "7H", "KC", "5S"}));
    assertFalse(checkIfFlush(new String[]{"AD", "4S", "10H", "KC", "5S"}));
    assertTrue(checkIfFlush(new String[]{"QD", "4D", "10D", "KD", "5D"}));
  }
}