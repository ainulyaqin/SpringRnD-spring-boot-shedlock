package com.fis.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fis.app.entity.Person;
import com.fis.app.repository.PersonRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public Person updateData(Person p) throws Exception {
		
		log.info("updating data ... ");
		
		Thread.sleep(30000);
		
		personRepository.save(p);
		
		return p;
	}
	
	public Iterable<Person> getData () throws Exception {
		return personRepository.findAll();
	}
}
