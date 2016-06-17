package com.hi.transformer.horoscops.manager;

public class RESTClientException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private final int code;
    private final String reason;

    public RESTClientException(int code, String reason) {
        this.code = code;
        this.reason = reason == null ? "" : reason.trim();
    }

    public int getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        if(reason.isEmpty()) {
            return "Error code " + code;
        } else {
            return reason + " (error code " + code + ")";
        }
    }
}
