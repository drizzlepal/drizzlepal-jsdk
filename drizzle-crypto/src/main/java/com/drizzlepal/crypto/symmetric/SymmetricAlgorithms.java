/**
 * 对称加密算法枚举类
 * 提供了一系列预定义的对称加密算法及其密钥大小
 */
package com.drizzlepal.crypto.symmetric;

import com.drizzlepal.crypto.symmetric.algorithm.AESAlgorithm;
import com.drizzlepal.crypto.symmetric.algorithm.AESCryptoKeySize;
import com.drizzlepal.crypto.symmetric.algorithm.DESAlgorithm;
import com.drizzlepal.crypto.symmetric.algorithm.DESCryptoKeySize;
import com.drizzlepal.crypto.symmetric.algorithm.DESedeAlgorithm;
import com.drizzlepal.crypto.symmetric.algorithm.DESedeCryptoKeySize;
import com.drizzlepal.crypto.symmetric.algorithm.SM4Algorithm;
import com.drizzlepal.crypto.symmetric.algorithm.SM4CryptoKeySize;

public enum SymmetricAlgorithms {

    // AES加密算法，支持不同密钥大小
    AES_128(new AESAlgorithm(AESCryptoKeySize.AES_128)),
    AES_192(new AESAlgorithm(AESCryptoKeySize.AES_192)),
    AES_256(new AESAlgorithm(AESCryptoKeySize.AES_256)),
    // DES加密算法
    DES(new DESAlgorithm(DESCryptoKeySize.DES_64)),
    // 三重DES加密算法
    DESede(new DESedeAlgorithm(DESedeCryptoKeySize.DESede_192)),
    // SM4加密算法
    SM4(new SM4Algorithm(SM4CryptoKeySize.SM4_128));

    // 对应的对称加密算法实例
    public final SymmetricAlgorithm algorithm;

    /**
     * 构造方法
     * 
     * @param algorithm 对称加密算法实例
     */
    SymmetricAlgorithms(SymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * 根据算法名称获取对应的对称加密算法实例
     * 
     * @param algorithmName 算法名称枚举
     * @return 对应的对称加密算法实例，如果找不到则返回null
     */
    public static SymmetricAlgorithm fromName(SymmetricCryptoAlgorithmName algorithmName) {
        for (SymmetricAlgorithms algorithm : values()) {
            if (algorithmName.equals(algorithm.algorithm.algName())) {
                return algorithm.algorithm;
            }
        }
        return null;
    }

}