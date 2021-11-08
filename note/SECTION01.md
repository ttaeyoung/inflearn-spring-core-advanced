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
  * begin, end, exception 메소드 제공
* 적용방법
  * 각 method 에서 TraceStatus 를 생성 & 종료(or 예외처리) 를 호출