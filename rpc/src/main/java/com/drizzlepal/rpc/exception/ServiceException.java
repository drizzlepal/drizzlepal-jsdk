package com.drizzlepal.rpc.exception;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcStatusCommon;

/**
 * 服务异常
 */
public class ServiceException extends RpcException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public RpcStatusCommon getRpcStatus() {
        return RpcStatusCommon.SERVICE_UNAVAILABLE;
    }

}
