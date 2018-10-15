import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.Test;

/**
 * In a grid of 4 by 4 squares you want to place a skyscraper in each square with only some clues:
 *
 *     The height of the skyscrapers is between 1 and 4
 *     No two skyscrapers in a row or column may have the same number of floors
 *     A clue is the number of skyscrapers that you can see in a row or column from the outside
 *     Higher skyscrapers block the view of lower skyscrapers located behind them
 *
 * https://www.codewars.com/kata/4-by-4-skyscrapers
 */
public class SkyScrapers4x4 {

  private static List<List<Integer>> all = getCombinations(Arrays.asList(1, 2, 3, 4));

  static int[][] solvePuzzle (int[] clues) {
    int[][] result = new int[4][4];

    // iterate over rows
    for (int rowIndex = 0; anyMatch(result, 0); rowIndex = ++rowIndex % 4) {
      int[] row = result[rowIndex];
      // possible matches for the row
      List<List<Integer>> rowMatches = getMatches(row, clues[15 - rowIndex], clues[4 + rowIndex]);

      // iterate over columns
      final int finalRowIndex = rowIndex;
      IntStream.range(0, 4).forEach(cIndex -> {
        int[] col = IntStream.range(0, 4).map(j -> result[j][cIndex]).toArray();
        // possible matches for the column
        List<List<Integer>> colMatches = getMatches(col, clues[cIndex], clues[11 - cIndex]);

        // filter possible matches for the row, based on possible matches for each column
        rowMatches.removeAll(
            rowMatches.stream()
            .filter(r -> colMatches.stream().noneMatch(c -> r.get(cIndex).equals(c.get(finalRowIndex))))
            .collect(toList()));
      });

      // fill entire row
      if (rowMatches.size() == 1)
        fill(row, rowMatches.get(0));
      else {
        // fill individual blocks
        IntStream.range(0, 4).filter(index -> rowMatches.stream().mapToInt(m -> m.get(index)).distinct().count() == 1)
            .forEach(index -> row[index] = rowMatches.stream().findFirst().orElseThrow().get(index));
      }
    }
    return result;
  }

  private static List<List<Integer>> getMatches(int[] arr, int leftClue, int rightClue) {
    return all.stream()
        .filter(c -> match(c, arr))
        .filter(m -> leftClue == 0 || getClues(m, false) == leftClue)
        .filter(m -> rightClue == 0 || getClues(m, true) == rightClue).collect(toList());
  }

  private static boolean anyMatch(int[][] arr, int value) {
    return Arrays.stream(arr).flatMapToInt(Arrays::stream).anyMatch(i -> i == value);
  }

  private static int getClues(final List<Integer> list, boolean reverse) {
    List<Integer> integerList = new ArrayList<>(list);
    if (reverse) Collections.reverse(integerList);
    return (int) IntStream.range(0, 4)
        .filter(i -> integerList.get(i) > integerList.subList(0, i).stream().mapToInt(Integer::intValue).max().orElse(0))
        .count();
  }

  private static void fill(int[] arr, List<Integer> list) {
    IntStream.range(0, 4).forEach(i -> arr[i] = list.get(i));
  }

  private static boolean match(List<Integer> list, int[] arr) {
    return IntStream.range(0, 4).noneMatch(i -> arr[i] != 0 && arr[i] != list.get(i));
  }

  private static List<List<Integer>> getCombinations(final List<Integer> list) {
    if (list.size() == 1) return Collections.singletonList(list);
    List<List<Integer>> result = new ArrayList<>();
    list.forEach(item -> {
      List<Integer> rest = new ArrayList<>(list);
      rest.remove(item);
      getCombinations(rest).forEach(c -> {
        List<Integer> combination = new ArrayList<>(list.size());
        combination.add(item);
        combination.addAll(c);
        result.add(combination);
      });
    });
    return result;
  }

  private static int clues[][] = {
        { 2, 2, 1, 3,
          2, 2, 3, 1,
          1, 2, 2, 3,
          3, 2, 1, 3 },
        { 0, 0, 1, 2,
          0, 2, 0, 0,
          0, 3, 0, 0,
          0, 1, 0, 0 }
  };

  private static int outcomes[][][] = {
        { { 1, 3, 4, 2 },
          { 4, 2, 1, 3 },
          { 3, 4, 2, 1 },
          { 2, 1, 3, 4 } },
        { { 2, 1, 4, 3 },
          { 3, 4, 1, 2 },
          { 4, 2, 3, 1 },
          { 1, 3, 2, 4 } }
  };

  @Test
  public void testSolvePuzzle1 () {
    assertEquals (solvePuzzle (clues[0]), outcomes[0]);
  }

  @Test
  public void testSolvePuzzle2 () {
    assertEquals (solvePuzzle (clues[1]), outcomes[1]);
  }
}
