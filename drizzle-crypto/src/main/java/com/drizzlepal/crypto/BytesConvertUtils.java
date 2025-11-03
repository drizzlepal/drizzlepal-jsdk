package com.drizzlepal.crypto;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.bouncycastle.util.encoders.Hex;

import com.alibaba.fastjson2.JSON;

/**
 * 字节转换工具类
 * 提供各种编码方式（如Hex, Base64, ASCII, UTF-8）下的字节与字符串互转功能
 * 以及对象与字节互转的功能
 */
public class BytesConvertUtils {

    /**
     * 将字节数组转换为Hex编码的字符串
     * 
     * @param bytes 待转换的字节数组
     * @return 转换后的Hex编码字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        return Hex.toHexString(bytes);
    }

    /**
     * 将Hex编码的字符串转换为字节数组
     * 
     * @param str 待转换的Hex编码字符串
     * @return 转换后的字节数组
     */
    public static byte[] hexStringToBytes(String str) {
        return Hex.decode(str);
    }

    /**
     * 将字节数组转换为Base64编码的字符串
     * 
     * @param bytes 待转换的字节数组
     * @return 转换后的Base64编码字符串
     */
    public static String bytesToBase64String(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 将Base64编码的字符串转换为字节数组
     * 
     * @param str 待转换的Base64编码字符串
     * @return 转换后的字节数组
     */
    public static byte[] base64StringToBytes(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * 将字节数组转换为ASCII编码的字符串
     * 
     * @param bytes 待转换的字节数组
     * @return 转换后的ASCII编码字符串
     */
    public static String bytesToASCIIString(byte[] bytes) {
        return new String(bytes, StandardCharsets.US_ASCII);
    }

    /**
     * 将ASCII编码的字符串转换为字节数组
     * 
     * @param str 待转换的ASCII编码字符串
     * @return 转换后的字节数组
     */
    public static byte[] asciiStringToBytes(String str) {
        return str.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * 将字节数组转换为UTF-8编码的字符串
     * 
     * @param bytes 待转换的字节数组
     * @return 转换后的UTF-8编码字符串
     */
    public static String bytesToUTF8String(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * 将UTF-8编码的字符串转换为字节数组
     * 
     * @param str 待转换的UTF-8编码字符串
     * @return 转换后的字节数组
     */
    public static byte[] utf8StringToBytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 将字节数组转换为指定类的对象
     * 
     * @param bytes 待转换的字节数组
     * @param clazz 目标对象类
     * @param <T>   泛型标记
     * @return 转换后的对象
     */
    public static <T> T bytesToObject(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }

    /**
     * 将对象转换为字节数组
     * 
     * @param obj 待转换的对象
     * @return 转换后的字节数组
     */
    public static byte[] objectToBytes(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    /**
     * 将对象转换为可加密的字符串形式
     * 若对象为字符串或字符类型，则直接转换为字符串；
     * 否则使用JSON格式化为字符串
     * 
     * @param obj 待转换的对象
     * @return 转换后的字符串
     */
    public static String toEncryptableString(Object obj) {
        if (obj instanceof String || obj instanceof Character) {
            return obj.toString();
        }
        return JSON.toJSONString(obj);
    }

}