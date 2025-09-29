package com.drizzlepal.rpc;

import com.drizzlepal.rpc.response.RpcFaildResponse;
import com.drizzlepal.rpc.response.RpcSucceedResponse;

import lombok.Data;

/**
 * RPC 响应结果
 */
@Data
public abstract class RpcResponse<T> {

    /**
     * 响应状态
     */
    protected RpcStatus status;

    /**
     * 请求响应主体数据
     */
    protected T data;

    public RpcResponse(RpcStatus status, T data) {
        this.status = status;
        this.data = data;
    }

    public static RpcFaildResponse<?> failed(RpcException cause) {
        return new RpcFaildResponse<>(cause);
    }

    public static RpcFaildResponse<?> failed(RpcStatus status) {
        return new RpcFaildResponse<>(status);
    }

    public static <T> RpcFaildResponse<?> failed(RpcStatus status, T data) {
        return new RpcFaildResponse<>(status, data);
    }

    public static RpcFaildResponse<?> failed(String message) {
        return new RpcFaildResponse<>(message);
    }

    public static <T> RpcFaildResponse<T> failed(RpcException rpcException, T data) {
        return new RpcFaildResponse<>(rpcException, data);
    }

    public static <T> RpcFaildResponse<T> failed(String message, T data) {
        return new RpcFaildResponse<>(message, data);
    }

    public static <T> RpcSucceedResponse<T> succeed(T data) {
        return new RpcSucceedResponse<>(data);
    }

}
