package com.drizzlepal.crypto.exception;

/**
 * 自定义加密异常类
 * 继承自Exception，用于处理加密相关操作中的异常情况
 */
public class CryptoErrorException extends Exception {

    /**
     * 通过底层Throwable构造加密异常
     * 
     * @param e 导致此异常的Throwable对象
     */
    public CryptoErrorException(Throwable e) {
        super(e);
    }

    /**
     * 通过错误信息和底层原因构造加密异常
     * 
     * @param message 错误描述信息
     * @param cause   导致此异常的Throwable对象
     */
    public CryptoErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 通过错误信息构造加密异常
     * 
     * @param message 错误描述信息
     */
    public CryptoErrorException(String message) {
        super(message);
    }
}