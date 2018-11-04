import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Sudoku Solution Validator
 * https://www.codewars.com/kata/sudoku-solution-validator
 */
public class SudokuValidator {

  public static boolean check(int[][] sudoku) {
    if (!IntStream.range(0, 9).allMatch(index ->
          // each row contain all of the digits from 1 to 9
          contains(sudoku, index, 0, 9, 1)
          &&
          // each column contain all of the digits from 1 to 9
          contains(sudoku, 0, index, 1, 9)))
      return false;

    // each of the nine 3x3 sub-grids contain all of the digits from 1 to 9
    for (int rIndex : Arrays.asList(0, 3, 6)) {
      for (int cIndex : Arrays.asList(0, 3, 6)) {
        if (!contains(sudoku, rIndex, cIndex, 3, 3)) return false;
      }
    }
    return true;
  }

  private static boolean contains(int[][] grid, int rIndex, int cIndex, int width, int height) {
    List<Integer> digits = new ArrayList<>(width * height);
    for (int r = rIndex; r < rIndex + height; r++) {
      for (int c = cIndex; c < cIndex + width; c++) {
        digits.add(grid[r][c]);
      }
    }
    return digits.stream().filter(d -> d >= 1 && d <= 10).distinct().count() == 9;
  }
}
