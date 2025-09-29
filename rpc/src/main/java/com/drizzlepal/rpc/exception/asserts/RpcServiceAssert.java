package com.drizzlepal.rpc.exception.asserts;

import java.util.Map;

import com.drizzlepal.rpc.exception.ServiceException;

/**
 * RPC 服务断言工具类
 */
public class RpcServiceAssert {

    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new ServiceException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new ServiceException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new ServiceException(message);
        }
    }

    public static void hasLength(String text, String message) {
        if (text == null || text.length() == 0) {
            throw new ServiceException(message);
        }
    }

    public static void hasText(String text, String message) {
        if (text == null || text.trim().length() == 0) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new ServiceException(message);
        }
    }

    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) {
                    throw new ServiceException(message);
                }
            }
        }
    }

    public static void notEmpty(Iterable<?> iterable, String message) {
        if (iterable == null || !iterable.iterator().hasNext()) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new ServiceException(message);
        }
    }

    public static void notEmpty(String text, String message) {
        if (text == null || text.isEmpty()) {
            throw new ServiceException(message);
        }
    }

    public static void isEmpty(Object[] array, String message) {
        if (array != null && array.length > 0) {
            throw new ServiceException(message);
        }
    }

    public static void isEmpty(Iterable<?> iterable, String message) {
        if (iterable != null && iterable.iterator().hasNext()) {
            throw new ServiceException(message);
        }
    }

    public static void isEmpty(Map<?, ?> map, String message) {
        if (map != null && !map.isEmpty()) {
            throw new ServiceException(message);
        }
    }

    public static void isEmpty(String text, String message) {
        if (text != null && !text.isEmpty()) {
            throw new ServiceException(message);
        }
    }

    public static void equals(Object o1, Object o2, String message) {
        if (o1 == null ? o2 != null : !o1.equals(o2)) {
            throw new ServiceException(message);
        }
    }

    public static void notEquals(Object o1, Object o2, String message) {
        if (o1 == null ? o2 == null : o1.equals(o2)) {
            throw new ServiceException(message);
        }
    }

    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new ServiceException(message);
        }
    }

}
