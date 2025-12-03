package com.drizzlepal.crypto;

import java.security.Key;

import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithm;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithmName;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithms;
import com.drizzlepal.crypto.exception.CryptoErrorException;

/**
 * 非对称加密工具类。
 * <p>
 * 封装常用非对称加解密接口，默认使用 SM2 算法，支持传入算法名或算法实例进行扩展。
 */
public abstract class AsymmetricCrypto {

    /**
     * 使用默认算法（SM2）加密数据。
     *
     * @param data 明文数据
     * @param key  公钥
     * @return 加密后的密文数据
     * @throws CryptoErrorException 加密异常
     */
    public static final byte[] encryptBytes(byte[] data, Key key) throws CryptoErrorException {
        return AsymmetricAlgorithms.fromName(AsymmetricAlgorithmName.SM2).encryptBytes(data, key);
    }

    /**
     * 使用默认算法（SM2）解密数据。
     *
     * @param data 密文数据
     * @param key  私钥
     * @return 解密后的明文数据
     * @throws CryptoErrorException 解密异常
     */
    public static final byte[] decryptBytes(byte[] data, Key key) throws CryptoErrorException {
        return AsymmetricAlgorithms.fromName(AsymmetricAlgorithmName.SM2).decryptBytes(data, key);
    }

    /**
     * 使用指定算法名进行加密。
     *
     * @param data          明文数据
     * @param key           公钥
     * @param algorithmName 算法枚举名（如 RSA、EC、SM2）
     * @return 加密后的密文数据
     * @throws CryptoErrorException 加密异常
     */
    public static final byte[] encryptBytes(byte[] data, Key key, AsymmetricAlgorithmName algorithmName)
            throws CryptoErrorException {
        return AsymmetricAlgorithms.fromName(algorithmName).encryptBytes(data, key);
    }

    /**
     * 使用指定算法名进行解密。
     *
     * @param data          密文数据
     * @param key           私钥
     * @param algorithmName 算法枚举名（如 RSA、EC、SM2）
     * @return 解密后的明文数据
     * @throws CryptoErrorException 解密异常
     */
    public static final byte[] decryptBytes(byte[] data, Key key, AsymmetricAlgorithmName algorithmName)
            throws CryptoErrorException {
        return AsymmetricAlgorithms.fromName(algorithmName).decryptBytes(data, key);
    }

    /**
     * 使用指定算法实例加密。
     *
     * @param data      明文数据
     * @param key       公钥
     * @param algorithm 算法实现类实例（如 new RSAAlgorithm(...)）
     * @return 加密后的密文数据
     * @throws CryptoErrorException 加密异常
     */
    public static final byte[] encryptBytes(byte[] data, Key key, AsymmetricAlgorithm algorithm)
            throws CryptoErrorException {
        return algorithm.encryptBytes(data, key);
    }

    /**
     * 使用指定算法实例解密。
     *
     * @param data      密文数据
     * @param key       私钥
     * @param algorithm 算法实现类实例（如 new RSAAlgorithm(...)）
     * @return 解密后的明文数据
     * @throws CryptoErrorException 解密异常
     */
    public static final byte[] decryptBytes(byte[] data, Key key, AsymmetricAlgorithm algorithm)
            throws CryptoErrorException {
        return algorithm.decryptBytes(data, key);
    }
}
