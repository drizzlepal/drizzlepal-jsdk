package com.drizzlepal.utils;

public class StringUtils {

    /**
     * 判断字符串是否包含非空白字符内容
     * 通过反向调用isBlank()方法保持逻辑一致性
     * 
     * @param str 待检查字符串
     * @return 当字符串包含至少一个非空白字符时返回true，否则返回false
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str); // 通过逻辑反操作复用isBlank()的判断逻辑
    }

    /**
     * 判断字符串是否为空白（null或全空白字符）
     * 优化实现：通过字符遍历避免trim()的内存开销
     * 
     * @param str 待检查字符串
     * @return 当字符串为null或全部由空白字符组成时返回true
     */
    public static boolean isBlank(String str) {
        // 处理null情况
        if (str == null) {
            return true;
        }
        // 遍历检查每个字符
        for (int i = 0; i < str.length(); i++) {
            // 发现非空白字符立即返回false
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        // 全部字符均为空白时返回true
        return true;
    }
}