package com.drizzlepal.rpc.exception;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcStatusCommon;

/**
 * 权限不足异常
 */
public class PermissionDeniedException extends RpcException {

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public RpcStatusCommon getRpcStatus() {
        return RpcStatusCommon.PERMISSION_DENIED;
    }

}
