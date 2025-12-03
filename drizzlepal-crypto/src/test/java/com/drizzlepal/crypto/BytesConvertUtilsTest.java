package com.drizzlepal.crypto;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class BytesConvertUtilsTest {

    @Test
    void test() {
        byte[] name = new byte[] { 72, 101, 108, 108, 111 };
        System.out.println(BytesConvertUtils.bytesToHexString(name));
        assertArrayEquals(name, BytesConvertUtils.hexStringToBytes("48656c6c6f"));
        byte[] bytes = "nihaadf".getBytes();
        System.out.println(BytesConvertUtils.bytesToBase64String(bytes));
        assertArrayEquals(bytes, BytesConvertUtils.base64StringToBytes(BytesConvertUtils.bytesToBase64String(bytes)));
    }

}
