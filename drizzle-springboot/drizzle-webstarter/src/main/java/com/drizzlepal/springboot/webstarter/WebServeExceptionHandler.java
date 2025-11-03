package com.drizzlepal.springboot.webstarter;

import java.util.StringJoiner;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.drizzlepal.rpc.RpcException;
import com.drizzlepal.rpc.RpcResponse;
import com.drizzlepal.rpc.RpcStatusCommon;

import lombok.extern.slf4j.Slf4j;

/**
 * Web服务异常处理器
 * 用于统一处理Web层抛出的异常，将异常转换为标准的RpcResponse格式返回给客户端
 */
@Slf4j
@ControllerAdvice(annotations = { RestController.class })
public class WebServeExceptionHandler {

    /**
     * 处理RPC异常
     * 当系统抛出RpcException时，记录错误日志并返回500状态码的失败响应
     * 
     * @param ex RPC异常对象
     * @return 包含异常信息的RpcResponse响应实体
     */
    @ResponseBody
    @ExceptionHandler(RpcException.class)
    public ResponseEntity<RpcResponse<?>> handleRpcException(RpcException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(RpcResponse.failed(ex));
    }

    /**
     * 处理方法参数验证异常
     * 当请求参数验证失败时，收集所有字段错误信息并返回500状态码的失败响应
     * 
     * @param ex 方法参数验证异常对象
     * @return 包含参数验证错误信息的RpcResponse响应实体
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RpcResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringJoiner stringJoiner = new StringJoiner(";");
        for (FieldError fieldError : ex.getFieldErrors()) {
            stringJoiner.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(RpcResponse.failed(
                        RpcException.newRpcException(RpcStatusCommon.PARAM_INVALID, stringJoiner.toString(), ex)));
    }

    /**
     * 处理未捕获的其他异常
     * 作为兜底异常处理器，处理所有未被其他处理器捕获的异常
     * 
     * @param cause 异常对象
     * @return 包含未知错误信息的RpcResponse响应实体
     */
    @ResponseBody
    @ExceptionHandler({ Throwable.class })
    public ResponseEntity<RpcResponse<?>> handleException(Throwable cause) {
        log.error("未处理的异常", cause);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .body(RpcResponse.failed(RpcStatusCommon.UNKNOWN_ERROR));
    }

}