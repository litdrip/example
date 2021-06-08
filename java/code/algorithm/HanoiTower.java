import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author sk.z
 */
public class HanoiTower {

    private static final Tower A = new Tower("A");
    private static final Tower B = new Tower("B");
    private static final Tower C = new Tower("C");
    private static final List<Tower> towerList = new ArrayList<>();

    public static void main(String[] args) {
        init();
        Show.print(A, B, C);
        move(A.getPlateList().get(A.getPlateList().size()-1), C);
        Show.print(A, B, C);
    }

    private static void move(Plate plate, Tower targetTower) {
        //移除上面的盘子
        Plate abovePlate = plate.getAbovePlate();
        if (Objects.nonNull(abovePlate)) {
            move(abovePlate, getTempTarget(abovePlate.getAtTower(), targetTower));
        }

        //移动自己到目标
        Tower atTower = plate.getAtTower();

        plate.getAtTower().pop();
        targetTower.putPlate(plate);

        System.out.println(atTower.getName() + plate.getSize() + " >> " + targetTower.getName());
        Show.print(A, B, C);

        //移回上面的盘子
        if (Objects.nonNull(abovePlate)) {
            move(abovePlate, targetTower);
        }
    }

    private static void init() {
        A.putPlate(new Plate(6));
        A.putPlate(new Plate(5));
        A.putPlate(new Plate(4));
        A.putPlate(new Plate(3));
        A.putPlate(new Plate(2));
        A.putPlate(new Plate(1));

        towerList.add(A);
        towerList.add(B);
        towerList.add(C);
    }

    //临时放上面的盘子
    private static Tower getTempTarget(Tower atTower, Tower myTarget) {
        for (Tower t : towerList) {
            if (t == atTower || t == myTarget) {
                continue;
            }
            return t;
        }
        throw new RuntimeException("no temp tower.");
    }

    /**
     * 塔
     */
    public static class Tower {
        private String name;
        private List<Plate> plateList;

        public Tower(String name) {
            this.name = name;
            this.plateList = new ArrayList<>();
        }

        public boolean canPut(Plate plate) {
            return plateList.isEmpty() || plate.lessThan(plateList.get(0));
        }

        public boolean putPlate(Plate plate) {
            if (plateList.isEmpty()) {
                plateList.add(plate);
                plate.setAtTower(this);
                return true;
            } else if (plate.lessThan(plateList.get(0))) {
                plateList.get(0).setAbovePlate(plate);
                plateList.add(0, plate);
                plate.setAtTower(this);
                return true;
            } else {
                return false;
            }
        }

        public Plate pop() {
            if (plateList.isEmpty()) {
                return null;
            }
            Plate plate = plateList.get(0);
            plate.setAtTower(null);
            plateList.remove(0);
            return plate;
        }

        public String getName() {
            return name;
        }

        public List<Plate> getPlateList() {
            return plateList;
        }
    }

    /**
     * 盘子
     */
    public static class Plate {
        private int size;
        private Tower atTower;      //所在塔
        private Plate abovePlate;   //上面的盘子

        public Plate(int size) {
            if (size < 0) {
                throw new IllegalArgumentException("plate size must greater than zero.");
            }
            this.size = size;
        }

        public boolean lessThan(Plate other) {
            return this.size < other.getSize();
        }

        public void pop() {
            if (Objects.nonNull(atTower)) {
                atTower.pop();
            }
        }

        public int getSize() {
            return size;
        }

        public Tower getAtTower() {
            return atTower;
        }

        public void setAtTower(Tower atTower) {
            this.atTower = atTower;
        }

        public Plate getAbovePlate() {
            return abovePlate;
        }

        public void setAbovePlate(Plate abovePlate) {
            this.abovePlate = abovePlate;
        }
    }

    public static class Show {
        public static void print(Tower... towers) {
            if (towers.length == 0) {
                return;
            }

            //总体宽高
            int high = 0;
            int width = 0;
            for (Tower tower : towers) {
                List<Plate> plateList = tower.getPlateList();
                high = Math.max(high, plateList.size());
                for (Plate plate : plateList) {
                    width = Math.max(width, plate.getSize());
                }
            }
            high += 2;
            width = width * 2;
            width += 6;

            //循环行
            for (int row = 0; row <= high; row++) {
                //循环塔
                for (Tower tower : towers) {
                    System.out.print(getRow(tower, row, high, width));
                }
                System.out.println();
            }
        }

        //获取一塔用于显示的某一行字符串表示
        private static String getRow(Tower tower, int row, int showHigh, int showWidth) {
            List<Plate> plateList = tower.getPlateList();
            int halfWidth = showWidth / 2;
            int index = plateList.size() - showHigh + row;

            if (row == 0){
                //塔名
                return make(' ', halfWidth, ' ', 0 , tower.getName().toCharArray()[0]);
            } else if (row == showHigh) {
                //塔基
                return make(' ', 1, '#', halfWidth - 1, '|');
            } else if (index < 0) {
                //无盘行
                return make(' ', halfWidth, ' ', 0, '|');
            } else {
                //有盘行
                int plateSize = plateList.get(index).getSize();
                return make(' ', halfWidth - plateSize, '-', plateSize, '|');
            }
        }

        //制行 三个字符及宽度
        private static String make(char first, int firstWidth, char second, int secondWidth, char centre) {
            char[] chars = new char[(firstWidth + secondWidth) * 2 + 1];

            for (int i = 0; i < firstWidth; i++) {
                chars[i] = first;
                chars[chars.length - 1 - i] = first;
            }

            for (int i = 0; i < secondWidth; i++) {
                chars[firstWidth + i] = second;
                chars[chars.length - 1 - i - firstWidth] = second;
            }

            chars[(firstWidth + secondWidth)] = centre;
            return new String(chars);
        }
    }
}




