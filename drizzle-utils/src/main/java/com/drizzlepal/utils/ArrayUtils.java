/**
 * ArrayUtils 类提供了数组操作的常用工具方法。
 * 该类主要用于检查数组的状态，包含判断是否为空等方法。
 */
package com.drizzlepal.utils;

public class ArrayUtils {

    /**
     * 判断指定数组是否为空或长度为0。
     *
     * @param array 要检查的数组对象，可能为 null。
     * @return 如果数组为 null 或者长度为 0，则返回 true；否则返回 false。
     */
    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 判断指定数组是否非空且长度大于0。
     *
     * @param array 要检查的数组对象，可能为 null。
     * @return 如果数组不为 null 并且长度大于 0，则返回 true；否则返回 false。
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

}