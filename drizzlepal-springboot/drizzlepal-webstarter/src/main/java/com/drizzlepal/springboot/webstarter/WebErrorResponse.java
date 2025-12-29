package com.drizzlepal.springboot.webstarter;

import lombok.Data;

@Data
public class WebErrorResponse {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public WebErrorResponse(WebErrorCode code, String message) {
        this.code = code.getName();
        this.message = message;
    }

}
