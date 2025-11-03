package com.drizzlepal.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.drizzlepal.crypto.exception.CryptoErrorException;

class SymmetricCryptoTest {

    private static final String PLAIN_TEXT = "This is a test";

    @Test
    void test() throws CryptoErrorException {
        byte[] keys = CryptoStringRandom.randomAES256Key().getBytes(StandardCharsets.UTF_8);
        byte[] encryptBytes = SymmetricCrypto.encryptBytes(PLAIN_TEXT.getBytes(StandardCharsets.UTF_8),
                keys);
        String bytesToHexString = BytesConvertUtils.bytesToHexString(encryptBytes);
        byte[] decryptBytes = SymmetricCrypto.decryptBytes(BytesConvertUtils.hexStringToBytes(bytesToHexString), keys);
        String string = new String(decryptBytes, StandardCharsets.UTF_8);
        assertEquals(PLAIN_TEXT, string);
    }

}