package com.justweighit.errors;

public class ErrorResponse {
    private String id;
    private ErrorCode code;
    private String message;

    public ErrorResponse(String id, ErrorCode code, String message) {
        this.id = id;
        this.code = code;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public ErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
