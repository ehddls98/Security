package com.study.SpringSecurity.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidException extends RuntimeException {

    @Getter
    private List<FieldError> fieldErrors;

    public ValidException(String message, List<FieldError> fieldErrors) { //ValidException 객체에 메시지와 필드 에러들을 받아서
        super(message); //RuntimeException을 생성할때 message를 넣어준다.
        this.fieldErrors = fieldErrors; //fieldErrors 리스트에 에러들을 담아준다.
    }
}
