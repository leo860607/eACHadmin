package com.fstop.eachadmin.batch;

import javax.transaction.Transactional;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(value = "Schedule.enabled")
@Transactional
@Service
@Slf4j
public class BatchSchedule {
//	@Scheduled(cron="#{@CronValue}")
//    public void scheduledCreateBookTime() {
//		
//        log.debug("scheduledCreateBookTime End");
//    }
//	
//	@Scheduled(cron="* * * * * *")
//    public void scheduledCreateBookTime2() {
//		
//        log.info("scheduledCreateBookTime End2");
//    }
}
