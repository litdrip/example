package codec;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 账号编码
 * 用于账号生成编码 对外使用邀请码使用
 */
public class UserIdCode {

    public static long key = 7777777777L;

    public static String userIdEncode(Long userId) {
        return longToString(encode(userId));
    }

    public static Long userIdDecode(String code) {
        return decode(stringToLong(code));
    }

    private static Long encode(Long code) {
        long l = code;
        long tmp = 1;
        while (l > 0) {
            tmp = tmp + (l % 10);
            l = l / 10;
        }
        tmp = tmp % 10;
        if (tmp == 0) {
            tmp = 1;
        }
        return ((code * tmp * 10) + tmp) ^ key;
    }

    private static Long decode(Long code) {
        long l = code ^ key;
        long tmp = l % 10;
        return (l / 10) / tmp;
    }

    /*
     * A-Z 65-90
     * a-z 97-122
     * 个位编码 随机生成十位 6-12
     * 60-64 91-96 123-129 需处理
     */
    private static String longToString(Long code) {
        StringBuilder sb = new StringBuilder();
        long l = code;
        int tmp;
        while (l > 0) {
            tmp = ((ThreadLocalRandom.current().nextInt(7) + 6) * 10) + (int) (l % 10);
            if (tmp <= 64) {
                tmp = tmp + 10;
            } else if (tmp >= 91 && tmp <= 96) {
                tmp = tmp + 10;
            } else if (tmp >= 123) {
                tmp = tmp - 10;
            }
            sb.append((char) tmp);
            l = l / 10;
        }
        return sb.toString();
    }

    private static Long stringToLong(String code) {
        Long result = 0L;
        long i = 1;
        for (byte b : code.getBytes()) {
            result = result + (((((int) b) & 0xff) % 10) * i);
            i = i * 10;
        }
        return result;
    }

}
