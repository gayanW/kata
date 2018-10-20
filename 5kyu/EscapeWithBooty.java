import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Escape with your booty!
 * https://www.codewars.com/kata/escape-with-your-booty
 */
public class EscapeWithBooty {

  private static Ship pirateShip;

  private static int X_BOUND, Y_BOUND;

  static boolean checkCourse(char[][] map) {
    Y_BOUND = map.length - 1;
    X_BOUND = map[0].length - 1;

    final List<Ship> navyShips = new ArrayList<>();

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[0].length; j++) {
        switch (map[i][j]) {
          case 'X':
            pirateShip = new Ship(j, i, 1, 0);
            break;
          case 'N':
            navyShips.add(new Ship(j, i, 0, i == 0 ? 1 : -1));
            break;
        }
      }
    }

    return IntStream.range(0, X_BOUND).noneMatch(x -> {
      pirateShip.move();
      navyShips.forEach(Ship::move);
      return navyShips.stream().anyMatch(navyShip -> navyShip.isAdjacent(pirateShip));
    });
  }

  static class Ship {
    private int x, y;
    private int incX, incY;

    Ship(int x, int y, int incX, int incY) {
      this.x = x;
      this.y = y;
      this.incX = incX;
      this.incY = incY;
    }

    void move() {
      int x1 = x + incX;
      int y1 = y + incY;

      if (isOutOfBound(x1, y1)) {
        reverse();
        move();
      } else {
        x = x1;
        y = y1;
      }
    }

    boolean isOutOfBound(int x, int y) {
      return x < 0 || x > X_BOUND || y < 0 || y > Y_BOUND;
    }

    void reverse() {
      incX = -incX;
      incY = -incY;
    }

    boolean isAdjacent(Ship ship) {
      return  abs(y - ship.y) <= 1 && abs(x - ship.x) <= 1;
    }
  }
}
