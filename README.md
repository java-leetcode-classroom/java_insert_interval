# java_insert_interval

You are given an array of non-overlapping intervals `intervals` where `intervals[i] = [starti, endi]` represent the start and the end of the `ith` interval and `intervals` is sorted in ascending order by `starti`. You are also given an interval `newInterval = [start, end]` that represents the start and end of another interval.

Insert `newInterval` into `intervals` such that `intervals` is still sorted in ascending order by `starti` and `intervals` still does not have any overlapping intervals (merge overlapping intervals if necessary).

Return `intervals` *after the insertion*.

## Examples

**Example 1:**

```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]

```

**Example 2:**

```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].

```

**Constraints:**

- `0 <= intervals.length <= 104`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 105`
- `intervals` is sorted by `starti` in **ascending** order.
- `newInterval.length == 2`
- `0 <= start <= end <= 105`

## 解析

給定一個 2 維陣列 intervals ，還有一個 長度為 2 的整數陣列 newIntervals

對每個 intervals[i] = [$start_i, end_i$] 都代表一個區間，而 newIntervals = [$start_{new}, end_{new}$] 代表要放入原本 intervals 的新區間

原本的 intervals 內的每個區間彼此都不會重疊

要求放入之後如果遇到重疊的狀況下，則把 兩個區間做合併

如下圖：

![](https://i.imgur.com/g70cxFL.png)

要求寫一個演算法計算給定的 intervals 與 newInterval 合併之後的結果

在加入 newInterval 假設沒有重疊，就只要找到第一個要放入的位置，放入即可

初始化 overlapStart = newInterval[0], overlapEnd = newInterval[1], result = [], hasInsert = false

沒有重疊的情況有 當 intervals[pos][1] < newInterval[0] 或是 intervals[pos][0] > newInterval[1]

  1. intervals[pos][1] < newInterval[0] 

      把 interval[pos] 加入 result

1. intervals[pos][0] > newInterval[1]

      如果 hasInsert = false 代表在這個之前沒遇到其他重疊的 interval

      先把 [overlapStart, overlapEnd] 加入 result

     把 interval[pos] 加入 result

     hasInsert = true

有重疊的情況有 當 intervals[pos][1] ≥ newInterval[0] 或是 intervals[pos][0] ≤ newInterval[1]:

1. intervals[pos][1] ≥ overlapStart:

     如果 intervals[pos][0] ≤ overlapStart 

     更新 overlapStart = intervals[pos][0]

1. intervals[pos][0] ≤ overlapEnd :

      如果 intervals[pos][1] ≥ overlapEnd

      更新 overlapEnd = intervals[pos][1]

      把 [overlapStart, overlapEnd] 加入 result

     更新 hasInsert = true

如果執行到最後

hasInsert = false 代表 overlapEnd 超過原本的所有 interval

則把 [overlapStart, overlapEnd] 加入 result

時間複雜度是 O(n)

除了要紀錄 overlapStart, overlapEnd, 還有 hasInsert 之外

不需要額外的紀錄，空間複雜度是 O(1)

![](https://i.imgur.com/fxroncW.png)

## 程式碼
```java
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
        if (overlapStart >= interval[0]) {
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
```
## 困難點

1. 要想到找出 overlap 的起始點與終點
2. 當超過或是遇到 overlap 的終點時，則把 overlap 區間 

## Solve Point

- [x]  初始化 overlapStart = newInterval[0], overlapEnd = newInterval[1], result = [], hasInsert = false
- [x]  遍歷 intervals 找尋有重疊區塊的起點與終點
- [x]  當遇到起點時也就是 intervals[pos][1] ≥ overlapStart && intervals[pos][0] ≤ overlapStart, 更新 overlapStart = intervals[pos][0]
- [x]  當遇到終點時也就是 intervals[pos][1] ≥ overlapEnd && intervals[pos][0] ≤ overlapEnd, 更新 overlapEnd = intervals[pos][1] 並且把 [overlapStart, overlapEnd] 加入 result 並且更新 hasInsert = true 代表已經 新增完新的 interval
- [x]  當超過終點時，如果 hasInsert = false, 則需要先把 [overlapStart, overlapEnd] 加入 result 並且更新 hasInsert = true 代表已經 新增完新的 interval， 然後再加把當下 intervals[pos] 加入 result
- [x]  當走完所有結點後 如果 hasInsert = false ，需要先把 [overlapStart, overlapEnd] 加入 result 並且更新 hasInsert = true 代表已經 新增完新的 interval 然後回傳 result