package com.drizzlepal.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;

import com.drizzlepal.crypto.exception.CryptoErrorException;

public class MessageCryptoTest {

    @Test
    void test() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoErrorException {
        String plainText = "Hello World!";
        String salt = "salt";
        String info = "info";
        MessageCrypto serverMessageCrypto = new MessageCrypto();
        MessageCrypto clientMessageCrypto = new MessageCrypto();
        MessageCryptoResult encrypt = clientMessageCrypto.encrypt(serverMessageCrypto.publicKey(), plainText, salt,
                info);
        System.out.println(encrypt);
        String decrypt = serverMessageCrypto.decrypt(encrypt.getPublicKey(), encrypt.getEncrypted(), encrypt.getIv(),
                salt, info);
        System.out.println(decrypt);
        assertEquals(plainText, decrypt);
    }

    @Test
    void testWithJsKey()
            throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoErrorException {
        String plainText = "hello world";
        String salt = "salt";
        String info = "info";
        String jsPublicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEcZgY8EfQ4TLisHrmjmCS1ME7r7/JQQu7DD5Th1lMfkXDfmKFdIewzyyCYpABX36CQaevTTHN5CtSfDp+xsdcWQ==";
        MessageCrypto serverMessageCrypto = new MessageCrypto();
        MessageCryptoResult encrypt = serverMessageCrypto.encrypt(jsPublicKey, plainText, salt,
                info);
        System.out.println(encrypt);
    }

    @Test
    void testWithServerKey()
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, CryptoErrorException {
        String salt = "salt";
        String info = "info";
        String privateKeyStr = "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgjrxU37W3yOF9XEekVbEcKzeFrV1OBWYTafbhhJjJEXOhRANCAARxmBjwR9DhMuKweuaOYJLUwTuvv8lBC7sMPlOHWUx+RcN+YoV0h7DPLIJikAFffoJBp69NMc3kK1J8On7Gx1xZ";
        String publicKeyStr = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEcZgY8EfQ4TLisHrmjmCS1ME7r7/JQQu7DD5Th1lMfkXDfmKFdIewzyyCYpABX36CQaevTTHN5CtSfDp+xsdcWQ==";
        String encrypted = "NS6mkYyBntDPrpnHW0Tr+5zCwkHcz5WNOvPF";
        String iv = "EYem1xi6BqZZAf/8X9g8bw==";
        String jsPublicKey = "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEJcUH5RuoyBua7eAYFtRod3TU59WegN346wiFQ8TKms/4HXoL4wIjlPnaUZv3KygbDqxmtszzr8qpCoL4lea3Xw==";
        MessageCrypto clientMessageCrypto = new MessageCrypto(privateKeyStr, publicKeyStr);
        String decrypt = clientMessageCrypto.decrypt(jsPublicKey, encrypted, iv,
                salt, info);
        assertEquals("hello world", decrypt);
    }

}
