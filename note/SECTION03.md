# 섹션 3

> 변하는 부분과 변하지 않는 부분을 잘 분리하는 것이다.

## 템플릿 메서드 패턴
다형성을 사용해서 변하는 부분과 변하지 않는 부분을 분리하는 방법이다.

### 익명 클래스
자바의 익명클래스(Anonymous Class)  
부모클래스의 인스턴스를 생성하면서 중괄호{}를 넣고 그 안에 처리구문을 넣어주면 된다. 이때 부모클래스의 메소드를 오버라이드 해주면 된다.
```java
AbstractTemplate template2 = new AbstractTemplate() {
    @Override
    protected void call() {
        log.info("비지니스 로직2 실행");
    }
};
```