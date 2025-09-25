package com.dhunters.kpop.core.exception;


import com.dhunters.kpop.core.api.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleAllExceptions(Exception e) {
        log.error("예상치 못한 오류 발생", e);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }



    // 이미지 Exception

    @ExceptionHandler(ImageExceptions.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleImageNotFoundException(ImageExceptions.NotFound e) {
        log.error("이미지 조회 실패", e);
        return new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(ImageExceptions.UploadFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleFileUploadException(ImageExceptions.UploadFailed e) {
        log.error("파일 업로드 실패", e);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(ImageExceptions.InvalidFileType.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidFileTypeException(ImageExceptions.InvalidFileType e) {
        log.error("잘못된 파일 형식", e);
        return new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ImageExceptions.ProcessingFailed.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleImageProcessingException(ImageExceptions.ProcessingFailed e) {
        log.error("이미지 처리 실패", e);
        return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(ImageExceptions.FileSizeExceeded.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleFileSizeExceededException(ImageExceptions.FileSizeExceeded e) {
        log.error("파일 크기 초과", e);
        return new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("파일 크기 초과", e);
        return new ApiError(HttpStatus.BAD_REQUEST.value(), "업로드 파일 크기가 너무 큽니다.");
    }

    // 404 전용
    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(NotFound e) {
        return new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }



}
