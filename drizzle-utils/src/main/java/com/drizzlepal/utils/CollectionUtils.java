/**
 * 工具类，用于对集合进行操作。
 */
package com.drizzlepal.utils;

import java.util.Collection;

/**
 * CollectionUtils 类提供了对集合进行操作的实用方法。
 * 包含检查集合是否为空或非空的方法。
 */
public class CollectionUtils {

    /**
     * 判断集合是否为空。
     *
     * @param c 集合对象，可以为 null
     * @return 如果集合为 null 或者大小为 0，则返回 true；否则返回 false
     */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.size() == 0;
    }

    /**
     * 判断集合是否非空。
     *
     * @param c 集合对象，可以为 null
     * @return 如果集合不为 null 并且大小大于 0，则返回 true；否则返回 false
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }

}