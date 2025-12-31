package com.drizzlepal.springboot.webstarter;

public abstract class WebException extends RuntimeException {

    private final WebErrorCode code;

    public WebException() {
        super(CommonWebErrorCode.Unknown.getMessage());
        this.code = CommonWebErrorCode.Unknown;
    }

    public WebException(String detail) {
        super(detail);
        this.code = CommonWebErrorCode.Unknown;
    }

    public WebException(Throwable cause) {
        super(cause);
        this.code = CommonWebErrorCode.Unknown;
    }

    public WebException(String detail, Throwable cause) {
        super(detail, cause);
        this.code = CommonWebErrorCode.Unknown;
    }

    public WebException(WebErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public WebException(WebErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public WebException(WebErrorCode code, String detail) {
        super(detail);
        this.code = code;
    }

    public WebException(WebErrorCode code, String detail, Throwable cause) {
        super(detail, cause);
        this.code = code;
    }

    public WebErrorCode getCode() {
        return code;
    }

    public static WebException newWebException(Throwable cause) {
        return new WebException(cause) {
        };
    }

    public static WebException newWebException(String detail) {
        return new WebException(detail) {
        };
    }

    public static WebException newWebException(String detail, Throwable cause) {
        return new WebException(detail, cause) {
        };
    }

    public static WebException newWebException(WebErrorCode code) {
        return new WebException(code) {
        };
    }

    public static WebException newWebException(WebErrorCode code, Throwable cause) {
        return new WebException(code, cause) {
        };
    }

    public static WebException newWebException(WebErrorCode code, String detail) {
        return new WebException(code, detail) {
        };
    }

    public static WebException newRpcException(WebErrorCode code, String detail, Throwable cause) {
        return new WebException(code, detail, cause) {
        };
    }

}