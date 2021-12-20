package hello.proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component {

    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long started = System.currentTimeMillis();

        String result = component.operation();

        long ended = System.currentTimeMillis();
        log.info("TimeDecorator 종료 result-time: {}ms", (ended - started));
        return result;
    }
}
