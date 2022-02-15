package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;


public class Pointcuts {

    // 포인트 컷 시그니쳐
    @Pointcut("execution(* hello.aop.order..*(..))")
    public void allOrder() {
        // 반환타입은 void & 함수 구현부는 비워두면 된다
    }

    // 클래스 이름을 패턴으로 사용하는 포인트 컷 시그니쳐
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {

    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {

    }
}
