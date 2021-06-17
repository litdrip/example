import java.util.*;

/**
 * 列表交集运算
 *
 * @author sk.z
 */
public class IntersectIntList {

    public static Map<Integer, Integer> join(List<Integer> leftList, List<Integer> rightList) {
        if (leftList == null || leftList.isEmpty() || rightList == null || rightList.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Integer, Integer> map = new HashMap<>();

        leftList.sort(Integer::compareTo);
        rightList.sort(Integer::compareTo);

        int lIdx = 0;
        int rIdx = 0;

        while (true) {
            Integer left = leftList.get(lIdx);
            Integer right = rightList.get(rIdx);

            int compare = left.compareTo(right);
            if (compare == 0) {
                map.put(left, right);
                lIdx++;
                rIdx++;
            } else if (compare < 0) {
                lIdx++;
            } else {
                rIdx++;
            }

            if (leftList.size() <= lIdx || rightList.size() <= rIdx) {
                return map;
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> l = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 99, 21, 37, 49, 12);
        List<Integer> r = Arrays.asList(11, 12, 13, 14, 15, 16, 17, 99, 21, 37, 49, 12, 2, 4, 6);
        Map<Integer, Integer> join = join(l, r);
        join.forEach((key, value) -> System.out.println(key + " - " + value));
    }
}
