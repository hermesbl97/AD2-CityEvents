package com.svalero.cityEvents.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    public int code;
    public String title;
    public String message;
    private Map<String, String> errors;

//    private ErrorResponse(int code, String title, String message, Map<String, String> errors) {
//        this.code = code;
//        this.title = title;
//        this.message = message;
//        this.errors = errors;
//    }

    public static ErrorResponse generalError(int code, String title, String message) {
        return new ErrorResponse(code,title,message, new HashMap<>());
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404,"not-found", message, new HashMap<>());
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(400,"bad-request", "Validation error", errors);
    }
    public static ErrorResponse internalServerError() {
        return new ErrorResponse(500,"internal-server-error","There is a bug in the backend", new HashMap<>());
    }

}


