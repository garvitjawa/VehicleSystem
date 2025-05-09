package com.bachatt.challanSystem.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String id) {
        super("%s not found with ID: %s".formatted(resource, id));
    }
}

