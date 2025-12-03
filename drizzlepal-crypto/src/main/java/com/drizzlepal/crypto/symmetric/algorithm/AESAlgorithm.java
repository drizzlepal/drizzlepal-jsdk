package com.drizzlepal.crypto.symmetric.algorithm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.drizzlepal.crypto.symmetric.SymmetricAlgorithm;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoAlgorithmName;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoMode;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoPadding;

/**
 * AES对称加密算法类
 * 继承自SymmetricAlgorithm，实现了AES具体的加密算法细节
 */
public class AESAlgorithm extends SymmetricAlgorithm {

    // AES算法的固定块大小
    private static final int AES_BLOCK_SIZE = 16;

    /**
     * 构造函数
     * 初始化AES算法的加密模式、填充方式和密钥大小
     *
     * @param mode       加密模式
     * @param padding    填充方式
     * @param aesKeySize 密钥大小
     */
    public AESAlgorithm(SymmetricCryptoMode mode, SymmetricCryptoPadding padding, AESCryptoKeySize aesKeySize) {
        super(mode, padding, aesKeySize);
    }

    /**
     * 构造函数
     * 初始化AES算法的密钥大小，使用默认的加密模式和填充方式
     *
     * @param aesKeySize 密钥大小
     */
    public AESAlgorithm(AESCryptoKeySize aesKeySize) {
        super(aesKeySize);
    }

    /**
     * 获取加密算法名称
     *
     * @return 加密算法名称AES
     */
    @Override
    public SymmetricCryptoAlgorithmName algName() {
        return SymmetricCryptoAlgorithmName.AES;
    }

    /**
     * 获取AES算法的块大小
     *
     * @return 块大小16字节
     */
    @Override
    public int blockSize() {
        return AES_BLOCK_SIZE;
    }

    /**
     * 根据给定的密钥字节数组生成SecretKey对象
     *
     * @param key 密钥字节数组
     * @return SecretKey对象
     * @throws InvalidKeySpecException  如果密钥规范无效
     * @throws InvalidKeyException      如果密钥无效
     * @throws NoSuchAlgorithmException 如果没有找到对应的算法
     */
    @Override
    protected SecretKey specSecretKey(byte[] key)
            throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException {
        return new SecretKeySpec(key, algName().name());
    }

}