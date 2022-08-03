package com.onlinebookstore.crud.exception;

public class InvalidDataException extends BookStoreException{

    public InvalidDataException(String errorCode, String message) {
        super(errorCode,message);
    }
}