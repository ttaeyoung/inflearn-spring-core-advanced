# 섹션 2

## 필드 동기화
* 문제점: 로그 추적기를 사용하기 위해 모든 함수에 TraceId 를 파라미터로 넘겨야 한다. 
TraceId 를 넘기지 않고 해결 할 수 있을까?

### 해결 방법
**LogTrace** 인터페이스를 구현해보자.
* begin, end, exception 을 제공하는 인터페이스

### 구현방법
* LogTrace 를 implements 한 FieldLogTrace 구현
* FieldLogTrace 를 Bean 으로 등록
 
### LogTrace 문제점
* Bean 으로 등록했다 > 싱글톤이다.
* 멤버필드로 들고 있는 traceIdHolder 에 여러 쓰레드가 접근할 수 있다.

## ThreadLocal
스레드 로컬은 해당 스레드만 접근할 수 있는 특별한 저장소를 말한다.
따라서 같은 인스턴스의 스레드 로컬 필드에 접근해도 문제 없다.

* 적용 전 동시성 문제 발생
```
23:55:15.949 [Test worker] INFO com.taemmy.spring.advanced.threadlocal.FieldServiceTest - main start
23:55:15.957 [thread-A] INFO com.taemmy.spring.advanced.threadlocal.code.FieldService - 저장 name=userA -> nameStore=null
23:55:16.059 [thread-B] INFO com.taemmy.spring.advanced.threadlocal.code.FieldService - 저장 name=userB -> nameStore=userA
23:55:16.971 [thread-A] INFO com.taemmy.spring.advanced.threadlocal.code.FieldService - 조회 nameStore=userB
23:55:17.062 [thread-B] INFO com.taemmy.spring.advanced.threadlocal.code.FieldService - 조회 nameStore=userB
23:55:19.063 [Test worker] INFO com.taemmy.spring.advanced.threadlocal.FieldServiceTest - main exit
```
* 적용 후 동시성 문제 해결
```
23:56:12.851 [Test worker] INFO com.taemmy.spring.advanced.threadlocal.ThreadLocalServiceTest - main start
23:56:12.859 [thread-A] INFO com.taemmy.spring.advanced.threadlocal.code.ThreadLocalService - 저장 name=userA -> nameStore=null
23:56:12.962 [thread-B] INFO com.taemmy.spring.advanced.threadlocal.code.ThreadLocalService - 저장 name=userB -> nameStore=null
23:56:13.873 [thread-A] INFO com.taemmy.spring.advanced.threadlocal.code.ThreadLocalService - 조회 nameStore=userA
23:56:13.966 [thread-B] INFO com.taemmy.spring.advanced.threadlocal.code.ThreadLocalService - 조회 nameStore=userB
23:56:15.965 [Test worker] INFO com.taemmy.spring.advanced.threadlocal.ThreadLocalServiceTest - main exit
```
