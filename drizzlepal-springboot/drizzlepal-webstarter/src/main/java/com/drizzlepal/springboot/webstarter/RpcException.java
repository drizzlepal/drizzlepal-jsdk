package com.drizzlepal.springboot.webstarter;

public abstract class RpcException extends RuntimeException {

    private final RpcErrorCode code;

    public RpcException() {
        super(CommonRpcErrorCode.Unknown.getMessage());
        this.code = CommonRpcErrorCode.Unknown;
    }

    public RpcException(String detail) {
        super(detail);
        this.code = CommonRpcErrorCode.Unknown;
    }

    public RpcException(Throwable cause) {
        super(cause);
        this.code = CommonRpcErrorCode.Unknown;
    }

    public RpcException(String detail, Throwable cause) {
        super(detail, cause);
        this.code = CommonRpcErrorCode.Unknown;
    }

    public RpcException(RpcErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public RpcException(RpcErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public RpcException(RpcErrorCode code, String detail) {
        super(detail);
        this.code = code;
    }

    public RpcException(RpcErrorCode code, String detail, Throwable cause) {
        super(detail, cause);
        this.code = code;
    }

    public RpcErrorCode getCode() {
        return code;
    }

    public static RpcException newRpcException(Throwable cause) {
        return new RpcException(cause) {
        };
    }

    public static RpcException newRpcException(String detail) {
        return new RpcException(detail) {
        };
    }

    public static RpcException newRpcException(String detail, Throwable cause) {
        return new RpcException(detail, cause) {
        };
    }

    public static RpcException newRpcException(RpcErrorCode code) {
        return new RpcException(code) {
        };
    }

    public static RpcException newRpcException(RpcErrorCode code, Throwable cause) {
        return new RpcException(code, cause) {
        };
    }

    public static RpcException newRpcException(RpcErrorCode code, String detail) {
        return new RpcException(code, detail) {
        };
    }

    public static RpcException newRpcException(RpcErrorCode code, String detail, Throwable cause) {
        return new RpcException(code, detail, cause) {
        };
    }

}