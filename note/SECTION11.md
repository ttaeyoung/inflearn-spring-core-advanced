# 섹션 11

## 포인트컷 지시자
포인트컷 표현식은 `execution` 과 같은 지시자(Pointcut Designator) 로 시작한다. 줄여서 PCD 라고 한다.

### 포인트컷 지시자 종류
* execution : 메소드 실행 조인 포인트를 매칭한다. 스프링 AOP 에서 가장 많이 사용하고, 기능도 복잡하다. 
* within : 특정 타입 내의 조인 포인트를 매칭한다. 
* args : 인자가 주어진 타입의 인스턴스인 조인 포인트 
* this : 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인 포인트 
* target : Target 객체(스프링 AOP 프록시가 가르키는 실제 대상)를 대상으로 하는 조인 포인트 
* @target : 실행 객체의 클래스에 주어진 타입의 애노테이션이 있는 조인 포인트 
* @within : 주어진 애노테이션이 있는 타입 내 조인 포인트 
* @annotation : 메서드가 주어진 애노테이션을 가지고 있는 조인 포인트를 매칭 
* @args : 전달된 실제 인수의 런타임 타입이 주어진 타입의 애노테이션을 갖는 조인 포인트 
* bean : 스프링 전용 포인트컷 지시자, 빈의 이름으로 포인트컷을 지정한다.

### execution

### within
특정 타입 내의 조인 포인트를 매칭한다 == execution 에 타입구분만 한다
> 그런데 within 사용시 주의해야 할 점이 있다. 표현식에 부모 타입을 지정하면 안된다는 점이다. 정확하게 타입이 맞아야 한다.

### args
인자가 주어진 타입의 인스턴스인 조인 포인트

#### execution 과 차이는?
execution 은 파라미터 타입이 정확하게 매칭되어야 한다. execution 은 클래스에 선언된 정보를 기반으로 판단한다.  
args 는 부모 타입을 허용한다. args 는 실제 넘어온 파라미터 객체 인스턴스를 보고 판단한다.

### @target, @within
* @target: 인스턴스의 모든 메서드를 조인 포인트로 적용한다. 자식 타입에 어노테이션이 있는 경우, 부모 타입의 메소드도 적용된다.
* @within: 해당 타입 내에 있는 메서드만 조인 포인트로 적용한다. 자식 타입에 어노테이션이 있는 경우, 부모 타입의 메소드는 적용하지 않는다.

### @annotation, @args

### bean
스프링 빈의 이름으로 AOP 적용여부를 지정한다. 이것은 스프링에서만 사용할 수 있는 특별한 지시자다.

### 매개변수 전달
다음 포인트컷 표현식을 사용해서 어드바이스에 매개변수를 전달할 수 있다.
this, target, args, @target, @within, @annotation, @args

### this, target
* this: 스프링 빈 객체(스프링 AOP 프록시) 로 하는 조인 포인트
* target: Target 객체(스프링 AOP 프록시가 가르키는 실제 대상)를 대상으로 하는 조인 포인트

#### JDK 동적 프록시 vs CGLIB 프록시의 차이
* JDK 동적 프록시: 인터페이스가 필수이고, 인터페이스를 구현한 프록시 객체를 생성한다.
* CGLIB: 인터페이스가 있어도 구체 클래스를 상속 받아서 프록시 객체를 생성한다.

> 두 방식에 차이에 따라 this 의 동작의 차이가 발생할 수 있음을 인지한다.
