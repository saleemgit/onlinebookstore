package com.onlinebookstore.crud.exception.handler;

import com.onlinebookstore.crud.exception.InvalidDataException;
import com.onlinebookstore.crud.exception.model.ResponseObj;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookStoreExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    public ResponseObj handleInvalidDataException(InvalidDataException ex) {

        return ResponseObj.builder().errorCode(ex.getErrorCode()).message(ex.getMessage()).build();
    }

}


