package com.drizzlepal.jdbc.exception;

public abstract class JdbcException extends Exception {

    public JdbcException(String msg) {
        super(msg);
    }

    public JdbcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }

}
