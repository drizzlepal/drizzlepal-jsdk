package com.drizzlepal.springboot.webstarter;

import org.springframework.http.HttpStatus;

public interface WebErrorCode {

    String getName();

    String getMessage();

    HttpStatus getHttpStatus();

}
