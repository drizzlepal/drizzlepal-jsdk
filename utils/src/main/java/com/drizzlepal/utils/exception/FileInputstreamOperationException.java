package com.drizzlepal.utils.exception;

public class FileInputstreamOperationException extends FileUtilException {

    public FileInputstreamOperationException(String message) {
        super(message);
    }

    public FileInputstreamOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileInputstreamOperationException(Throwable cause) {
        super(cause);
    }
}
