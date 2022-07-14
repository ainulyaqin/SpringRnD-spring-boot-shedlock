package com.fis.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.fis.app.service.RetryMsgService;

@Configuration
@EnableScheduling
public class SchedulerConfig {

	@Autowired
	private RetryMsgService retryMsgService;
	
	
	@Scheduled(cron = "*/20 * * * * ?") //sv3
	public void retrySurveyDmlApplication() {
		retryMsgService.retry();
	}
}
