package com.maciaszek.adrian;

public class AppException extends RuntimeException {

    private final String message;

    public AppException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
