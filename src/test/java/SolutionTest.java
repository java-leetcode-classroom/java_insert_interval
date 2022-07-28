import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
  final private Solution sol = new Solution();

  @Test
  void insertExample1() {
    assertArrayEquals(new int[][]{{1, 5}, {6, 9}}, sol.insert(new int[][]{
        {1, 3}, {6, 9}
    }, new int[]{2, 5}));
  }

  @Test
  void insertExample2() {
    assertArrayEquals(new int[][]{
        {1,2},{3,10},{12,16}
    }, sol.insert(new int[][]{
        {1,2},{3,5},{6,7},{8,10},{12,16}
    }, new int[]{4, 8}));
  }

  @Test
  void insertExample3() {
    assertArrayEquals(new int[][]{
        {4, 8}
    }, sol.insert(new int[][]{}, new int[]{4, 8}));
  }
}