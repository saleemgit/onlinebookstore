package com.onlinebookstore.crud.exception.model;

import lombok.Builder;

@Builder
public class ResponseObj {

    private String message;
    private String errorCode;

    public String getStatusCode() {
        return errorCode;
    }

    public void setStatusCode(String statusCode) {
        this.errorCode = statusCode;
    }

    public ResponseObj(String message, String statusCode) {
        this.message = message;
        this.errorCode = statusCode;
    }

    public ResponseObj(String msg){
        this.message = msg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
