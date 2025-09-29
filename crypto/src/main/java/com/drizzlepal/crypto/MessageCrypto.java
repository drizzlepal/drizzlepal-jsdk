package com.drizzlepal.crypto;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.KeyAgreement;

import org.bouncycastle.crypto.generators.HKDFBytesGenerator;
import org.bouncycastle.crypto.params.HKDFParameters;

import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithms;
import com.drizzlepal.crypto.asymmetric.KeyPairFactory;
import com.drizzlepal.crypto.exception.CryptoErrorException;
import com.drizzlepal.crypto.exception.InvalidKeyAlgorithmException;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoMode;
import com.drizzlepal.crypto.symmetric.SymmetricCryptoPadding;
import com.drizzlepal.crypto.symmetric.algorithm.AESAlgorithm;
import com.drizzlepal.crypto.symmetric.algorithm.AESCryptoKeySize;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * 消息加解密核心类
 * 基于 ECDH 密钥交换 + AES-GCM 加密算法实现安全通信。
 */
public class MessageCrypto {
    // 存储本地生成的密钥对（私钥 + 公钥字符串）
    private final MessageCryptoKeyPair messageCryptoKeyPair;

    /**
     * 构造函数：生成新的 ECC 密钥对（默认使用 secp256r1 曲线）
     */
    public MessageCrypto() {
        KeyPair keyPair;
        try {
            // 使用工厂类生成 ECC 密钥对
            keyPair = KeyPairFactory.generateKeyPair(AsymmetricAlgorithms.ECCSECP256);
            MessageCryptoKeyPair messageCryptoKeyPair = new MessageCryptoKeyPair(keyPair.getPrivate(),
                    keyPair.getPublic());
            this.messageCryptoKeyPair = messageCryptoKeyPair;
        } catch (InvalidKeyAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 构造函数：使用现有私钥和公钥字符串恢复密钥对
     */
    public MessageCrypto(String privateKeyStr, String publicKeyStr)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PublicKey publicKey = KeyPairFactory.importKeySpki(publicKeyStr);
        PrivateKey privateKey = KeyPairFactory.importKeyPkcs8(privateKeyStr);
        this.messageCryptoKeyPair = new MessageCryptoKeyPair(privateKey, publicKey);
    }

    /**
     * 基于本地私钥与对方公钥进行 ECDH 密钥交换，生成共享密钥
     *
     * @param peerPublicKey 对方公钥
     * @param privateKey    本地私钥
     * @return 共享密钥原始字节
     */
    public static byte[] deriveSharedSecret(PublicKey peerPublicKey, PrivateKey privateKey)
            throws InvalidKeyException, NoSuchAlgorithmException {
        KeyAgreement ka = KeyAgreement.getInstance("ECDH");
        ka.init(privateKey);
        ka.doPhase(peerPublicKey, true);
        return ka.generateSecret();
    }

    /**
     * 基于共享密钥和 HKDF 派生出 AES 对称加密密钥
     *
     * @param sharedSecret ECDH 密钥交换得到的共享密钥
     * @param salt         盐值（可选）
     * @param info         附加信息（可选）
     * @return 256 位 AES 密钥
     */
    public static byte[] deriveAESKey(byte[] sharedSecret, String salt, String info) {
        HKDFBytesGenerator hkdf = new HKDFBytesGenerator(new org.bouncycastle.crypto.digests.SHA256Digest());
        hkdf.init(new HKDFParameters(
                sharedSecret,
                BytesConvertUtils.utf8StringToBytes(salt),
                BytesConvertUtils.utf8StringToBytes(info)));
        byte[] aesKeyBytes = new byte[32]; // 256 位
        hkdf.generateBytes(aesKeyBytes, 0, 32);
        return aesKeyBytes;
    }

    /**
     * 使用对方公钥加密消息（ECDH 生成密钥 + AES-GCM 加密）
     *
     * @param spkiPublicKey 对方 SPKI 格式公钥字符串
     * @param plainText     明文内容
     * @param salt          用于 HKDF 的盐值
     * @param info          用于 HKDF 的附加信息
     * @return 加密结果（包含密文、IV、本地公钥）
     */
    public MessageCryptoResult encrypt(String spkiPublicKey, String plainText, String salt, String info)
            throws InvalidKeyException, NoSuchAlgorithmException, CryptoErrorException, InvalidKeySpecException {
        PublicKey publicKey = KeyPairFactory.importKeySpki(spkiPublicKey);

        // 派生共享密钥
        byte[] deriveSharedSecret = deriveSharedSecret(publicKey, this.messageCryptoKeyPair.privateKey);

        // 生成 AES 密钥
        byte[] deriveAESKey = MessageCrypto.deriveAESKey(deriveSharedSecret, salt, info);

        // 随机生成 16 字节 IV（用于 GCM）
        byte[] randomAESIV = CryptoBytesRandom.getSecureRandomBytes(16);

        // 执行 AES-GCM 加密
        String encrypted = BytesConvertUtils.bytesToBase64String(SymmetricCrypto.encryptBytes(
                BytesConvertUtils.utf8StringToBytes(plainText),
                deriveAESKey,
                new AESAlgorithm(SymmetricCryptoMode.GCM, SymmetricCryptoPadding.NoPadding, AESCryptoKeySize.AES_256),
                randomAESIV));

        // 返回结果对象，包含密文、IV、本地公钥字符串
        return new MessageCryptoResult(
                encrypted,
                BytesConvertUtils.bytesToBase64String(randomAESIV),
                this.messageCryptoKeyPair.publicKeyStr);
    }

    /**
     * 解密对方发来的加密内容
     *
     * @param spkiPublicKey 对方 SPKI 公钥
     * @param encrypted     Base64 编码的密文
     * @param iv            Base64 编码的 AES IV
     * @param salt          盐值
     * @param info          附加信息
     * @return 解密后的明文字符串
     */
    public String decrypt(String spkiPublicKey, String encrypted, String iv, String salt, String info)
            throws CryptoErrorException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        PublicKey publicKey = KeyPairFactory.importKeySpki(spkiPublicKey);

        // 派生共享密钥
        byte[] deriveSharedSecret = deriveSharedSecret(publicKey, this.messageCryptoKeyPair.privateKey);

        // 生成 AES 密钥
        byte[] deriveAESKey = MessageCrypto.deriveAESKey(deriveSharedSecret, salt, info);

        // 解密并返回 UTF-8 明文
        return BytesConvertUtils.bytesToUTF8String(SymmetricCrypto.decryptBytes(
                BytesConvertUtils.base64StringToBytes(encrypted),
                deriveAESKey,
                new AESAlgorithm(SymmetricCryptoMode.GCM, SymmetricCryptoPadding.NoPadding, AESCryptoKeySize.AES_256),
                BytesConvertUtils.base64StringToBytes(iv)));
    }

    /**
     * 内部类：用于保存密钥对信息（私钥 + 公钥字符串）
     */
    private static class MessageCryptoKeyPair {
        private String publicKeyStr; // 公钥字符串（Base64 编码）
        private PrivateKey privateKey; // 私钥对象

        public MessageCryptoKeyPair(PrivateKey privateKey, PublicKey publicKey) {
            this.publicKeyStr = KeyPairFactory.exportKeySpki(publicKey);
            this.privateKey = privateKey;
        }
    }

    /**
     * 获取本地密钥对中的公钥字符串（Base64 编码的 SPKI 格式）
     *
     * @return 公钥字符串
     */
    public String publicKey() {
        return this.messageCryptoKeyPair.publicKeyStr;
    }
}
