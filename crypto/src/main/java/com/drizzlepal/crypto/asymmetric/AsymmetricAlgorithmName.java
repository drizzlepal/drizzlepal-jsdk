package com.drizzlepal.crypto.asymmetric;

/**
 * 枚举类型，定义了非对称加密算法名称
 * 
 * 该枚举列出了当前支持的两种非对称加密算法类型：
 * RSA - 基于RSA算法的非对称加密
 * EC - 基于椭圆曲线(Elliptic Curve)的非对称加密
 */
public enum AsymmetricAlgorithmName {
    RSA("RSA"),
    SM2("EC"),
    ECC("EC");

    private final String algName;

    AsymmetricAlgorithmName(String algName) {
        this.algName = algName;
    }

    public String getAlgName() {
        return algName;
    }

}