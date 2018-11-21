import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import org.junit.Test;

public class ConwayLife {

  public static int[][] getGeneration(int[][] cells, int generations) {
    while (generations-- > 0) {
      cells = extend(cells);
      cells = nextGeneration(cells);
    }
    return crop(cells);
  }

  private static int[][] nextGeneration(int[][] cells) {
    int[][] result = new int[cells.length][cells[0].length];
    IntStream.range(0, cells.length).forEach(row ->
      IntStream.range(0, cells[0].length).forEach(col ->
          result[row][col] = nextCellState(cells, row, col)
      ));
    return result;
  }

  private static int nextCellState(int[][] cells, int row, int col) {
    int liveNeighboursCount = getLiveNeighboursCount(cells, row, col);
    if (cells[row][col] == 1)
      return (liveNeighboursCount < 2 || liveNeighboursCount > 3) ? 0 : 1;
    else
      return liveNeighboursCount == 3 ? 1 : 0;
  }

  private static int[][] extend(int[][] matrix) {
    int[][] extended = new int[matrix.length + 2][matrix[0].length + 2];
    IntStream.range(0, matrix.length).forEach(row -> System.arraycopy(matrix[row], 0, extended[row + 1], 1, matrix[0].length));
    return extended;
  }

  private static int[][] crop(int[][] matrix) {
    int rMin = 0, rMax = 0;
    for (int rIdx = 0; rIdx < matrix.length; rIdx++) {
      if (!isDead(matrix[rIdx])) {
        rMax = rIdx;
        if (rMin == 0) rMin = rIdx;
      }
    }

    int cMin = 0, cMax = 0;
    for (int cIdx = 0; cIdx < matrix[0].length; cIdx++) {
      if (!isDead(getCol(matrix, cIdx))) {
        cMax = cIdx;
        if (cMin == 0) cMin = cIdx;
      }
    }

    int[][] cropped = new int[rMax - rMin + 1][cMax - cMin + 1];
    for (int rIdx = 0; rIdx < cropped.length; rIdx++)
      System.arraycopy(matrix[rIdx + rMin], cMin , cropped[rIdx], 0, cropped[0].length);
    return cropped;
  }

  private static int[] getCol(int[][] matrix, int colIdx) {
    return Arrays.stream(matrix).mapToInt(row -> row[colIdx]).toArray();
  }

  private static boolean isDead(int[] row) {
    return Arrays.stream(row).noneMatch(i -> i == 1);
  }

  private static int getLiveNeighboursCount(int[][] cells, int row, int col) {
    int count = 0;
    for (int r = (row - 1 < 0) ? 0 : row - 1; r <= ((row + 1 < cells.length) ? row + 1 : cells.length - 1); r++)
      for (int c = (col - 1 < 0) ? 0 : col - 1; c <= ((col + 1 < cells[0].length) ? col + 1 : cells[0].length - 1); c++)
        if (!(r == row && c == col) && cells[r][c] == 1)
          count++;

    return count;
  }

  @Test
  public void testGlider() {
    int[][][] gliders = {
        {
            {1,0,0},
            {0,1,1},
            {1,1,0}
        },
        {
            {0,1,0},
            {0,0,1},
            {1,1,1}
        }
    };
    int[][] res = ConwayLife.getGeneration(gliders[0], 1);
    assertArrayEquals(res, gliders[1]);
  }
}
