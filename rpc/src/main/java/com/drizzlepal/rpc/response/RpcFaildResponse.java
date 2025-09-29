package com.drizzlepal.rpc.response;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcResponse;
import com.drizzlepal.rpc.RpcStatus;
import com.drizzlepal.rpc.RpcStatusCommon;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * RPC 失败调用响应
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RpcFaildResponse<T> extends RpcResponse<T> {

    private String detail;

    public RpcFaildResponse() {
        super(RpcStatusCommon.UNKNOWN_ERROR, null);
        this.detail = RpcStatusCommon.UNKNOWN_ERROR.getMessage();
    }

    public RpcFaildResponse(RpcStatus status) {
        super(status, null);
        this.detail = status.getMessage();
    }

    public RpcFaildResponse(RpcStatus status, T data) {
        super(status, data);
        this.detail = status.getMessage();
    }

    public RpcFaildResponse(String detail) {
        super(RpcStatusCommon.UNKNOWN_ERROR, null);
        this.detail = detail;
    }

    public RpcFaildResponse(String detail, T data) {
        super(RpcStatusCommon.UNKNOWN_ERROR, data);
        this.detail = detail;
    }

    public RpcFaildResponse(RpcException exception) {
        super(exception.getRpcStatus(), null);
        this.detail = exception.getMessage();
    }

    public RpcFaildResponse(RpcException exception, T data) {
        super(exception.getRpcStatus(), data);
        this.detail = exception.getMessage();
    }

    public RpcFaildResponse(RpcStatus status, String detail) {
        super(status, null);
        this.detail = detail;
    }

    public RpcFaildResponse(RpcStatus status, String detail, T data) {
        super(status, data);
        this.detail = detail;
    }

    public RpcFaildResponse(RpcException exception, String detail) {
        super(exception.getRpcStatus(), null);
        this.detail = detail;
    }

    public RpcFaildResponse(RpcException exception, String detail, T data) {
        super(exception.getRpcStatus(), data);
        this.detail = detail;
    }

    public RpcFaildResponse(RpcStatus status, Throwable cause) {
        super(status, null);
        this.detail = cause.getMessage();
    }

}
