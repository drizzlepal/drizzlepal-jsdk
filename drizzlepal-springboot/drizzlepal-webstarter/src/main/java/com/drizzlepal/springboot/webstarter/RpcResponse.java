package com.drizzlepal.springboot.webstarter;

import lombok.Data;

/**
 * RPC 响应结果
 */
@Data
public abstract class RpcResponse<T> {

    protected final String code;

    protected final String message;

    protected final T data;

    public RpcResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static RpcResponse<Throwable> failed(RpcException ex) {
        return new RpcResponse<Throwable>(ex.getCode().getName(), ex.getCode().getMessage(), ex.getCause()) {
        };
    }

    public static RpcResponse<Throwable> failed(RpcErrorCode code, Throwable cause) {
        return new RpcResponse<Throwable>(code.getName(), code.getMessage(), cause) {
        };
    }

    public static Object failed(String detail) {
        return new RpcResponse<String>(CommonRpcErrorCode.Unknown.name(), detail,
                null) {
        };
    }

}
