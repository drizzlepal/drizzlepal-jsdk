package com.drizzlepal.springboot.webstarter;

public class RpcAssert {

    public static void isTrue(boolean equals, RpcErrorCode code, String detail) {
        if (!equals) {
            throw RpcException.newRpcException(code, detail);
        }
    }

}
