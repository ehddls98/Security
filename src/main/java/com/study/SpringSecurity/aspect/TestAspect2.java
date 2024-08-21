package com.study.SpringSecurity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(value = 1)
public class TestAspect2 {

    //AOP 사용을 위한 기본 틀
    //각 메서드명에 해당하는 어노테이션을 달아준다.

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.Test2Aop)")
    private void pointCut() {} //해당 어노테이션이 있는 메서드를 찾아서 around 메서드를 실행

    @Around("pointCut()") //핵심기능이 호출되면 무조건 around 메서드가 동작한다.
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        System.out.println(signature.getName()); //메소드명 출력
        System.out.println(signature.getDeclaringTypeName()); //클래스명 출력

        Object[] args = proceedingJoinPoint.getArgs(); //매개변수를 배열에 저장
        String[] paramNames = signature.getParameterNames(); //매개변수명을 배열에 저장

        for(int i = 0; i < args.length; i++) {
            System.out.println(paramNames[i] + ": " + args[i]);
        }

        System.out.println("전처리2");
        Object result = proceedingJoinPoint.proceed(); //TestAspect의 실행결과를 result에 넣는다.
        //핵심기능 호출(TestService에 들어있는 aopTest), result에는 aopTest 메서드의 실행 결과가 담긴다.
        //pointcut execution에 의해 aspect1으로 간다.
        System.out.println("후처리2");

        return result; //핵심기능 실행 결과를 리턴한다.
    }
}
