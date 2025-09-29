/**
 * 对称加密算法抽象类，提供对称加密的一些通用功能
 * 该类支持不同的加密模式、填充方式和密钥大小，并提供了加密和解密的方法
 */
package com.drizzlepal.crypto.symmetric;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.drizzlepal.crypto.exception.CryptoErrorException;
import com.drizzlepal.crypto.exception.InvalidKeySizeException;

public abstract class SymmetricAlgorithm {

    // 定义使用的安全提供者，这里使用Bouncy Castle
    protected static final String PROVIDER = "BC";

    // 静态代码块，确保Bouncy Castle提供者被加载
    static {
        if (Security.getProvider(PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    // 加密模式
    protected final SymmetricCryptoMode mode;

    // 填充方式
    protected final SymmetricCryptoPadding padding;

    // 密钥大小
    protected final SymmetricCryptoKeySize keySize;

    // 构造函数，初始化加密模式、填充方式和密钥大小
    public SymmetricAlgorithm(SymmetricCryptoMode mode, SymmetricCryptoPadding padding,
            SymmetricCryptoKeySize keySize) {
        this.mode = mode;
        this.padding = padding;
        this.keySize = keySize;
    }

    // 简化构造函数，默认使用CBC模式和PKCS7填充
    public SymmetricAlgorithm(SymmetricCryptoKeySize keySize) {
        this.mode = SymmetricCryptoMode.ECB;
        this.padding = SymmetricCryptoPadding.PKCS7Padding;
        this.keySize = keySize;
    }

    // 抽象方法，返回算法名称
    public abstract SymmetricCryptoAlgorithmName algName();

    // 抽象方法，返回块大小
    public abstract int blockSize();

    // 检查密钥大小是否合法
    public void checkKeySize(byte[] key) throws InvalidKeySizeException {
        if (key.length * 8 != keySize.getKeySize()) {
            throw new InvalidKeySizeException(key.length, keySize.getKeySize());
        }
    }

    // 抽象方法，根据字节数组生成密钥
    protected abstract SecretKey specSecretKey(byte[] key)
            throws InvalidKeySpecException, InvalidKeyException, NoSuchAlgorithmException;

    // 加密方法
    public byte[] encryptBytes(byte[] data, byte[] key, byte... iv) throws CryptoErrorException {
        try {
            // 根据算法名称、模式和填充创建Cipher实例
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", algName(), mode, padding),
                    PROVIDER);
            // 根据模式决定是否需要初始化向量
            if (mode == SymmetricCryptoMode.ECB) {
                cipher.init(Cipher.ENCRYPT_MODE, specSecretKey(key));
            } else {
                if (iv == null || iv.length != blockSize()) {
                    throw new CryptoErrorException(
                            String.format("Invalid iv length %d, must be %d", iv.length,
                                    blockSize()));
                }
                cipher.init(Cipher.ENCRYPT_MODE, specSecretKey(key), new IvParameterSpec(iv));
            }
            // 执行加密操作
            return cipher.doFinal(data);
        } catch (Throwable e) {
            // 捕获异常并重新抛出自定义异常
            throw new CryptoErrorException(e.getMessage(), e);
        }
    }

    // 解密方法
    public byte[] decryptBytes(byte[] data, byte[] key, byte... iv) throws CryptoErrorException {
        try {
            // 根据算法名称、模式和填充创建Cipher实例
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/%s", algName(), mode, padding),
                    PROVIDER);
            // 根据模式决定是否需要初始化向量
            if (mode == SymmetricCryptoMode.ECB) {
                cipher.init(Cipher.DECRYPT_MODE, specSecretKey(key));
            } else {
                if (iv == null || iv.length != blockSize()) {
                    throw new CryptoErrorException(
                            String.format("Invalid iv length %d, must be %d", iv.length,
                                    blockSize()));
                }
                cipher.init(Cipher.DECRYPT_MODE, specSecretKey(key), new IvParameterSpec(iv));
            }
            // 执行解密操作
            return cipher.doFinal(data);
        } catch (Throwable e) {
            // 捕获异常并重新抛出自定义异常
            throw new CryptoErrorException(e.getMessage(), e);
        }
    }

}