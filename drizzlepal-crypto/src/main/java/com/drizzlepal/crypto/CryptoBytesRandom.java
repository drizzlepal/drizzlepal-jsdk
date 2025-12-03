package com.drizzlepal.crypto;

import java.security.SecureRandom;

/**
 * 用于生成安全随机字节数组的工具类
 */
public class CryptoBytesRandom {

    // 创建一个全局唯一的 SecureRandom 实例（线程安全，推荐复用）
    private static final SecureRandom secureRandom = new SecureRandom();

    /**
     * 生成指定长度的安全随机字节数组
     *
     * @param length 要生成的字节数组的长度
     * @return 随机生成的字节数组（用于密码学安全场景）
     */
    public static byte[] getSecureRandomBytes(int length) {
        // 创建一个指定长度的字节数组
        byte[] bytes = new byte[length];

        // 使用 SecureRandom 填充随机字节，具有强随机性，适用于加密用途
        secureRandom.nextBytes(bytes);

        // 返回生成的随机字节数组
        return bytes;
    }
}
