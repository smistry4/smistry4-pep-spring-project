package com.example.exception;

public class ClientErrorException extends RuntimeException {

    /**
     * This exception is used to throw 400, with custom message.
     * @param message custom message
     */
    public ClientErrorException(String message) {
        super(message);
    }
}