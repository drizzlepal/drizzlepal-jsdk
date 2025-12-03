/**
 * 对称加密密钥大小接口
 * 该接口定义了获取对称加密算法密钥大小的标准方法
 * 实现该接口的类需要提供具体的方法来返回密钥的大小
 */
package com.drizzlepal.crypto.symmetric;

public interface SymmetricCryptoKeySize {

    /**
     * 获取对称加密密钥的大小
     * 
     * @return 密钥的大小，以位为单位
     */
    public int getKeySize();

}