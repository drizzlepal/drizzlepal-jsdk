package com.drizzlepal.crypto;

import com.drizzlepal.crypto.exception.CryptoErrorException;
import com.drizzlepal.crypto.symmetric.SymmetricAlgorithm;
import com.drizzlepal.crypto.symmetric.SymmetricAlgorithms;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoAlgorithmName;

/**
 * 对称加密处理类，提供数据的加密和解密功能
 */
public class SymmetricCrypto {

    /**
     * 使用AES-256算法加密数据
     * 
     * @param data 待加密的数据
     * @param key  加密密钥
     * @return 加密后的数据
     * @throws CryptoErrorException 如果加密过程中发生错误
     */
    public static final byte[] encryptBytes(byte[] data, byte[] key)
            throws CryptoErrorException {
        // 获取AES-256加密算法实例
        SymmetricAlgorithm algorithm = SymmetricAlgorithms.AES_256.algorithm;
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据加密
        return algorithm.encryptBytes(data, key);
    }

    /**
     * 使用AES-256算法解密数据
     * 
     * @param data 待解密的数据
     * @param key  解密密钥
     * @return 解密后的数据
     * @throws CryptoErrorException 如果解密过程中发生错误
     */
    public static final byte[] decryptBytes(byte[] data, byte[] key)
            throws CryptoErrorException {
        // 获取AES-256解密算法实例
        SymmetricAlgorithm algorithm = SymmetricAlgorithms.AES_256.algorithm;
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据解密
        return algorithm.decryptBytes(data, key);
    }

    /**
     * 使用指定的对称加密算法名称加密数据
     * 
     * @param data          待加密的数据
     * @param key           加密密钥
     * @param algorithmName 加密算法名称
     * @param iv            初始向量
     * @return 加密后的数据
     * @throws CryptoErrorException 如果加密过程中发生错误或算法名称无效
     */
    public static final byte[] encryptBytes(byte[] data, byte[] key, SymmetricCryptoAlgorithmName algorithmName,
            byte... iv)
            throws CryptoErrorException {
        // 根据算法名称获取对应的加密算法实例
        SymmetricAlgorithm algorithm = SymmetricAlgorithms.fromName(algorithmName);
        // 如果算法名称无效，抛出异常
        if (algorithm == null) {
            throw new CryptoErrorException("Invalid algorithm name");
        }
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据加密
        return algorithm.encryptBytes(data, key, iv);
    }

    /**
     * 使用指定的对称加密算法名称解密数据
     * 
     * @param data          待解密的数据
     * @param key           解密密钥
     * @param algorithmName 解密算法名称
     * @param iv            初始向量
     * @return 解密后的数据
     * @throws CryptoErrorException 如果解密过程中发生错误或算法名称无效
     */
    public static final byte[] decryptBytes(byte[] data, byte[] key, SymmetricCryptoAlgorithmName algorithmName,
            byte... iv)
            throws CryptoErrorException {
        // 根据算法名称获取对应的解密算法实例
        SymmetricAlgorithm algorithm = SymmetricAlgorithms.fromName(algorithmName);
        // 如果算法名称无效，抛出异常
        if (algorithm == null) {
            throw new CryptoErrorException("Invalid algorithm name");
        }
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据解密
        return algorithm.decryptBytes(data, key, iv);
    }

    /**
     * 使用指定的对称加密算法实例加密数据
     * 
     * @param data      待加密的数据
     * @param key       加密密钥
     * @param algorithm 加密算法实例
     * @param iv        初始向量
     * @return 加密后的数据
     * @throws CryptoErrorException 如果加密过程中发生错误
     */
    public static final byte[] encryptBytes(byte[] data, byte[] key, SymmetricAlgorithm algorithm, byte... iv)
            throws CryptoErrorException {
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据加密
        return algorithm.encryptBytes(data, key, iv);
    }

    /**
     * 使用指定的对称加密算法实例解密数据
     * 
     * @param data      待解密的数据
     * @param key       解密密钥
     * @param algorithm 解密算法实例
     * @param iv        初始向量
     * @return 解密后的数据
     * @throws CryptoErrorException 如果解密过程中发生错误
     */
    public static final byte[] decryptBytes(byte[] data, byte[] key, SymmetricAlgorithm algorithm, byte... iv)
            throws CryptoErrorException {
        // 检查密钥长度是否符合算法要求
        algorithm.checkKeySize(key);
        // 执行数据解密
        return algorithm.decryptBytes(data, key, iv);
    }

}