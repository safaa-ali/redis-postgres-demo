package com.example.redis_demo.exception;

import java.time.LocalDateTime;

public class ApiError {
    private LocalDateTime timestamp;
    private String path;
    private int status;
    private String error;
    private String message;

    public ApiError(LocalDateTime timestamp, String path, int status, String error, String message) {
        this.timestamp = timestamp;
        this.path = path;
        this.status = status;
        this.error = error;
        this.message = message;
    }

    // Getters only
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPath() { return path; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
}
