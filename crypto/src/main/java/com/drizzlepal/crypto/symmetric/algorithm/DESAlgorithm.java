package com.drizzlepal.crypto.symmetric.algorithm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.drizzlepal.crypto.symmetric.SymmetricCryptoMode;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoPadding;
import com.drizzlepal.crypto.symmetric.SymmetricAlgorithm;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoAlgorithmName;

/**
 * DES对称加密算法实现类
 * 继承自SymmetricAlgorithm，实现了DES特定的加密算法逻辑
 */
public class DESAlgorithm extends SymmetricAlgorithm {

    // DES加密算法的块大小，固定为8字节
    private static final int DES_BLOCK_SIZE = 8;

    /**
     * 构造函数
     * 初始化DES算法的加密模式、填充方式和密钥长度
     *
     * @param mode    加密模式
     * @param padding 填充方式
     * @param keySize 密钥长度
     */
    public DESAlgorithm(SymmetricCryptoMode mode, SymmetricCryptoPadding padding, DESCryptoKeySize keySize) {
        super(mode, padding, keySize);
    }

    /**
     * 简化构造函数
     * 仅指定密钥长度，使用默认的加密模式和填充方式
     *
     * @param keySize 密钥长度
     */
    public DESAlgorithm(DESCryptoKeySize keySize) {
        super(keySize);
    }

    /**
     * 获取加密算法名称
     *
     * @return 加密算法名称
     */
    @Override
    public SymmetricCryptoAlgorithmName algName() {
        return SymmetricCryptoAlgorithmName.DES;
    }

    /**
     * 获取加密算法的块大小
     *
     * @return 块大小，单位为字节
     */
    @Override
    public int blockSize() {
        return DES_BLOCK_SIZE;
    }

    /**
     * 根据提供的密钥字节数组生成SecretKey对象
     *
     * @param key 密钥字节数组
     * @return SecretKey对象
     * @throws InvalidKeySpecException  如果密钥规范无效
     * @throws InvalidKeyException      如果密钥无效
     * @throws NoSuchAlgorithmException 如果没有找到指定的算法
     */
    @Override
    protected SecretKey specSecretKey(byte[] key)
            throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException {
        // 创建DESKeySpec对象，使用提供的密钥字节数组
        DESKeySpec desKeySpec = new DESKeySpec(key);
        // 获取SecretKeyFactory对象，用于生成SecretKey
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algName().name());
        // 使用DESKeySpec生成SecretKey对象
        return keyFactory.generateSecret(desKeySpec);
    }

}