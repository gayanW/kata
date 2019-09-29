/**
 * Matrix Shift
 * https://www.codewars.com/kata/clickbait-matrix-shift-clickbait
 */
public class MatrixShift {

    public static char[][] shift(char[][] matrix, int n) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        char[][] result = new char[matrix.length][matrix[0].length];

        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                result[(r + ((c + n) / cols)) % rows] [(c + n) % cols] = matrix[r][c];

        return result;
    }
}
