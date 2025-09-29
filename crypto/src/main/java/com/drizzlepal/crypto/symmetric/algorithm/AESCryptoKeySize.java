/**
 * AES加密密钥大小枚举类
 * 提供符合AES加密标准的密钥大小选项
 */
package com.drizzlepal.crypto.symmetric.algorithm;

import com.drizzlepal.crypto.symmetric.SymmetricCryptoKeySize;

/**
 * AES加密密钥大小枚举
 * 实现了SymmetricCryptoKeySize接口，用于获取支持的密钥大小
 */
public enum AESCryptoKeySize implements SymmetricCryptoKeySize {
    /**
     * 128位AES加密密钥大小
     */
    AES_128(128),
    /**
     * 192位AES加密密钥大小
     */
    AES_192(192),
    /**
     * 256位AES加密密钥大小
     */
    AES_256(256);

    // 密钥大小
    private int keySize;

    /**
     * 构造函数，初始化密钥大小
     *
     * @param keySize 密钥大小，单位为位
     */
    AESCryptoKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * 获取密钥大小
     *
     * @return 密钥大小，单位为位
     */
    public int getKeySize() {
        return keySize;
    }
}