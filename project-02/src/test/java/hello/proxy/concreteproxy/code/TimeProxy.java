package hello.proxy.concreteproxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeProxy extends ConcreteLogic {

    private ConcreteLogic logic;

    public TimeProxy(ConcreteLogic logic) {
        this.logic = logic;
    }

    @Override
    public String operation() {
        log.info("TimeProxy 실행");
        long started = System.currentTimeMillis();

        String result = super.operation();

        long ended = System.currentTimeMillis();
        log.info("TimeProxy 종료 result-time: {}ms", (ended - started));
        return result;
    }
}
