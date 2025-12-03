package com.drizzlepal.crypto;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 加密随机字符串生成工具类
 * 提供生成各种加密算法所需密钥和初始向量(IV)的方法
 */
public abstract class CryptoStringRandom {

    /**
     * ASCII可读字符集，包含四组字符：
     * 1. 数字(0-9)
     * 2. 大写字母(A-Z)
     * 3. 小写字母(a-z)
     * 4. 特殊符号
     */
    private static final String[] ACII_H = {
            "0123456789", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghijklmnopqrstuvwxyz",
            "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
    };

    // 加密安全的随机数生成器
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * 生成指定长度的随机字符串
     * 
     * @param strLen 要生成的字符串长度
     * @return 随机生成的字符串
     */
    public static final String randomString(int strLen) {
        // 初始化字符列表，预分配容量
        ArrayList<Character> chars = new ArrayList<>(strLen);
        int ACII_H_P = 0; // 当前使用的字符集索引

        // 循环生成每个字符
        for (int i = 0; i < strLen; i++) {
            // 从当前字符集中随机选取一个字符
            chars.add(ACII_H[ACII_H_P].charAt(secureRandom.nextInt(ACII_H[ACII_H_P].length())));
            // 轮询使用不同字符集
            if (++ACII_H_P >= ACII_H.length) {
                ACII_H_P = 0;
            }
        }

        // 打乱字符顺序增加随机性
        Collections.shuffle(chars);

        // 将字符列表转换为字符串
        StringBuilder builder = new StringBuilder();
        chars.forEach(builder::append);
        return builder.toString();
    }

    /**
     * 生成AES-128密钥(16字节)
     */
    public static final String randomAES128Key() {
        return randomString(16);
    }

    /**
     * 生成AES-192密钥(24字节)
     */
    public static final String randomAES192Key() {
        return randomString(24);
    }

    /**
     * 生成AES-256密钥(32字节)
     */
    public static final String randomAES256Key() {
        return randomString(32);
    }

    /**
     * 生成DES密钥(8字节)
     */
    public static final String randomDESKey() {
        return randomString(8);
    }

    /**
     * 生成3DES密钥(24字节)
     */
    public static final String randomDES3Key() {
        return randomString(24);
    }

    /**
     * 生成SM4密钥(16字节)
     */
    public static final String randomSM4Key() {
        return randomString(16);
    }

    /**
     * 生成AES初始向量(IV)(16字节)
     */
    public static final String randomAESIV() {
        return randomString(16);
    }

    /**
     * 生成DES初始向量(IV)(8字节)
     */
    public static final String randomDESIV() {
        return randomString(8);
    }

    /**
     * 生成3DES初始向量(IV)(8字节)
     */
    public static final String randomDES3IV() {
        return randomString(8);
    }

    /**
     * 生成SM4初始向量(IV)(16字节)
     */
    public static final String randomSM4IV() {
        return randomString(16);
    }
}