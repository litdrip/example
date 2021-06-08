import java.math.BigDecimal;
import java.util.Random;

/**
 * 月氏琅琊 2016/12/13 0013.
 */
public class RandomMoney {
    public static void main(String[] args) {

        System.out.println(get(10,1,1));

    }

    public static BigDecimal get(int total,int number,int multiple){

        Random r = new Random();
        double money = r.nextDouble()*total/number*2;
        if(multiple>1){
            if(r.nextInt(multiple)==0){
                money=money*multiple;
            }else {
                money=money/multiple;
            }
        }

        money = money <= 0.01 ? 0.01 : money;

        return new BigDecimal(money).setScale(2, BigDecimal.ROUND_HALF_DOWN);
    }

}
