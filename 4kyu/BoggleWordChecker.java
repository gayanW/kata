import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Boggle Word Checker
 * https://www.codewars.com/kata/boggle-word-checker
 */
class BoggleWordChecker {

  private char[][] board;
  private char[] word;
  private boolean[][] visited;

  BoggleWordChecker(final char[][] board, final String word) {
    this.board = board;
    this.word = word.toCharArray();
    this.visited = new boolean[board.length][board[0].length];
  }

  private boolean DFS(int r, int c, int charIdx) {
    if (charIdx == word.length) return true;

    visited[r][c] = true;
    boolean found = getAdjacentNodes(r, c).stream()
        .filter(node -> !visited[node.rIndex][node.cIndex])
        .filter(node -> node.value == word[charIdx])
        .anyMatch(node -> DFS(node.rIndex, node.cIndex, charIdx + 1)
        );

    if (!found) visited[r][c] = false;
    return found;
  }

  private List<Node> getAdjacentNodes(int r, int c) {
    List<Node> nodes = new ArrayList<>();

    IntStream.rangeClosed(-1, 1).forEach(rdx ->
        IntStream.rangeClosed(-1, 1)
            .forEach(cdx -> {
              if (rdx == 0 && cdx == 0) return;
              int rIndex = r + rdx;
              int cIndex = c + cdx;
              if (isWithinBounds(rIndex, cIndex))
                nodes.add(new Node(rIndex, cIndex, board[rIndex][cIndex]));
            }));
    return nodes;
}

  private boolean isWithinBounds(int rIndex, int cIndex) {
    return rIndex >= 0 && rIndex < board.length && cIndex >= 0 && cIndex < board[0].length;
  }

  boolean check() {
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        if (board[i][j] == word[0]) {
          if (DFS(i, j, 1))
            return true;
        }
      }
    }
    return false;
  }

  private class Node {
    int rIndex, cIndex;
    char value;

    Node(int rIndex, int cIndex, char value) {
      this.rIndex = rIndex;
      this.cIndex = cIndex;
      this.value = value;
    }
  }

}



