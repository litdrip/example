import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author sk.z
 * 使用时间分页碰到相同时间问题
 * <p>
 * 通常碰到排序的值非唯一时,可以通过加入副排序来解决问题.
 * 此方案提供了另一种不需要副排序的解决方案.
 * 注意:方案只适合那种极少可能碰到同值的情况.
 * <p>
 * 解决方法宗旨就是确保返回列表的末尾时间值,不存在在后续页.
 * 首先想到的是将末尾时间值到库里再查一次确定数量,但这样操作浪费资源性能.
 * 而反过来想,末尾记录可能存在同值记录,那么直接将其从列表中剔除即可.
 * 这样剔除后的列表末尾记录的时间肯定是后续不再出现的.
 * <p>
 * 衍生:
 * 如果末尾记录连续多个记录时间相同,那可以将多个记录剔除.
 * 分页如果需要确定数量,可以在查寻库时多找1条或几条,用来剔除.
 */
public class PageByTimeSameValue {

    private List<Record> data;

    {
        data = new ArrayList<>();
        data.add(new Record("A", 1));
        data.add(new Record("B", 2));
        data.add(new Record("C", 3));
        data.add(new Record("D", 4));
        data.add(new Record("E", 5));
        data.add(new Record("F", 5));
        data.add(new Record("G", 6));
        data.add(new Record("H", 7));
        data.add(new Record("I", 8));
        data.add(new Record("J", 9));
    }

    public static void main(String[] args) {
        PageByTimeSameValue example = new PageByTimeSameValue();

        System.out.println("问题示例:");
        int count = 1;
        int time = 0;
        int size = 5;
        List<Record> page = example.page(time, size);
        while (page.size() > 0) {
            System.out.println("第" + count + "页");
            count++;
            for (Record r : page) {
                r.show();
            }
            System.out.println("------------");
            time = page.get(page.size() - 1).time;
            page = example.page(time, size);
        }

        System.out.println("\n\n解决示例:");
        count = 1;
        time = 0;
        size = 6;
        page = example.page(time, size);
        while (page.size() > 0) {
            if (page.size() == size) {
                example.removeUncertainTail(page);
            }
            System.out.println("第" + count + "页");
            count++;
            for (Record r : page) {
                r.show();
            }
            System.out.println("------------");
            time = page.get(page.size() - 1).time;
            page = example.page(time, size);
        }
    }

    private List<Record> page(int time, int size) {
        List<Record> list = new ArrayList<>();
        for (Record r : this.data) {
            if (r.time > time) {
                list.add(r);
                if (list.size() >= size) {
                    return list;
                }
            }
        }
        return list;
    }

    private void removeUncertainTail(List<Record> list) {
        for (int i = (list.size() - 1); i > 0; i--) {
            if (list.get(i).time == list.get(i - 1).time) {
                list.remove(i);
            } else {
                list.remove(i);
                return;
            }
        }
        throw new IllegalStateException("当页所有记录时间值一样");
    }

    private static class Record {
        String id;
        int time;

        Record(String id, int time) {
            this.id = id;
            this.time = time;
        }

        private void show() {
            System.out.println(id + "\t" + time);
        }
    }

}
