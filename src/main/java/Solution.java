import java.util.ArrayList;

public class Solution {
  public int[][] insert(int[][] intervals, int[] newInterval) {
    ArrayList<int[]> buffer = new ArrayList<>();
    int overlapStart = newInterval[0];
    int overlapEnd = newInterval[1];
    boolean hasInserted = false;
    for (int[] interval: intervals) {
      if (overlapStart > interval[1]) {
        buffer.add(interval);
      } else if (overlapEnd < interval[0]) {
        if (!hasInserted) {
          buffer.add(new int[]{overlapStart, overlapEnd});
          hasInserted = true;
        }
        buffer.add(interval);
      } else { // find overlap start and end
        if (overlapStart <= interval[1] && overlapStart >= interval[0]) {
          overlapStart = interval[0];
        }
        if (overlapEnd <= interval[1] && overlapEnd >= interval[0]) {
          overlapEnd = interval[1];
        }
      }
    }
    if (!hasInserted) {
      buffer.add(new int[]{overlapStart, overlapEnd});
      hasInserted = true;
    }
    int Len = buffer.size();
    int[][] result = new int[Len][2];
    for (int pos = 0; pos < Len; pos++) {
      result[pos] =  buffer.get(pos);
    }
    return result;
  }
}
