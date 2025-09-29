package com.drizzlepal.utils;

/**
 * ExceptionUtils类提供了异常处理相关的工具方法
 */
public class ExceptionUtils {

    /**
     * 递归获取异常的最根本原因或异常信息
     * 
     * @param e Throwable对象，可以是Exception或Error的实例
     * @return 最根本异常的原因或信息如果异常没有进一步的cause，则返回异常的直接信息
     */
    public static String getBasedExceptionMessage(Throwable e) {
        // 检查异常是否有进一步的cause
        if (e.getCause() != null) {
            // 如果有进一步的cause，则递归调用本方法获取最根本的异常信息
            return getBasedExceptionMessage(e.getCause());
        }
        // 如果没有进一步的cause，则返回当前异常的信息
        return e.getMessage();
    }

}