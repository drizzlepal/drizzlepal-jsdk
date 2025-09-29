package com.drizzlepal.springboot.miniostarter.exception;

public class DrizzlepalMinioObjectOpException extends Exception {

    private static final long serialVersionUID = 1L;

    public DrizzlepalMinioObjectOpException(Throwable cause) {
        super(cause);
    }

    public DrizzlepalMinioObjectOpException(String message) {
        super(message);
    }

    public DrizzlepalMinioObjectOpException(String message, Throwable cause) {
        super(message, cause);
    }

}
