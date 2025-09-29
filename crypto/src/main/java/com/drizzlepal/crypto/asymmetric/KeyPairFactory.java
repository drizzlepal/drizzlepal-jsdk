package com.drizzlepal.crypto.asymmetric;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import com.drizzlepal.crypto.BytesConvertUtils;
import com.drizzlepal.crypto.exception.InvalidKeyAlgorithmException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * 密钥对工厂类，提供生成、导入和导出非对称密钥对的功能
 * 
 * 该类封装了与密钥对相关的各种操作，包括生成密钥对、将密钥导出为PEM格式、
 * 从PEM格式导入密钥等，支持多种非对称加密算法
 */
public class KeyPairFactory {

    private static final String PRIVATE_KEY_HEADER = "PRIVATE KEY";
    private static final String PUBLIC_KEY_HEADER = "PUBLIC KEY";

    /**
     * 生成非对称密钥对
     * 
     * @param alg 指定的非对称加密算法参数
     * @return 生成的非对称密钥对
     * @throws InvalidKeyAlgorithmException 如果指定的算法无效，则抛出此异常
     */
    public static final KeyPair generateKeyPair(AsymmetricAlgorithm alg) throws InvalidKeyAlgorithmException {
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(alg.algName().getAlgName(), AsymmetricAlgorithm.PROVIDER);
            keyPairGenerator.initialize(alg.parameterSpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new InvalidKeyAlgorithmException(alg.algName().getAlgName());
        }
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 根据指定的非对称算法生成密钥对
     * 
     * @param algEnum 指定的非对称算法枚举，用于确定生成密钥对所使用的算法
     * @return 生成的密钥对，包含公钥和私钥
     * @throws InvalidKeyAlgorithmException 如果指定的算法无效，则抛出此异常
     */
    public static final KeyPair generateKeyPair(AsymmetricAlgorithms algEnum) throws InvalidKeyAlgorithmException {
        return generateKeyPair(algEnum.algorithm);
    }

    /**
     * 将私钥导出为PEM格式的字符串
     * 
     * @param key 私钥对象，需要被导出
     * @return 导出的私钥的PEM格式字符串
     * @throws IOException 如果导出过程中发生输入输出异常
     */
    public static final String exportKeyPem(PrivateKey key) throws IOException {
        StringWriter pemWriter = new StringWriter();
        try (PemWriter writer = new PemWriter(pemWriter)) {
            PemObject pemObject = new PemObject(PRIVATE_KEY_HEADER, key.getEncoded());
            writer.writeObject(pemObject);
        }
        return pemWriter.toString();
    }

    /**
     * 将公钥导出为PEM格式的字符串
     * 
     * @param key 要导出的公钥
     * @return 导出的PEM格式的公钥字符串
     * @throws IOException 如果发生I/O错误
     */
    public static final String exportKeyPem(PublicKey key) throws IOException {
        StringWriter pemWriter = new StringWriter();
        try (PemWriter writer = new PemWriter(pemWriter)) {
            PemObject pemObject = new PemObject(PUBLIC_KEY_HEADER, key.getEncoded());
            writer.writeObject(pemObject);
        }
        return pemWriter.toString();
    }

    /**
     * 根据给定的公钥PEM字符串和非对称算法导入公钥
     * 
     * @param publicKeyPem 公钥的PEM格式字符串
     * @param alg          指定的非对称算法
     * @return 根据指定算法导入的公钥对象
     * @throws InvalidKeyAlgorithmException 如果公钥无效或算法不支持
     */
    public static final PublicKey importPublicKeyPem(String publicKeyPem, AsymmetricAlgorithm alg)
            throws InvalidKeyAlgorithmException {
        try (PEMParser pemParser = new PEMParser(new StringReader(publicKeyPem))) {
            PemObject pemObject = pemParser.readPemObject();
            KeyFactory keyFactory = KeyFactory.getInstance(alg.algName().getAlgName(), AsymmetricAlgorithm.PROVIDER);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pemObject.getContent());
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            throw new InvalidKeyAlgorithmException(alg.algName().getAlgName(), e);
        }
    }

    /**
     * 导入PEM格式的私钥
     * 
     * @param privateKeyPem PEM格式的私钥字符串
     * @param alg           非对称算法枚举，用于指定使用的算法和提供者
     * @return 生成的PrivateKey对象
     * @throws InvalidKeyAlgorithmException 如果密钥算法无效
     */
    public static final PrivateKey importPrivateKeyPem(String privateKeyPem, AsymmetricAlgorithm alg)
            throws InvalidKeyAlgorithmException {
        try (PEMParser pemParser = new PEMParser(new StringReader(privateKeyPem))) {
            PemObject pemObject = pemParser.readPemObject();
            KeyFactory keyFactory = KeyFactory.getInstance(alg.algName().getAlgName(), AsymmetricAlgorithm.PROVIDER);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pemObject.getContent());
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            throw new InvalidKeyAlgorithmException(alg.algName().getAlgName(), e);
        }
    }

    /**
     * 导入公钥的PEM格式字符串，并根据指定的算法类型返回公钥对象
     * 
     * @param publicKeyPem 公钥的PEM格式字符串
     * @param algEnum      算法类型枚举
     * @return 初始化后的公钥对象
     * @throws InvalidKeyAlgorithmException 如果公钥字符串无效或不支持指定算法
     */
    public static final PublicKey importPublicKeyPem(String publicKeyPem, AsymmetricAlgorithms algEnum)
            throws InvalidKeyAlgorithmException {
        return importPublicKeyPem(publicKeyPem, algEnum.algorithm);
    }

    /**
     * 导入PEM格式的私钥
     * 
     * @param privateKeyPem 私钥的PEM格式字符串
     * @param algEnum       非对称算法的枚举值
     * @return PrivateKey实例
     * @throws InvalidKeyAlgorithmException 如果私钥字符串无效或不支持指定算法
     */
    public static final PrivateKey importPrivateKeyPem(String privateKeyPem, AsymmetricAlgorithms algEnum)
            throws InvalidKeyAlgorithmException {
        return importPrivateKeyPem(privateKeyPem, algEnum.algorithm);
    }

    /**
     * 将公钥对象导出为SPKI格式的Base64字符串
     * 
     * @param publicKey 需要导出的公钥对象
     * @return Base64编码的SPKI格式公钥字符串
     */
    public static String exportKeySpki(PublicKey publicKey) {
        return BytesConvertUtils.bytesToBase64String(publicKey.getEncoded());
    }

    /**
     * 导入SPKI格式的公钥字符串
     * 
     * @param spkiPublicKey SPKI格式的Base64编码公钥字符串
     * @return 导入的公钥对象
     * @throws NoSuchAlgorithmException 如果算法不可用
     * @throws InvalidKeySpecException  如果密钥规格无效
     */
    public static PublicKey importKeySpki(String spkiPublicKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(BytesConvertUtils.base64StringToBytes(spkiPublicKey));
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 将私钥导出为PKCS#8格式的Base64字符串
     * 
     * @param privateKey 需要导出的私钥对象
     * @return Base64编码的PKCS#8格式私钥字符串
     */
    public static String exportKeyPkcs8(PrivateKey privateKey) {
        byte[] encoded = privateKey.getEncoded(); // DER 格式
        return BytesConvertUtils.bytesToBase64String(encoded);
    }

    /**
     * 导入PKCS#8格式的私钥字符串
     * 
     * @param privateKeyStr PKCS#8格式的Base64编码私钥字符串
     * @return 导入的私钥对象
     * @throws InvalidKeySpecException  如果密钥规格无效
     * @throws NoSuchAlgorithmException 如果算法不可用
     */
    public static PrivateKey importKeyPkcs8(String privateKeyStr)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        byte[] keyBytes = BytesConvertUtils.base64StringToBytes(privateKeyStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(keySpec);
    }
}