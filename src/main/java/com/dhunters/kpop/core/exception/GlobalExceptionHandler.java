package com.dhunters.kpop.core.exception;


import com.dhunters.kpop.core.api.ApiError;
import com.dhunters.kpop.core.api.v2.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    /**
     * 401 (Unauthorized)
     * 403 (Forbidden)
     * 404 (Not Found)
     * 409 (Conflict)
     * 500 (Internal Server Error)
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleResponseStatus(ResponseStatusException ex) {
        log.info("handleResponseStatus:{}", ex.getMessage());
        return ApiResponse.error(ex.getStatusCode().value());
    }

    /**
     * 인증 실패
     * id or pw
     * 401 (Unauthorized)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleResponseStatus(BadCredentialsException ex) {
        log.info("handleResponseStatus:{}", ex.getMessage());
        return ApiResponse.error(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 토큰 만료
     * 401 (Unauthorized)
     */
    @ExceptionHandler(CredentialsExpiredException.class)
    public ResponseEntity<?> handleResponseStatus(CredentialsExpiredException ex) {
        log.info("handleResponseStatus:{}", ex.getMessage());
        return ApiResponse.error(HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * 정의하지 않은 오류
     * 500 (Internal Server Error)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(HttpServletRequest httpRequest, Exception e) throws Exception {
        log.info("handleAllExceptions:{}", e.getMessage());

        String uri = httpRequest.getRequestURI();
        if (uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui")) {
            throw e;
        }

        return ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
