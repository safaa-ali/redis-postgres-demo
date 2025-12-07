package com.example.login.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ApiError buildError(Exception ex, WebRequest request, HttpStatus status, String error) {
        return new ApiError(
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                status.value(),
                error,
                ex.getMessage()
        );
    }

    // 404 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(ex, request, HttpStatus.NOT_FOUND, "Not Found"));
    }

    // 400 BAD REQUEST
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildError(ex, request, HttpStatus.BAD_REQUEST, "Bad Request"));
    }

    // 409 CONFLICT
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(ConflictException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildError(ex, request, HttpStatus.CONFLICT, "Conflict"));
    }

    // 400 VALIDATION ERRORS (DTO)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {

//        String validationMessage = ex.getBindingResult()
//                .getFieldErrors()
//                .stream()
//                .map(err -> err.getField() + ": " + err.getDefaultMessage())
//                .collect(Collectors.joining(", "));
//
//        ApiError error = new ApiError(
//                LocalDateTime.now(),
//                request.getDescription(false).replace("uri=", ""),
//                HttpStatus.BAD_REQUEST.value(),
//                "Validation Error",
//                validationMessage
//        );
//
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                request.getDescription(false).replace("uri=", ""),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                errors
        );

        return ResponseEntity.badRequest().body(apiError);
    }


    // 500 INTERNAL SERVER ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
    }
}

