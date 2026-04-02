package com.l2p.hmps.exception;

import org.springframework.http.HttpStatus;

public class AIServiceException extends HpmsException {

    public AIServiceException(String message) {
        super(message, HttpStatus.BAD_GATEWAY, "AI_SERVICE_ERROR");
    }

    public AIServiceException(String message, HttpStatus status, String errorCode) {
        super(message, status, errorCode);
    }
}