package net.xuele.wisdom.assist.constant;

import java.text.MessageFormat;

/**
 * @author sk.z
 */
public enum RedisKey {
    
    //item
    //goal
    //email
    //currency
    CURRENCY_INSERT_LOCK("cy:it:lk:{0}:{1}");   //货币写入锁[0:userId][1:ccyType]

    /**
     * 获取组装key
     *
     * @param paramArr 参数数组
     */
    public String of(String... paramArr) {
        if (paramArr.length != this.paramNum) {
            throw new IllegalArgumentException("redis key 组装参数个数不符");
        }
        return MessageFormat.format(this.key, paramArr);
    }

    private final int paramNum;
    private final String key;

    RedisKey(String key) {
        this.key = "wd:" + key;

        int count = 0;
        for (char c : key.toCharArray()) {
            if (c == '{') {
                count++;
            }
        }
        this.paramNum = count;
    }
}
