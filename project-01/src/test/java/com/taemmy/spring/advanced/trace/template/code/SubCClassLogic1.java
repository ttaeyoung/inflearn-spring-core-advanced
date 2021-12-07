package com.taemmy.spring.advanced.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubCClassLogic1 extends AbstractTemplate {
    @Override
    protected void call() {
      log.info("비지니스 로직1 실행");
    }
}
