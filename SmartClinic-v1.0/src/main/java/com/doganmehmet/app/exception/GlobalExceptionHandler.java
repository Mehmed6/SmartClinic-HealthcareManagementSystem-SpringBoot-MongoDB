package com.doganmehmet.app.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String, String>> handleApiExceptions(ApiException apiException, WebRequest request)
    {
        var errorResponse = new HashMap<String, String>();
        errorResponse.put("errorCode", apiException.getMyError().getErrorCode());
        errorResponse.put("message", apiException.getMessage());
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));
        errorResponse.put("errorTime", formatter.format(java.time.LocalDateTime.now()));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException exception, WebRequest request)
    {
        var errorResponse = new HashMap<String, String>();
        var errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        errorResponse.put("errorCode", "VALIDATION_ERROR");
        errorResponse.put("message", errorMessages);
        errorResponse.put("path", request.getDescription(false).replace("uri=", ""));
        errorResponse.put("errorTime", formatter.format(java.time.LocalDateTime.now()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
