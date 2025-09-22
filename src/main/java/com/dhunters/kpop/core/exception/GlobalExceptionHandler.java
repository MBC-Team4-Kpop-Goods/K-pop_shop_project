package com.dhunters.kpop.core.exception;


import com.dhunters.kpop.core.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiError handleAllExceptions(Exception e) {

        var status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        var message = e.getMessage();

        return new ApiError(status, message);
    }

    // 404 전용
    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(NotFound e) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }



}
