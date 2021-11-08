package com.taemmy.spring.advanced.app.v1;

import com.taemmy.spring.advanced.trace.TraceStatus;
import com.taemmy.spring.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderRepositoryV1.save()");
            // do
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }

            sleep(1_000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
