package com.parlakci.ciftlikbank.domain.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BusinessException extends RuntimeException {

    private final Serializable[] parameters;

    public BusinessException(String message, Serializable... parameters) {
        super(message);
        this.parameters = parameters;
    }
}