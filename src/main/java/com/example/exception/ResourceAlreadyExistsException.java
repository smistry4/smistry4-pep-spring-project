package com.example.exception;

public class ResourceAlreadyExistsException extends RuntimeException {

    /**
     * This message is used to throw 409 with custom message.
     * @param message the custom message
     */
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
}
