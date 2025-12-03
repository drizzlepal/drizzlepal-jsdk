/**
 * DESede加密算法的密钥大小枚举类
 * DESede是一种对称加密算法，也称为TripleDES，它使用三个独立的密钥对数据进行三次加密
 * 本枚举类定义了DESede加密算法的密钥大小
 */
package com.drizzlepal.crypto.symmetric.algorithm;

import com.drizzlepal.crypto.symmetric.SymmetricCryptoKeySize;

/**
 * 定义DESede加密算法的密钥大小
 */
public enum DESedeCryptoKeySize implements SymmetricCryptoKeySize {

    /**
     * DESede加密算法的密钥大小为192位
     * 尽管DESede支持168位的有效密钥长度，但通常使用192位的密钥空间（每64位增加8位奇偶校验位）
     */
    DESede_192(192);

    /**
     * 存储密钥的大小
     */
    private final int keySize;

    /**
     * 构造函数，初始化密钥大小
     *
     * @param keySize 密钥的大小，以位为单位
     */
    DESedeCryptoKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * 获取密钥的大小
     *
     * @return 密钥的大小，以位为单位
     */
    @Override
    public int getKeySize() {
        return keySize;
    }

}