package com.cv.s04uamservice.controller;

import com.cv.s01coreservice.controller.generic.GenericController;
import com.cv.s01coreservice.dto.PaginationDto;
import com.cv.s01coreservice.dto.UserDto;
import com.cv.s01coreservice.enumeration.APIResponseType;
import com.cv.s04uamservice.constant.UAMConstant;
import com.cv.s04uamservice.service.intrface.UserService;
import com.cv.s04uamservice.util.StaticUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UAMConstant.APP_NAVIGATION_API_USER)
@AllArgsConstructor
@Slf4j
public class UserController implements GenericController<UserDto> {

    private UserService service;

    @PostMapping
    @Override
    public ResponseEntity<Object> create(@RequestBody @Valid UserDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("UserController.create {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result.getAllErrors(), APIResponseType.MESSAGE_CODE_LIST);
            }
            return StaticUtil.getSuccessResponse(service.create(dto), APIResponseType.OBJECT_ONE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.create {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @PutMapping
    @Override
    public ResponseEntity<Object> update(@RequestBody @Valid UserDto dto, BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("UserController.update {}", result.getAllErrors());
                return StaticUtil.getFailureResponse(result.getAllErrors(), APIResponseType.MESSAGE_CODE_LIST);
            }
            return StaticUtil.getSuccessResponse(service.update(dto), APIResponseType.OBJECT_ONE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.update {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @PatchMapping
    @Override
    public ResponseEntity<Object> updateStatus(@RequestParam String id, @RequestParam boolean status) {
        try {
            return StaticUtil.getSuccessResponse(service.updateStatus(id, status), APIResponseType.OBJECT_ONE,
                    HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.updateStatus {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @GetMapping
    @Override
    public ResponseEntity<Object> readOne(@RequestParam String id) {
        try {
            return StaticUtil.getSuccessResponse(service.readOne(id), APIResponseType.OBJECT_ONE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.readOne {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @DeleteMapping
    @Override
    public ResponseEntity<Object> delete(@RequestParam String id) {
        try {
            return StaticUtil.getSuccessResponse(service.delete(id), APIResponseType.OBJECT_ONE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.delete {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @PostMapping(UAMConstant.APP_NAVIGATION_REST_READ_PAGE)
    @Override
    public ResponseEntity<Object> readPage(@RequestBody PaginationDto dto) {
        try {
            return StaticUtil.getSuccessResponse(service.readAll(dto), APIResponseType.OBJECT_LIST, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.readPage {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

    @GetMapping(UAMConstant.APP_NAVIGATION_REST_READ_ID_NAME_MAP)
    @Override
    public ResponseEntity<Object> readIdNameMapping(@RequestParam String id) {
        try {
            return StaticUtil.getSuccessResponse(service.readIdAndNameMap(), APIResponseType.OBJECT_ONE, HttpStatus.OK);
        } catch (Exception e) {
            log.error("UserController.readIdNameMapping {}", ExceptionUtils.getStackTrace(e));
            return StaticUtil.getFailureResponse(ExceptionUtils.getStackTrace(e), APIResponseType.MESSAGE_ACTUAL);
        }
    }

}
