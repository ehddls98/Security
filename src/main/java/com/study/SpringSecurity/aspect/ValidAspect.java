package com.study.SpringSecurity.aspect;

import com.study.SpringSecurity.dto.request.ReqSignupDto;
import com.study.SpringSecurity.exception.ValidException;
import com.study.SpringSecurity.service.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

@Slf4j
@Aspect
@Component
public class ValidAspect {

    @Autowired
    private SignupService signupService;

    @Pointcut("@annotation(com.study.SpringSecurity.aspect.annotation.ValidAop)")
    private void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable { //ProceedingJoinPoint: 핵심기능(메소드) 정보가 담겨있다.(변수명, 클래스명, 매개변수 등)
        Object[] args = proceedingJoinPoint.getArgs(); //변수: ReqSignupDto, BeanPropertyBindingResult

        BeanPropertyBindingResult bindingResult = null;

        for (Object arg : args) { //ReqSignupDto, BindingResult를 object로 업캐스팅해서 반복
            if (arg.getClass() == BeanPropertyBindingResult.class) { //변수가 bindingResult와 같은 객체면
                bindingResult = (BeanPropertyBindingResult) arg; //다시 BeanPropertyBindingResult로 다운캐스팅해서 bindingResult에 넣는다
                break;
            }
        }
        //signature: 매개변수명, 클래스명, 패키지명, 메소드명 등이 담겨있다. -> 하위에 codeSignature:
        switch (proceedingJoinPoint.getSignature().getName()) { //.getname() : 메소드명
            case "signup":
                validSignupDto(args, bindingResult);
                break;
        }

        if (bindingResult.hasErrors()) { //checkPassword에서 유효성 오류가 있었는지 확인하고, 만약에 있으면
            throw new ValidException("유효성 검사 오류", bindingResult.getFieldErrors()); //생성을 하고 ExceptionControllerAdvice의 @ExceptionHandler에 던져준다.
        }

        return proceedingJoinPoint.proceed(); //에러가 없으면 signup(서비스)를 실행
    }

    private void validSignupDto(Object[] args, BeanPropertyBindingResult bindingResult) {
        for (Object arg : args) { //ReqSignupDto, BindingResult를 object로 업캐스팅해서 반복
            if (arg.getClass() == ReqSignupDto.class) { //변수가 ReqSignupDto와 같은 객체면
                ReqSignupDto dto = (ReqSignupDto) arg; //dto로 다운캐스팅
                if (!dto.getPassword().equals(dto.getCheckPassword())) { //비밀번호가 일치하는지 확인한다.
                    FieldError fieldError = new FieldError("checkPassword", "checkPassword", "비밀번호가 일치하지 않습니다.");
                    bindingResult.addError(fieldError);
                }
                if(signupService.isDuplicatedUsername(dto.getUsername())) {
                    FieldError fieldError = new FieldError("username", "username", "이미 존재하는 사용자 이름입니다.");
                    bindingResult.addError(fieldError);
                }
                break;
            }
        }
    }
}

