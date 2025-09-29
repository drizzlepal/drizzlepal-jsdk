package com.drizzlepal.crypto.symmetric.algorithm;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.drizzlepal.crypto.symmetric.SymmetricAlgorithm;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoAlgorithmName;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoMode;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoPadding;

/**
 * 三重DES加密算法实现类
 * 继承自SymmetricAlgorithm，实现了具体的三重DES加密算法逻辑
 */
public class DESedeAlgorithm extends SymmetricAlgorithm {

    // 定义三重DES加密算法的块大小
    private static final int DESede_BLOCK_SIZE = 8;

    /**
     * 构造函数，初始化加密模式、填充方式和密钥长度
     * 
     * @param mode    加密模式
     * @param padding 填充方式
     * @param keySize 密钥长度
     */
    public DESedeAlgorithm(SymmetricCryptoMode mode, SymmetricCryptoPadding padding,
            DESedeCryptoKeySize keySize) {
        super(mode, padding, keySize);
    }

    /**
     * 构造函数，仅初始化密钥长度
     * 
     * @param keySize 密钥长度
     */
    public DESedeAlgorithm(DESedeCryptoKeySize keySize) {
        super(keySize);
    }

    /**
     * 获取加密算法名称
     * 
     * @return 加密算法名称
     */
    @Override
    public SymmetricCryptoAlgorithmName algName() {
        return SymmetricCryptoAlgorithmName.DESede;
    }

    /**
     * 获取加密算法块大小
     * 
     * @return 块大小
     */
    @Override
    public int blockSize() {
        return DESede_BLOCK_SIZE;
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
        // 创建DESedeKeySpec对象
        DESedeKeySpec desKeySpec = new DESedeKeySpec(key);
        // 获取SecretKeyFactory对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algName().name());
        // 生成并返回SecretKey对象
        return keyFactory.generateSecret(desKeySpec);
    }

}