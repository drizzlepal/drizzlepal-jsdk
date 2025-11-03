package com.drizzlepal.utils.exception;

public class YamlLoadException extends YamlUtilException {

    public YamlLoadException(String message) {
        super(message);
    }

    public YamlLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public YamlLoadException(Throwable cause) {
        super(cause);
    }
}
