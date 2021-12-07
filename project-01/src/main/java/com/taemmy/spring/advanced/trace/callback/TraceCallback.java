package com.taemmy.spring.advanced.trace.callback;

public interface TraceCallback<T> {
    T call();
}
