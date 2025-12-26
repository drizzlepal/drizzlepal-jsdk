package com.drizzlepal.springboot.webstarter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * RPC 响应结果
 */
@Data
@Schema(name = "HTTP 响应规范", description = "统一响应结果")
public abstract class RpcResponse<T> {

    @Schema(name = "响应码", description = "响应码")
    protected String code;

    @Schema(name = "响应信息", description = "响应信息")
    protected String message;

    @Schema(name = "响应数据", description = "响应数据")
    protected T data;

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

    public static RpcResponse<?> failed(String detail) {
        return new RpcResponse<String>(CommonRpcErrorCode.Unknown.name(), detail,
                null) {
        };
    }

    public static <T> RpcResponse<T> succeed(T data) {
        return new RpcResponse<T>(CommonRpcErrorCode.Success.name(), CommonRpcErrorCode.Success.getMessage(),
                data) {
        };
    }

}
