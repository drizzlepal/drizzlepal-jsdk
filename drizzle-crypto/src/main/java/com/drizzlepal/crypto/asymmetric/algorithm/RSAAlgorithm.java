package com.drizzlepal.crypto.asymmetric.algorithm;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.StringJoiner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.drizzlepal.crypto.BytesConvertUtils;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithm;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithmName;
import com.drizzlepal.crypto.exception.CryptoErrorException;

/**
 * 基于 RSA 算法的加解密实现。
 * 支持 PKCS1Padding 模式分块处理。
 */
public class RSAAlgorithm extends AsymmetricAlgorithm {

    /** 每次加密的最大块大小（字节），= 密钥长度 / 8 - 11（PKCS1 padding overhead） */
    private final int blockSize;

    /**
     * 构造 RSA 算法实例，使用指定密钥长度初始化。
     *
     * @param keySize RSA 密钥长度封装对象
     */
    public RSAAlgorithm(RSACryptoKeySize keySize) {
        super(new RSAKeyGenParameterSpec(keySize.getKeySize(), RSAKeyGenParameterSpec.F4));
        this.blockSize = keySize.getKeySize() / 8 - 11;
    }

    @Override
    protected AsymmetricAlgorithmName algName() {
        return AsymmetricAlgorithmName.RSA;
    }

    /** Cipher 算法标识：RSA 算法 + ECB 模式 + PKCS1Padding 填充 */
    private static final String CipherInstanceName = "RSA/ECB/PKCS1Padding";

    /** 分块之间的分隔符，用于拼接加密结果 */
    private static final String BlockSplit = ">>-<<";

    /**
     * 加密或解密单个数据块。
     *
     * @param data       原始数据块
     * @param key        加密/解密所用的密钥（公钥或私钥）
     * @param cryptoMode Cipher 模式（Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE）
     * @return 处理后的字节数组
     * @throws CryptoErrorException 加解密异常封装
     */
    private byte[] cryptoBlock(byte[] data, Key key, int cryptoMode) throws CryptoErrorException {
        try {
            Cipher cipher = Cipher.getInstance(CipherInstanceName);
            cipher.init(cryptoMode, key);
            return cipher.doFinal(data);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException
                | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CryptoErrorException(e);
        }
    }

    /**
     * 对输入字节数组进行 RSA 分块加密。
     *
     * @param data 待加密数据
     * @param key  公钥
     * @return 加密后的字符串字节（UTF-8 编码）
     * @throws CryptoErrorException 加密异常
     */
    @Override
    public byte[] encryptBytes(byte[] data, Key key) throws CryptoErrorException {
        StringJoiner joiner = new StringJoiner(BlockSplit);
        byte[] temp;

        // 块加密处理
        for (int i = 0; i < data.length / blockSize; i++) {
            temp = new byte[blockSize];
            System.arraycopy(data, i * blockSize, temp, 0, blockSize);
            joiner.add(BytesConvertUtils.bytesToHexString(cryptoBlock(temp, key, Cipher.ENCRYPT_MODE)));
        }

        // 处理最后不足 blockSize 的块
        int leftLength = data.length % blockSize;
        if (leftLength != 0) {
            temp = new byte[leftLength];
            System.arraycopy(data, data.length - leftLength, temp, 0, leftLength);
            joiner.add(BytesConvertUtils.bytesToHexString(cryptoBlock(temp, key, Cipher.ENCRYPT_MODE)));
        }

        // 最终拼接为字符串并转回字节数组
        return BytesConvertUtils.utf8StringToBytes(joiner.toString());
    }

    /**
     * 对加密后的数据进行解密（支持分块合并）。
     *
     * @param data 加密数据（UTF-8 字符串转字节）
     * @param key  私钥
     * @return 解密后的原始字节数组
     * @throws CryptoErrorException 解密异常
     */
    @Override
    public byte[] decryptBytes(byte[] data, Key key) throws CryptoErrorException {
        String dataStr = BytesConvertUtils.bytesToUTF8String(data);

        // 是否包含分隔符，判断是否为分块加密数据
        if (dataStr.contains(BlockSplit)) {
            String[] blocks = dataStr.split(BlockSplit);
            byte[] lastBlockBytes = cryptoBlock(BytesConvertUtils.hexStringToBytes(blocks[blocks.length - 1]), key,
                    Cipher.DECRYPT_MODE);

            byte[] result;
            if (lastBlockBytes.length < blockSize) {
                result = new byte[(blocks.length - 1) * blockSize + lastBlockBytes.length];
                for (int i = 0; i < blocks.length - 1; i++) {
                    System.arraycopy(
                            cryptoBlock(BytesConvertUtils.hexStringToBytes(blocks[i]), key, Cipher.DECRYPT_MODE),
                            0, result, i * blockSize, blockSize);
                }
                System.arraycopy(lastBlockBytes, 0, result, (blocks.length - 1) * blockSize, lastBlockBytes.length);
            } else {
                result = new byte[blocks.length * blockSize];
                for (int i = 0; i < blocks.length - 1; i++) {
                    System.arraycopy(
                            cryptoBlock(BytesConvertUtils.hexStringToBytes(blocks[i]), key, Cipher.DECRYPT_MODE),
                            0, result, i * blockSize, blockSize);
                }
                System.arraycopy(lastBlockBytes, 0, result, (blocks.length - 1) * blockSize, lastBlockBytes.length);
            }
            return result;
        } else {
            // 单块数据直接解密
            return cryptoBlock(BytesConvertUtils.hexStringToBytes(dataStr), key, Cipher.DECRYPT_MODE);
        }
    }

}
