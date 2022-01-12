package com.hungry.consultorang.config;

import com.hungry.consultorang.common.exception.CustomException;
import com.hungry.consultorang.common.response.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

// TODO: 2022-01-12 현재 @Slf4j log에 한글을 넣으면 깨지는 현상 발생 고칠 필요 있음

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<RestResponse> loginExceptionHandler
            (HttpServletRequest request , CustomException e){
        RestResponse ret = new RestResponse();
        String errMsg = e.getMessage();

        log.error("loginExcpetion"+errMsg);
        return new ResponseEntity<RestResponse>(ret.setCustomFail(e), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> unkwonExceptionHandler
            (HttpServletRequest request, Exception e){
        RestResponse ret = new RestResponse();
        log.error(e.toString());
        e.printStackTrace();
        return new ResponseEntity<RestResponse>(ret.setProgrammingFail(e), HttpStatus.BAD_REQUEST);
    }
}
