package com.cv.s04uamservice.util;

import com.cv.s01coreservice.constant.ApplicationResponseConstant;
import com.cv.s01coreservice.dto.APIResponseDto;
import com.cv.s01coreservice.enumeration.APIResponseType;
import com.cv.s04uamservice.exception.ApplicationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StaticUtil {

    public static ResponseEntity<Object> getSuccessResponse(Object o, APIResponseType type, HttpStatus httpStatus) {
        try {
            return new ResponseEntity<>(
                    new APIResponseDto(true, ApplicationResponseConstant.SUCCESS, type.getValue(), o), httpStatus);
        } catch (Exception ex) {
            log.error("StaticUtil.getSuccessResponse", ex);
            return new ResponseEntity<>(new APIResponseDto(false,
                    ((o != null && StringUtils.hasText(o.toString())) ? o.toString()
                            : ApplicationResponseConstant.FAILURE),
                    APIResponseType.MESSAGE_ACTUAL.getValue(), ExceptionUtils.getMessage(ex)), HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Object> getFailureResponse(Object o, APIResponseType type) {
        try {
            if (o instanceof ApplicationException) {
                return new ResponseEntity<>(
                        new APIResponseDto(false, ((ApplicationException) o).getMessage(), type.getValue(), o),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new APIResponseDto(false,
                    ((o != null && StringUtils.hasText(o.toString())) ? o.toString()
                            : ApplicationResponseConstant.FAILURE),
                    APIResponseType.MESSAGE_ACTUAL.getValue(), null), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("StaticUtil.getFailureResponse {1}", ex);
            return new ResponseEntity<>(new APIResponseDto(false,
                    ((o != null && StringUtils.hasText(o.toString())) ? o.toString()
                            : ApplicationResponseConstant.FAILURE),
                    APIResponseType.MESSAGE_ACTUAL.getValue(), ExceptionUtils.getMessage(ex)), HttpStatus.BAD_REQUEST);
        }
    }

}
