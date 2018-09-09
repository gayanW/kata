import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Write a class called User that is used to calculate the amount that a user will progress
 * through a ranking system similar to the one Codewars uses.
 * https://www.codewars.com/kata/51fda2d95d6efda45e00004e
 */
public class CodeWarsRanking {

    public class User {
        int rank;
        int progress;

        public User() {
            rank = -8;
        }

        public void incProgress(int kataRank) {
            if (!isValidRank(kataRank)) throw new IllegalArgumentException();

            int diff = kataRank - rank;
            int d = (kataRank * rank > 0) ? kataRank - rank :
                (diff > 0) ? diff - 1 : diff + 1;

            int points = 0;
            if (d == 0) points = 3;
            else if (d == -1) points = 1;
            else if (d > 0) points = 10 * d * d;

            incRank(progress + points);
            progress = (rank == 8) ? 0 : (progress + points) % 100;
        }

        private void incRank(int progress) {
            if (progress < 100) return;
            int expectedRank = rank + progress / 100; // including 0
            rank = (rank < 0 && expectedRank >= 0) ? expectedRank + 1 : expectedRank;
            if (rank >= 9) rank = 9;
        }

        private boolean isValidRank(int rank) {
            return (-8 >= rank && rank <= 8 && rank != 0);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSomething() {
        User user = new User();
        assertEquals(-8, user.rank);
        assertEquals(0, user.progress);
        user.incProgress(-7); // progress 10
        assertEquals(10, user.progress);
        user.incProgress(-5); // progress 90
        assertEquals(0, user.progress);
        assertEquals(-7, user.rank);
        user.incProgress(-3); // progress 160
        assertEquals(60, user.progress);
        assertEquals(-6, user.rank);
        user.incProgress(-1); // progress 250
        assertEquals(-3, user.rank);
        assertEquals(10, user.progress);
        user.incProgress(1); // progress 90
        assertEquals(-2, user.rank);
        assertEquals(0, user.progress);
        user.incProgress(4); // progress 250
        assertEquals(1, user.rank);
        assertEquals(50, user.progress);
        user.incProgress(-1); // progress 1
        assertEquals(51, user.progress);
    }
}
