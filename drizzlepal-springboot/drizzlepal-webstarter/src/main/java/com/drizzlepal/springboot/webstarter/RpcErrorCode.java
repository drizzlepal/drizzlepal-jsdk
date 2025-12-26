package com.drizzlepal.springboot.webstarter;

import org.springframework.http.HttpStatus;

public interface RpcErrorCode {

    String getName();

    String getMessage();

    HttpStatus getHttpStatus();

}
