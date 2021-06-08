package codec;

import java.util.Objects;
import java.util.Random;

/**
 * 密码 编码 解码
 * 编码后依旧是字母数字组合 大小写敏感
 * 过程就是字符替换
 * 编码：
 * 1.随机数值作为第一个字符的偏移量。
 * 2.随后字符的偏移量:((后前密文位置/2)+前偏移量)%字符数量。
 * 3.最后计算尾值，到达密文所有位置相加%字符数量的结果是第一个偏移量。
 */
public class Password {

    private static final char[] WORD_ARRAY = new char[]{'\n', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '!', '@', '#', '$', '%', '^', '&', '*', '(', ')'};

    private static final int LENGTH = WORD_ARRAY.length - 1;

    public static String encode(String input) {
        if (Objects.isNull(input) || input.length() < 7) {
            return null;
        }

        Random random = new Random();
        int startOffset = random.nextInt(LENGTH) + 1;
        int offset = startOffset;

        char[] chars = input.toCharArray();
        char[] result = new char[chars.length + 1];
        int sum = 0;

        for (int i = 0; i < chars.length; i++) {
            int index = getIndex(chars[i]);
            if (index < 0) {
                System.out.println("no support : [" + chars[i] + "]");
                return null;
            }
            int move = move(index, offset);
            sum += move;
            result[i] = WORD_ARRAY[move];

            offset = (move / 2 + offset) % LENGTH;
        }

        int last = 0;
        int sumMould = sum % LENGTH;
        if (sumMould != startOffset) {
            if (startOffset > sumMould) {
                last = startOffset - sumMould;
            } else {
                last = startOffset + LENGTH - sumMould;
            }
        }
        result[chars.length] = WORD_ARRAY[last];

        return new String(result);
    }

    public static String decode(String input) {
        if (Objects.isNull(input) || input.equals("")) {
            return null;
        }

        char[] chars = input.toCharArray();
        char[] result = new char[chars.length - 1];
        int sum = 0;
        for (char c : chars) {
            sum += getIndex(c);
        }
        int offset = sum % LENGTH;

        for (int i = 0; i < chars.length - 1; i++) {
            int index = getIndex(chars[i]);
            int move = move(index, -offset);
            result[i] = WORD_ARRAY[move];

            offset = ((index / 2) + offset) % LENGTH;
        }

        return new String(result);
    }

    private static int getIndex(char c) {
        for (int i = 1; i <= LENGTH; i++) {
            if (c == WORD_ARRAY[i]) {
                return i;
            }
        }
        return -1;
    }

    private static int move(int index, int offset) {
        int result = index + offset;
        if (result > LENGTH) {
            return result - LENGTH;
        } else if (result <= 0) {
            return result + LENGTH;
        }
        return result;
    }

}
