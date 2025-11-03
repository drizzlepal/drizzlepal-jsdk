/**
 * SM4对称加密算法密钥大小枚举类
 * 实现了SymmetricCryptoKeySize接口，用于定义SM4加密算法支持的密钥大小
 */
package com.drizzlepal.crypto.symmetric.algorithm;

import com.drizzlepal.crypto.symmetric.SymmetricCryptoKeySize;

/**
 * 定义SM4加密算法的密钥大小
 */
public enum SM4CryptoKeySize implements SymmetricCryptoKeySize {

    /**
     * SM4加密算法支持的密钥大小为128位
     */
    SM4_128(128);

    /**
     * 存储密钥大小的变量
     */
    private final int keySize;

    /**
     * 构造函数，初始化密钥大小
     * 
     * @param keySize 密钥大小，单位为位
     */
    SM4CryptoKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * 获取密钥大小
     * 
     * @return 密钥大小，单位为位
     */
    @Override
    public int getKeySize() {
        return keySize;
    }

}