package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.exception.ValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //컴포넌트이기 때문에 IOC 안에서 터지는 예외만 가져올 수 있다.
public class ExceptionControllerAdvice {

    @ExceptionHandler(ValidException.class) //ValidAspect에서 ValidException이 터지는지 모니터링 하고 있다.
    public ResponseEntity<?> validException(ValidException validException) { //ValidException을 매개변수로 받는다.
        return ResponseEntity.badRequest().body(validException.getFieldErrors()); //validException의 에러(badRequest: 400)를 리턴한다.
    }
}
