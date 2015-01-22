/**
 * 
 */
package com.r.core.util;

import org.junit.Test;

/**
 * @author rain
 *
 */
public class RandomUtilTest {

    @Test
    public void test() {

        int ht = 0;

        int size = 100000000;
        for (int hit = 0; hit <= size; hit++) {
            if (RandomUtil.hitRate(2, 1000)) {
                ht++;
            }
        }

        System.out.println(StrUtil.formart("进行{}次判断,命中{}次.成功率{}", size, ht, ((float) ht) / size));

    }
}
