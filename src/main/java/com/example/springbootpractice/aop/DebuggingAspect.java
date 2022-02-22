package com.example.springbootpractice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect // AOP 클래스 선언 : 부가 기능을 주입하는 클래스
@Component // Ioc 컨테이너가 해당 객체를 생성 및 관리하도록 함
public class DebuggingAspect {

    //대상 메소드 선택 : CommentService#create() (어느 지점에 들어갈 건지를 지정)
    @Pointcut("execution(* com.example.springbootpractice.service.CommentService.*(..))")
    private void cut(){}
    
    //실행 시점 설정
    //메소드가 실행되기 이전에 해당 부가 기능을 실행
    @Before("cut()") //-> cut()에 지정된 @PointCut()의 대상으로 지정이 됨
    public void loggingArgs(JoinPoint joinPoint){ // cut()의 대상 메소드(정확히는 메소드를 둘러 싼 결합 지점)
        // 입력 값 가져오기
        Object[] args = joinPoint.getArgs();
        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();

        // 입력값 로깅하기
        for (Object obj : args){
            log.info("{}#{}의 입력값 => {}", className, methodName, obj);
        }
    }

    // 실행 시점 설정 : cut()에 지정된 대상 호출 후 호출이 정상적으로 실행 됐을 때 해당 메소드를 실행
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void logginReturnValue(JoinPoint joinPoint, // cut()의 대상 메소드(대략적으로 메소드라고 이해하면 됨)
                                  Object returnObj){ // 리턴값
        // 클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        // 메소드명
        String methodName = joinPoint.getSignature().getName();
        
        // 반환값 로깅
        log.info("{}#{}의 반환값 => {}", className, methodName, returnObj);
    }
}
