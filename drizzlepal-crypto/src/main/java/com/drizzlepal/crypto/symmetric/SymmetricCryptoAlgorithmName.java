/**
 * 对称加密算法名称枚举类
 * 该枚举类定义了项目中可能使用的对称加密算法名称
 * 对称加密是指加密和解密使用相同密钥的加密方法
 */
package com.drizzlepal.crypto.symmetric;

/**
 * 定义对称加密算法名称的枚举
 * 枚举包括：AES、DES、DESede和SM4
 * 这些算法名称用于在加密和解密过程中选择对应的算法
 */
public enum SymmetricCryptoAlgorithmName {
    /**
     * 高级加密标准（Advanced Encryption Standard），一种对称加密算法
     */
    AES,
    /**
     * 数据加密标准（Data Encryption Standard），一种较老的对称加密算法
     */
    DES,
    /**
     * 三重数据加密标准（Triple Data Encryption Standard），DES的加强版
     */
    DESede,
    /**
     * 国产对称加密算法（SMS4），用于无线局域网标准WAPI中的数据加密
     */
    SM4
}