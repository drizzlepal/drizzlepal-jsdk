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
 * SM4对称加密算法实现类
 * SM4是中国国家密码管理局发布的商用密码算法，用于替换DES和3DES
 */
public class SM4Algorithm extends SymmetricAlgorithm {

    // SM4算法的块大小为16字节
    private static final int SM4_BLOCK_SIZE = 16;

    /**
     * 构造函数，初始化SM4算法的加密模式、填充方式和密钥长度
     *
     * @param mode    加密模式，如CBC、ECB等
     * @param padding 填充方式，如PKCS5Padding、NoPadding等
     * @param keySize 密钥长度，SM4支持128位密钥
     */
    public SM4Algorithm(SymmetricCryptoMode mode, SymmetricCryptoPadding padding, SM4CryptoKeySize keySize) {
        super(mode, padding, keySize);
    }

    /**
     * 简化构造函数，仅指定密钥长度，使用默认的加密模式和填充方式
     *
     * @param keySize 密钥长度，SM4支持128位密钥
     */
    public SM4Algorithm(SM4CryptoKeySize keySize) {
        super(keySize);
    }

    /**
     * 获取加密算法名称
     *
     * @return 返回SM4加密算法名称
     */
    @Override
    public SymmetricCryptoAlgorithmName algName() {
        return SymmetricCryptoAlgorithmName.SM4;
    }

    /**
     * 获取加密算法的块大小
     *
     * @return 返回SM4算法的块大小，固定为16字节
     */
    @Override
    public int blockSize() {
        return SM4_BLOCK_SIZE;
    }

    /**
     * 根据给定的密钥字节数组生成SecretKey对象
     *
     * @param key 密钥字节数组
     * @return 返回生成的SecretKey对象
     * @throws InvalidKeySpecException  如果密钥规范无效
     * @throws InvalidKeyException      如果密钥无效
     * @throws NoSuchAlgorithmException 如果没有找到对应的算法
     */
    @Override
    protected SecretKey specSecretKey(byte[] key)
            throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException {
        // 使用SecretKeySpec类将密钥字节数组和算法名称传入以生成SecretKey对象
        return new SecretKeySpec(key, algName().name());
    }

}