package com.drizzlepal.springboot.webstarter;

import org.springframework.http.HttpStatus;

public enum CommonWebErrorCode implements WebErrorCode {

    Success("Success", "成功", HttpStatus.OK),
    ParamInvalid("ParamInvalid", "非法的参数", HttpStatus.BAD_REQUEST),
    ServerError("ServerError", "未标化服务错误", HttpStatus.INTERNAL_SERVER_ERROR),
    Unknown("Unknown", "未定义的错误", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String name;

    private final HttpStatus httpStatus;

    private final String message;

    CommonWebErrorCode(String name, String message, HttpStatus httpStatus) {
        this.name = name;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
