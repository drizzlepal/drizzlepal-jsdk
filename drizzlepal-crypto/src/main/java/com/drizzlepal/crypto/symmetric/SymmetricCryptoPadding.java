/**
 * 对称加密填充方式枚举类
 * 在对称加密算法中，由于数据块的大小可能不是加密算法块大小的整数倍，
 * 因此需要使用填充物来补全最后一个数据块
 * 本枚举类提供了几种常见的对称加密填充方式
 */
package com.drizzlepal.crypto.symmetric;

/**
 * 对称加密填充方式枚举
 * 提供了四种填充方式：PKCS5Padding, PKCS7Padding, ZerosPadding, NoPadding
 */
public enum SymmetricCryptoPadding {
    /**
     * 使用PKCS5标准进行填充
     * 适用于块加密算法，如DES、AES等
     */
    PKCS5Padding,
    /**
     * 使用PKCS7标准进行填充
     * 是一种通用的填充机制，适用于任何块大小
     */
    PKCS7Padding,
    /**
     * 使用零字节进行填充
     * 直到数据块达到加密算法所需的长度
     */
    ZerosPadding,
    /**
     * 不使用填充
     * 当数据块恰好是加密算法块大小的整数倍时使用
     */
    NoPadding
}