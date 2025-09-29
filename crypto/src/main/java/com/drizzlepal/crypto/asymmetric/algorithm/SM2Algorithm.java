package com.drizzlepal.crypto.asymmetric.algorithm;

import java.security.Key;

import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;

import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithm;
import com.drizzlepal.crypto.asymmetric.AsymmetricAlgorithmName;
import com.drizzlepal.crypto.asymmetric.ECGenParameters;
import com.drizzlepal.crypto.exception.CryptoErrorException;

/**
 * SM2 算法实现类，基于 BouncyCastle 提供的 SM2Engine 实现。
 * <p>
 * 支持加密、解密、密钥协商（Key Agreement）。
 */
public class SM2Algorithm extends AsymmetricAlgorithm {

    /**
     * 构造函数，初始化 SM2 椭圆曲线参数（sm2p256v1）。
     */
    public SM2Algorithm() {
        super(ECGenParameters.sm2p256v1.getECGenParameter());
    }

    /**
     * 返回算法名称（使用 EC 枚举标识）。
     *
     * @return EC 算法名
     */
    @Override
    protected AsymmetricAlgorithmName algName() {
        return AsymmetricAlgorithmName.SM2;
    }

    /**
     * 使用 SM2 公钥加密数据。
     *
     * @param data 明文数据
     * @param key  公钥
     * @return 加密后的密文字节数组
     * @throws CryptoErrorException 加密过程中发生异常
     */
    @Override
    public byte[] encryptBytes(byte[] data, Key key) throws CryptoErrorException {
        try {
            SM2Engine sm2Engine = new SM2Engine();
            ECPublicKeyParameters pubKeyParams = (ECPublicKeyParameters) PublicKeyFactory.createKey(key.getEncoded());
            ParametersWithRandom paramsWithRandom = new ParametersWithRandom(pubKeyParams);
            sm2Engine.init(true, paramsWithRandom);
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (Exception e) {
            throw new CryptoErrorException(e);
        }
    }

    /**
     * 使用 SM2 私钥解密数据。
     *
     * @param data 密文数据
     * @param key  私钥
     * @return 解密后的明文字节数组
     * @throws CryptoErrorException 解密过程中发生异常
     */
    @Override
    public byte[] decryptBytes(byte[] data, Key key) throws CryptoErrorException {
        try {
            SM2Engine sm2Engine = new SM2Engine();
            ECPrivateKeyParameters priKeyParams = (ECPrivateKeyParameters) PrivateKeyFactory
                    .createKey(key.getEncoded());
            sm2Engine.init(false, priKeyParams);
            return sm2Engine.processBlock(data, 0, data.length);
        } catch (Exception e) {
            throw new CryptoErrorException(e);
        }
    }

}
