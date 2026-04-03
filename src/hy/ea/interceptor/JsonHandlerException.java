package hy.ea.interceptor;

public class JsonHandlerException extends Exception {
    private String code;

    public JsonHandlerException() {
    }

    public JsonHandlerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public JsonHandlerException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public JsonHandlerException(Throwable cause) {
        super(cause);
    }

    public JsonHandlerException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
