package com.drizzlepal.rpc.exception;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcStatusCommon;

/**
 * 未授权异常
 */
public class UnauthorizedException extends RpcException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public RpcStatusCommon getRpcStatus() {
        return RpcStatusCommon.UNAUTHORIZED;
    }

}
