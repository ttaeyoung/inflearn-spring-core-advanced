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
