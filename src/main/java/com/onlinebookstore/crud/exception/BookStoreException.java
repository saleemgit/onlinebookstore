package com.onlinebookstore.crud.exception;

import lombok.Getter;

@Getter
public class BookStoreException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private String errorCode;
    private final String message;

    public BookStoreException(String message) {
        super(message);
        this.message = message;
    }

    public BookStoreException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }

}