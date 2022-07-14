package com.fis.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fis.app.entity.MsgLog;
import com.fis.app.entity.Person;
import com.fis.app.service.MsgLogService;
import com.fis.app.service.PersonService;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@RestController
public class ControllerApp {

	@Autowired
	private  PersonService personService;
	
	@Autowired
	private MsgLogService msgLogService;
	
	@PostMapping("/api/update-data")
	@Transactional
	@SchedulerLock(name = "personUpdate", lockAtMostFor = "PT15M")
	public Person updateData (@RequestBody Person p) throws Exception {
		
		ObjectMapper om = new ObjectMapper();
		
		/*
		 * 1st save ke msgLog as draft
		 */
		MsgLog log = new MsgLog();
		log.setJson(om.writeValueAsString(p));
		log.setStatus(MsgLogService.DRAFT);
		log.setErr(null);
		log.setCreatedDate(new Date());
		
		msgLogService.saveLog(log);
		
		/*
		 * 2nd transform ke transactional table
		 */
		Person p1 = personService.updateData(p);
		
		/*
		 * 3rd update completed
		 */
		msgLogService.updateLog(log.getId(), MsgLogService.COMPLETED);
		
		System.out.println("COMPLETED");
		
		
		return p1;
	}
	
	@GetMapping("/api/get-data")
	public Iterable<Person> getData () throws Exception {
		return personService.getData();
	}
}
