package com.drizzlepal.crypto;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter.Feature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 表示加密操作后的结果对象
 * 包含密文、IV 向量、本地公钥（用于对方后续解密）
 */
@Getter // 自动生成 getter 方法
@Setter // 自动生成 setter 方法
@AllArgsConstructor // 自动生成全参构造函数
public class MessageCryptoResult {

    /**
     * Base64 编码的密文
     */
    private String encrypted;

    /**
     * Base64 编码的 AES 初始化向量（IV）
     */
    private String iv;

    /**
     * Base64 编码的本地公钥（SPKI 格式）
     */
    private String publicKey;

    /**
     * 重写 toString 方法，格式化为 JSON 字符串（便于日志输出和调试）
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this, Feature.PrettyFormat);
    }

}
