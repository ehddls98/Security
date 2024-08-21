package com.study.SpringSecurity.service;

import com.study.SpringSecurity.aspect.annotation.Test2Aop;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    //aspect에서 around 메서드에서 핵심 기능 호출 때 실행된다.
    public String aopTest() {
        System.out.println("AOP 테스트 입니다.");
        return "AOP 테스트 입니다.";
    }

    @Test2Aop
    public void aopTest2(String name, int age) {
        //aop 라이브러리에 의해 리턴 자료형이 void 면 null을 리턴한다.
        System.out.println("이름: " + name);
        System.out.println("나이: " + age);
        System.out.println("AOP 테스트2 입니다.");
    }

    @Test2Aop
    public void aopTest3(String phone, String address) {
        System.out.println("AOP 테스트3 입니다.");
    }
}
