package com.svalero.cityEvents.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ErrorResponse {
        public int code;
        public String title;
        public String message;
    }
