package com.l2p.hmps.exception;

import org.springframework.http.HttpStatus;


public class EmailSendingException extends HpmsException {

    public EmailSendingException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_500");
    }

    public EmailSendingException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_500");
        initCause(cause);
    }
}