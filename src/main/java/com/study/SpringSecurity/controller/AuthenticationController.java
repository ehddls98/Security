package com.study.SpringSecurity.controller;

import com.study.SpringSecurity.aspect.annotation.ParamsAop;
import com.study.SpringSecurity.aspect.annotation.ValidAop;
import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    @Autowired
    private SignupService signupService;

    @ValidAop
    @ParamsAop
    @PostMapping("/signup")
    //valid 어노테이션이 있으면 ReqSignupDto의 값들의 유효성 확인을 한다.(String 값만 유효성 검사 가능) 그리고 유효하지 않은 값에 대해 fieldError 객체를 생성한다.
    //BeanPropertyBindingResult fieldError가 담긴 객체이다. 에러가 없어도 null 값을 가진 BeanPropertyBindingResult 객체가 생성된다.
    //BeanPropertyBindingResult 객체는 valid 어노테이션이 생성하는것이다.
    public ResponseEntity<?> signup(@Valid @RequestBody ReqSignupDto dto, BeanPropertyBindingResult bindingResult) {
        return ResponseEntity.created(null).body(signupService.signup(dto));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> user(@PathVariable Long id) {
        return ResponseEntity.ok().body(signupService.getUser(id));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(signupService.getAllUsers());
    }
}
