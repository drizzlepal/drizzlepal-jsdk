/**
 * 定义 DES（数据加密标准）对称加密算法的密钥长度。
 * 此枚举实现了 SymmetricCryptoKeySize 接口，提供标准化的密钥长度值。
 */
package com.drizzlepal.crypto.symmetric.algorithm;

import com.drizzlepal.crypto.symmetric.SymmetricCryptoKeySize;

public enum DESCryptoKeySize implements SymmetricCryptoKeySize {

    /**
     * 表示 DES 加密算法的密钥长度，固定大小为 64 位。
     */
    DES_64(64);

    private final int keySize;

    /**
     * DESCryptoKeySize 枚举的构造函数。
     * 使用指定的密钥长度初始化新的枚举实例。
     *
     * @param keySize 密钥长度，单位为 bit
     */
    DESCryptoKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * 获取当前密钥长度的值。
     *
     * @return 密钥长度（bit）
     */
    @Override
    public int getKeySize() {
        return keySize;
    }
}