package com.drizzlepal.crypto.asymmetric;

import com.drizzlepal.crypto.asymmetric.algorithm.ECCAlgorithm;
import com.drizzlepal.crypto.asymmetric.algorithm.RSAAlgorithm;
import com.drizzlepal.crypto.asymmetric.algorithm.RSACryptoKeySize;
import com.drizzlepal.crypto.asymmetric.algorithm.SM2Algorithm;

/**
 * 枚举类，定义了支持的多种非对称加密算法实例。
 * 包括RSA、ECC（椭圆曲线加密）和SM2（国密算法）的不同密钥长度和曲线类型。
 * 每个枚举实例对应一种具体的算法实现。
 */
public enum AsymmetricAlgorithms {

    /**
     * RSA算法，密钥长度1024位
     */
    RAS1024(new RSAAlgorithm(RSACryptoKeySize.RSA_1024)),

    /**
     * RSA算法，密钥长度2048位
     */
    RAS2048(new RSAAlgorithm(RSACryptoKeySize.RSA_2048)),

    /**
     * RSA算法，密钥长度3072位
     */
    RSA3072(new RSAAlgorithm(RSACryptoKeySize.RSA_3072)),

    /**
     * RSA算法，密钥长度4096位
     */
    RSA4096(new RSAAlgorithm(RSACryptoKeySize.RSA_4096)),

    /**
     * ECC算法，使用brainpoolP256r1曲线
     */
    ECCBRAINPOOLP256(new ECCAlgorithm(ECGenParameters.brainpoolP256r1)),

    /**
     * ECC算法，使用brainpoolP384r1曲线
     */
    ECCBRAINPOOLP384(new ECCAlgorithm(ECGenParameters.brainpoolP384r1)),

    /**
     * ECC算法，使用brainpoolP512r1曲线
     */
    ECCBRAINPOOLP512(new ECCAlgorithm(ECGenParameters.brainpoolP512r1)),

    /**
     * ECC算法，使用secp192r1曲线
     */
    ECCSECP192(new ECCAlgorithm(ECGenParameters.secp192r1)),

    /**
     * ECC算法，使用secp224r1曲线
     */
    ECCSECP224(new ECCAlgorithm(ECGenParameters.secp224r1)),

    /**
     * ECC算法，使用secp256r1曲线
     */
    ECCSECP256(new ECCAlgorithm(ECGenParameters.secp256r1)),

    /**
     * ECC算法，使用secp384r1曲线
     */
    ECCSECP384(new ECCAlgorithm(ECGenParameters.secp384r1)),

    /**
     * ECC算法，使用secp521r1曲线
     */
    ECCSECP521(new ECCAlgorithm(ECGenParameters.secp521r1)),

    /**
     * SM2国密算法
     */
    SM2(new SM2Algorithm());

    /**
     * 存储对应枚举实例的算法实现对象
     */
    public final AsymmetricAlgorithm algorithm;

    /**
     * 枚举构造函数，初始化算法实例
     * 
     * @param algorithm 具体的非对称算法实现对象
     */
    AsymmetricAlgorithms(AsymmetricAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public static AsymmetricAlgorithm fromName(AsymmetricAlgorithmName algorithmName) {
        switch (algorithmName.name()) {
            case "RSA":
                return RSA4096.algorithm;
            case "SM2":
                return SM2.algorithm;
            case "ECC":
                return ECCSECP521.algorithm;
            default:
                return SM2.algorithm;
        }
    }
}