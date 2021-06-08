import java.util.ArrayList;
import java.util.List;

/**
 * @author sk.z
 */
public class ListUtil {
    public static <T> List<List<T>> splitList(List<T> source, int splitSize) {
        List<List<T>> result = new ArrayList<>();
        int remainder = source.size() % splitSize;
        int number = source.size() / splitSize;
        int i=0;
        for (; i < number; i++) {
            List<T> sub = source.subList(i * splitSize, (i + 1) * splitSize);
            result.add(sub);
        }
        if (remainder > 0){
            result.add(source.subList(i*splitSize,source.size()));
        }
        return result;
    }
}
