package com.gerimedica.csvuploader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileParseException extends RuntimeException {

    public FileParseException(String message, Throwable exception) {
        super(message, exception);
    }
}
