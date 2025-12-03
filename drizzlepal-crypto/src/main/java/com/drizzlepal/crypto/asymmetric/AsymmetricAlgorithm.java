
package com.drizzlepal.crypto.asymmetric;

import java.security.Key;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.drizzlepal.crypto.exception.CryptoErrorException;

public abstract class AsymmetricAlgorithm {

    // 算法提供者
    public static final String PROVIDER = "BC";

    /**
     * 验证确保加载了BC安全管理应用提供者
     */
    static {
        if (Security.getProvider(PROVIDER) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }
    /**
     * 算法参数
     */
    protected AlgorithmParameterSpec parameterSpec;

    public AsymmetricAlgorithm(AlgorithmParameterSpec parameterSpec) {
        this.parameterSpec = parameterSpec;
    }

    /**
     * 算法名称
     * 
     * @return 算法名称
     */
    protected abstract AsymmetricAlgorithmName algName();

    /**
     * 使用指定的密钥加密字节数组数据
     * 
     * @param data 待加密的字节数据
     * @param key  用于加密的密钥对象，符合Key接口
     * @return 加密后的字节数组，返回的具体类型和长度取决于所使用的加密算法
     * @throws CryptoErrorException 当加密操作因任何原因（如不支持的加密算法、无效的密钥等）失败时抛出
     */
    public abstract byte[] encryptBytes(byte[] data, Key key) throws CryptoErrorException;

    /**
     * 使用指定的密钥解密字节数组数据
     * 
     * @param data 待解密的字节数组数据
     * @param key  用于解密的密钥
     * @return 解密后的字节数组
     * @throws CryptoErrorException 如果解密过程中发生错误，将抛出此异常
     */
    public abstract byte[] decryptBytes(byte[] data, Key key) throws CryptoErrorException;

}