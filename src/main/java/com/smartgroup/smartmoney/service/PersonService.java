package com.smartgroup.smartmoney.service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.smartgroup.smartmoney.model.Person;
import com.smartgroup.smartmoney.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public Person update(Long id, Person person) {
		Person savedPerson = personRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(person, savedPerson, "id");
		return savedPerson = personRepository.save(savedPerson);
	}
	
}
