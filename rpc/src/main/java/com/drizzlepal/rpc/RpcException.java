package com.drizzlepal.rpc;

/**
 * RpcException类是专门为RPC（远程过程调用）定义的抽象异常类。
 * 它继承自RuntimeException，用于处理RPC调用过程中的各种异常情况。
 * 该类提供了一种机制，通过覆盖httpStatus方法来指定异常对应的HTTP状态码。
 */
public abstract class RpcException extends RuntimeException {

    /**
     * 构造函数，接收异常消息作为参数。
     * 
     * @param message 异常消息
     */
    public RpcException(String message) {
        super(message);
    }

    /**
     * 构造函数，接收Throwable对象作为参数。
     * 
     * @param cause 引发异常的原因
     */
    public RpcException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    /**
     * 构造函数，同时接收异常消息和Throwable对象作为参数。
     * 
     * @param message 异常消息
     * @param cause   引发异常的原因
     */
    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public static RpcException newRpcException(RpcStatus status) {
        return new RpcException(status.getMessage()) {
            private static final long serialVersionUID = 1L;

            @Override
            public RpcStatus getRpcStatus() {
                return status;
            }

        };
    }

    /**
     * 静态工厂方法，根据Throwable对象创建一个新的RpcException实例。
     * 默认HTTP状态码为INTERNAL_SERVER_ERROR。
     * 
     * @param cause 引发异常的原因
     * @return 新的RpcException实例
     */
    public static RpcException newRpcException(RpcStatus status, Throwable cause) {
        return new RpcException(cause) {
            private static final long serialVersionUID = 1L;

            @Override
            public RpcStatus getRpcStatus() {
                return status;
            }
        };
    }

    /**
     * 静态工厂方法，根据异常消息创建一个新的RpcException实例。
     * 默认HTTP状态码为INTERNAL_SERVER_ERROR。
     * 
     * @param message 异常消息
     * @return 新的RpcException实例
     */
    public static RpcException newRpcException(RpcStatus status, String message) {
        return new RpcException(message) {
            private static final long serialVersionUID = 1L;

            @Override
            public RpcStatus getRpcStatus() {
                return status;
            }
        };
    }

    /**
     * 静态工厂方法，根据异常消息和Throwable对象创建一个新的RpcException实例。
     * 默认HTTP状态码为INTERNAL_SERVER_ERROR。
     * 
     * @param message 异常消息
     * @param cause   引发异常的原因
     * @return 新的RpcException实例
     */
    public static RpcException newRpcException(RpcStatus status, String message, Throwable cause) {
        return new RpcException(message, cause) {
            private static final long serialVersionUID = 1L;

            @Override
            public RpcStatus getRpcStatus() {
                return status;
            }
        };
    }

    public abstract RpcStatus getRpcStatus();

}