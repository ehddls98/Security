package com.study.SpringSecurity.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.study.SpringSecurity.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/test")
    public ResponseEntity<?> get() {

        System.out.println(testService.aopTest()); //핵심 기능
        testService.aopTest2("김동인", 27);
        testService.aopTest3("010-1234-5678", "부산 진구");

        return ResponseEntity.ok("확인");
    }
}
