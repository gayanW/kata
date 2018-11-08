/**
 * Snail -  Given an n x n array, return the array elements
 * arranged from outermost elements to the middle element, traveling clockwise.
 * https://www.codewars.com/kata/snail
 */
public class Snail {

  private static class MoveDir {
    private int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private int dr = directions[0][0], dc = directions[0][1];
    private int index = 0;

    private void next() {
      index = ++index % directions.length;
      dr = directions[index][0];
      dc = directions[index][1];
    }
  }

  private static int[] result;
  private static int resultIdx;
  private static int rIndex, cIndex;
  private static MoveDir moveDir;

  private static int[] snail(int[][] array) {
    result = new int[array.length * array.length];
    resultIdx = 0;
    rIndex = 0; cIndex = -1;
    moveDir = new MoveDir();

    for (int i = array.length; i > 0; i--) {
      if (i == array.length) traverse(i, array);
      else {
        moveDir.next();
        traverse(i, array);
        moveDir.next();
        traverse(i, array);
      }
    }
    return result;
  }

  private static void traverse(int steps, int[][] array) {
    for (int j = 0; j < steps; j++) {
      rIndex += moveDir.dr;
      cIndex += moveDir.dc;
      result[resultIdx++] = array[rIndex][cIndex];
    }
  }
}

