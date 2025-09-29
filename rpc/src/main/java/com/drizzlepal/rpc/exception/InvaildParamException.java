package com.drizzlepal.rpc.exception;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcStatusCommon;

/**
 * 请求参数错误异常
 */
public class InvaildParamException extends RpcException {

    public InvaildParamException(String message) {
        super(message);
    }

    public InvaildParamException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public InvaildParamException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public RpcStatusCommon getRpcStatus() {
        return RpcStatusCommon.PARAM_INVALID;
    }

}
