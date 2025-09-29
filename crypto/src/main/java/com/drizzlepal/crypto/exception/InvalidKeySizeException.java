package com.drizzlepal.crypto.exception;

/**
 * 表示密钥长度不符合要求时抛出的异常
 * 继承自CryptoErrorException，用于加密操作中密钥长度校验失败场景
 */
public class InvalidKeySizeException extends CryptoErrorException {

    /**
     * 构造方法 - 通过当前密钥长度和期望长度创建异常
     * 
     * @param currentKeySize  当前实际密钥长度(单位：bit)
     * @param expectedKeySize 期望的密钥长度(单位：bit)
     *                        注意：内部会自动将expectedKeySize除以8转换为字节单位显示
     */
    public InvalidKeySizeException(int currentKeySize, int expectedKeySize) {
        super(String.format("Invalid key size: %d, expected: %d", currentKeySize,
                expectedKeySize / 8));
    }

    /**
     * 构造方法 - 通过当前密钥长度和自定义错误信息创建异常
     * 
     * @param currentKeySize 当前实际密钥长度(单位：bit)
     * @param message        自定义的期望密钥长度描述信息
     */
    public InvalidKeySizeException(int currentKeySize, String message) {
        super(String.format("Invalid key size: %d, expected: %s", currentKeySize, message));
    }
}