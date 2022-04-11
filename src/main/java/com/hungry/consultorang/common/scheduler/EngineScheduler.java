package com.hungry.consultorang.common.scheduler;

import com.hungry.consultorang.rest.engine.EngineService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableAsync
public class EngineScheduler {

    private EngineService engineService;

    public EngineScheduler(EngineService engineService) {
        this.engineService = engineService;
    }

    @Scheduled(cron = "0 0 0 1 * ?") //매달 1일 0시 0분에 실행
    public void changeEngineSolution() throws Exception{
        engineService.changeMonthlyEngineSolution();
    }
}
