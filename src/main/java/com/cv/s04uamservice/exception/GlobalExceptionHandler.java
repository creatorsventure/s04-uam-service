package com.cv.s04uamservice.exception;

import com.cv.s01coreservice.enumeration.APIResponseType;
import com.cv.s04uamservice.util.StaticUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<Object> handleException(Exception e) {
        log.error("GlobalExceptionHandler.handleException {}", ExceptionUtils.getMessage(e));
        if (e instanceof ApplicationException) {
            return StaticUtil.getFailureResponse(e, APIResponseType.MESSAGE_CODE);
        }
        return StaticUtil.getFailureResponse(e, APIResponseType.MESSAGE_ACTUAL);
    }
}
