package com.taemmy.spring.advanced.trace.logtrace;

import com.taemmy.spring.advanced.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
