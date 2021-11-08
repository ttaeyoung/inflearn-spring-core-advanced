# 섹션 1
## 프로젝트 생성
* dependencies
    * springboot-web
    * lombok
    
## 예제 프로젝트 만들기
v0/Order 만들기 

## 로그 추적기 요구사항 분석
### 요구사항
* 모든 PUBLIC 메서드의 호출과 응답 정보를 로그로 출력
* 애플리케이션의 흐름을 변경하면 안됨
* 메소드 호출에 걸린 시간
* 정상 흐름과 예외 흐름 구분
* HTTP 요청을 구분
  * HTTP 요청 단위로 특정 ID를 남겨서 어떤 HTTP 요청에서 시작된 것인지 명확하게 구분이 가능해야 함
  
### 프로토 타입 만들기
* TraceId: id, level 을 가지는 클래스 정의
* TraceStatus: TraceId, start timestamp, message 를 가지는 클래스 정의

### 로그추적기 v1 적용
* HelloTraceV1
  * TraceStatus begin(String message)
  * void end(TraceStatus status)
  * void exception(TraceStatus status, Exception e)

* 적용방법
  * 각 함수에서 TraceStatus 를 생성 & 종료(or 예외처리) 를 호출

* 문제점
  * 메서드 호출의 깊이 표현
  * HTTP 요청을 구분(트랜잭션 ID)
  
### 로그추적기 v2 적용
* HelloTraceV2
  * TraceStatus beginSync(TraceId beforeTraceId, String message) 추가
  
* 적용방법
  * 각 함수를 호출할때 현재의 TraceId 를 파라미터로 전달
  
* 문제점
  * HTTP 요청을 구분하기 위해 TraceId 를 동기화를 위해
    * TraceId 의 동기화를 위해서 관련 메서드의 모든 파라미터를 수정해야함
  * begin & beginSync 를 구분해서 호출해야 함
  
> TraceId 를 파라미터로 넘기는 거 말고 방법이 없나?