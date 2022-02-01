package com.hungry.consultorang.common.exception;

import lombok.experimental.StandardException;

@StandardException
public class LoginException extends CustomException {
    public LoginException() {
        super();
    }

    public LoginException(String message) {
        super(message);
    }
}
