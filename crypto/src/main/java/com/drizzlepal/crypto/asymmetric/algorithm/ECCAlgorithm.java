package com.drizzlepal.crypto.asymmetric.algorithm;

import java.security.Key;

import javax.crypto.Cipher;

import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithm;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithmName;
import com.drizzlepal.crypto.asymmetric.ECGenParameters;
import com.drizzlepal.crypto.exception.CryptoErrorException;

/**
 * ECCAlgorithm 类实现了 ECIES（椭圆曲线集成加密方案）算法的加解密操作。
 * 继承自 AsymmetricAlgorithm，封装了基于椭圆曲线的加密逻辑。
 *
 * 算法名称：EC（Elliptic Curve）
 * 加密方案：ECIES（Elliptic Curve Integrated Encryption Scheme）
 */
public class ECCAlgorithm extends AsymmetricAlgorithm {

    /** 使用的加密方案名称（Java Cipher 算法标识） */
    private static final String CIPHER_NAME = "ECIES";

    /**
     * 构造函数，传入椭圆曲线生成参数。
     *
     * @param e 椭圆曲线参数封装类
     */
    public ECCAlgorithm(ECGenParameters e) {
        super(e.getECGenParameter());
    }

    /**
     * 返回算法名称。
     *
     * @return AsymmetricAlgorithmName 枚举值（EC）
     */
    @Override
    protected AsymmetricAlgorithmName algName() {
        return AsymmetricAlgorithmName.ECC;
    }

    /**
     * 使用公钥加密数据。
     *
     * @param data 待加密数据字节数组
     * @param key  公钥（类型为 EC 公钥）
     * @return 加密后的字节数组
     * @throws CryptoErrorException 加密过程中发生异常
     */
    @Override
    public byte[] encryptBytes(byte[] data, Key key) throws CryptoErrorException {
        try {
            Cipher encryptCipher = Cipher.getInstance(CIPHER_NAME);
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            return encryptCipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoErrorException(e);
        }
    }

    /**
     * 使用私钥解密数据。
     *
     * @param data 加密后的数据字节数组
     * @param key  私钥（类型为 EC 私钥）
     * @return 解密后的字节数组
     * @throws CryptoErrorException 解密过程中发生异常
     */
    @Override
    public byte[] decryptBytes(byte[] data, Key key) throws CryptoErrorException {
        try {
            Cipher decryptCipher = Cipher.getInstance(CIPHER_NAME);
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
            return decryptCipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoErrorException(e);
        }
    }

}
