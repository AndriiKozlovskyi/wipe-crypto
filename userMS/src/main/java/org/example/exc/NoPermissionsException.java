package org.example.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException(String message) {
        super(message);
    }
}
