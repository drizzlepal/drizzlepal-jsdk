package com.drizzlepal.crypto.asymmetric.algorithm;

/**
 * RSA 密钥长度枚举类型。
 * <p>
 * 每个枚举值代表一种 RSA 密钥位数，常用于初始化密钥生成参数。
 * 常见取值：1024、2048、3072、4096 位。
 */
public enum RSACryptoKeySize {
    /** 1024 位密钥（安全性较低，已不推荐） */
    RSA_1024(1024),

    /** 2048 位密钥（常用，兼顾性能与安全） */
    RSA_2048(2048),

    /** 3072 位密钥（高安全级别，性能略低） */
    RSA_3072(3072),

    /** 4096 位密钥（更高安全级别，性能开销大） */
    RSA_4096(4096);

    /** 密钥长度（单位：位） */
    private final int keySize;

    /**
     * 构造函数，传入密钥长度。
     *
     * @param keySize 密钥位数
     */
    RSACryptoKeySize(int keySize) {
        this.keySize = keySize;
    }

    /**
     * 获取密钥长度（单位：位）。
     *
     * @return RSA 密钥长度
     */
    public int getKeySize() {
        return keySize;
    }
}
