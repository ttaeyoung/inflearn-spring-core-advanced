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

### Controller 적용
* 적용 전
```
2021-11-17 00:08:55.872  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] OrderController.request()
2021-11-17 00:08:55.873  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |-->OrderService.orderItem()
2021-11-17 00:08:55.873  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |-->OrderRepository.save()
2021-11-17 00:08:56.877  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |<--OrderRepository.save() time=1004ms
2021-11-17 00:08:56.878  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |<--OrderService.orderItem() time=1005ms
2021-11-17 00:08:56.879  INFO 4425 --- [nio-8080-exec-1] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] OrderController.request() time=1007ms

2021-11-17 00:08:55.946  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |-->OrderController.request()
2021-11-17 00:08:55.946  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |   |-->OrderService.orderItem()
2021-11-17 00:08:55.946  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |   |   |-->OrderRepository.save()
2021-11-17 00:08:56.947  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |   |   |<--OrderRepository.save() time=1001ms
2021-11-17 00:08:56.948  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |   |<--OrderService.orderItem() time=1002ms
2021-11-17 00:08:56.949  INFO 4425 --- [nio-8080-exec-2] c.t.s.a.trace.logtrace.FieldLogTrace     : [dc426adc] |   |   |<--OrderController.request() time=1003ms
```

* 적용 후 
```
2021-11-17 00:07:10.493  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] OrderController.request()
2021-11-17 00:07:10.494  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] |-->OrderService.orderItem()
2021-11-17 00:07:10.494  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] |   |-->OrderRepository.save()
2021-11-17 00:07:11.496  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] |   |<--OrderRepository.save() time=1002ms
2021-11-17 00:07:11.497  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] |<--OrderService.orderItem() time=1003ms
2021-11-17 00:07:11.497  INFO 4407 --- [nio-8080-exec-2] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [8a95d75e] OrderController.request() time=1004ms

2021-11-17 00:07:10.665  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] OrderController.request()
2021-11-17 00:07:10.665  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] |-->OrderService.orderItem()
2021-11-17 00:07:10.666  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] |   |-->OrderRepository.save()
2021-11-17 00:07:11.670  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] |   |<--OrderRepository.save() time=1004ms
2021-11-17 00:07:11.671  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] |<--OrderService.orderItem() time=1006ms
2021-11-17 00:07:11.671  INFO 4407 --- [nio-8080-exec-3] c.t.s.a.t.logtrace.ThreadLocalLogTrace   : [f5836a1c] OrderController.request() time=1006ms
```

### 스레드 로컬 주의사항 
스레드 로컬의 값을 사용 후 제거하지 않고 그냥 두면 WAS 처럼 스레드 풀을 사용하는 경우 심각한 문제가 발생할 수 있다.
* 스레드 풀을 사용하는 경우, 스레드가 사용이 끝난 후에 스레드 풀에 반환된다.
* 스레드 로컬에 정보를 삭제하지 않은 경우 ? 
  * 스레드 로컬에는 이전 스레드의 값이 남아있어서 잘못된 정보를 접근할 수 있게 된다.