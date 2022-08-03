package com.onlinebookstore.crud.exception.model;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INVALID_DATA("INVALID_DATA", "Unable to find the book data"),
    DATA_NOT_FOUND("DATA_NOT_FOUND", "Unable to delete the Book, data not found");

    String errorCode;
    String message;

    ErrorCode(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
