package com.drizzlepal.springboot.webstarter;

public class WebAssert {

    public static void isTrue(boolean equals, WebErrorCode code, String detail) {
        if (!equals) {
            throw WebException.newRpcException(code, detail);
        }
    }

}
