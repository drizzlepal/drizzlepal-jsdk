package com.drizzlepal.crypto.exception;

public class InvalidKeyAlgorithmException extends Exception {

    public InvalidKeyAlgorithmException(String alg) {
        super("不支持的秘钥算法：" + alg);
    }

    public InvalidKeyAlgorithmException(String algName, Exception e) {
        super("不支持的秘钥算法：" + algName, e);
    }

}
