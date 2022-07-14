package com.fis.app.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.app.entity.MsgLog;
import com.fis.app.entity.Person;

import lombok.extern.log4j.Log4j2;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Log4j2
@Service
public class RetryMsgService {
	
	@Autowired
	private MsgLogService msgLogService;
	
	@Autowired
	private PersonService personService;
	
	final Integer COUNT = 2;
	
	@SchedulerLock(name = "retry", lockAtMostFor = "PT5M")
	public void retry() {

		log.info("Execute retry ");
		
		/*
		 * RETRY ERROR AND COUNT < 2
		 */
		
		List<MsgLog> listMsgLog = msgLogService.getLogByStatusAndCount(MsgLogService.ERR, COUNT);
		
		for(MsgLog log : listMsgLog) {
			thread1(log);
		}
	}
	
	
	private void thread1(MsgLog log) {
		
		try {
			
			Person p = new ObjectMapper().readValue(log.getJson(), Person.class);
			
			personService.updateData(p);
			
			msgLogService.updateLog(log.getId(), MsgLogService.COMPLETED);
		} catch (Exception e) {
			
			try {
				msgLogService.updateLog(log.getId(),MsgLogService.ERR,e.getMessage(),log.getCount()+1);
			} catch (Exception e1) {
				
			}
		}
		
	}
	
}
