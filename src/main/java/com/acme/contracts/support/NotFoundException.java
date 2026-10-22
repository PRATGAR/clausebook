package com.acme.contracts.support;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String what, long id) {
        super(what + " " + id + " not found");
    }
}
