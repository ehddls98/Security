package com.study.SpringSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 2)
public class TestAspect {

    //AOP 사용을 위한 기본 틀
    //각 메서드명에 해당하는 어노테이션을 달아준다.

    @Pointcut("execution(* com.study.SpringSecurity.service.*.aop*(..))")
    private void pointCut() {} //어떤 메서드에 around 메서드를 넣을지 정의

    @Around("pointCut()") //pointCut()에서 명시한 메서드에 동작한다.
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("전처리");
        Object result = proceedingJoinPoint.proceed(); //TestAspect2의 결과를 result에 넣는다.
        //핵심기능 호출(TestService에 들어있는 aopTest), result에는 aopTest 메서드의 실행 결과가 담긴다.
        //aopTest2에서 받은 null 값을 result에 대입
        System.out.println("후처리");

        return result; //핵심기능 실행 결과를 리턴한다.
    }
}