package com.drizzlepal.crypto;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.drizzlepal.crypto.exception.CryptoErrorException;

/**
 * 消息摘要工具类
 */
public class MessageDigests {

    // 摘要算法枚举
    public enum DigestAlgorithm {
        MD5("MD5"),
        SHA256("SHA-256"),
        SHA512("SHA-512");

        private final String algorithm;

        DigestAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }

    // HMAC算法枚举
    public enum HmacAlgorithm {
        HMAC_MD5("HmacMD5"),
        HMAC_SHA1("HmacSHA1"),
        HMAC_SHA256("HmacSHA256"),
        HMAC_SHA512("HmacSHA512");

        private final String algorithm;

        HmacAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getAlgorithm() {
            return algorithm;
        }
    }

    // 普通摘要方法
    public static String digest(DigestAlgorithm algorithm, String data) throws CryptoErrorException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm.getAlgorithm());
            return BytesConvertUtils.bytesToHexString(md.digest(BytesConvertUtils.utf8StringToBytes(data)));
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoErrorException("消息摘要失败", e);
        }
    }

    // HMAC方法
    public static String hmac(HmacAlgorithm algorithm, String key, String data) throws CryptoErrorException {
        Mac mac;
        try {
            mac = Mac.getInstance(algorithm.getAlgorithm());
            mac.init(new SecretKeySpec(BytesConvertUtils.utf8StringToBytes(key), algorithm.getAlgorithm()));
            return BytesConvertUtils.bytesToHexString(mac.doFinal(BytesConvertUtils.utf8StringToBytes(data)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new CryptoErrorException("消息摘要失败", e);
        }
    }

}
