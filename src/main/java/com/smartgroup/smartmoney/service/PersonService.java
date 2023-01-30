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
		Person savedPerson = findPersonById(id);
		BeanUtils.copyProperties(person, savedPerson, "id");
		return savedPerson = personRepository.save(savedPerson);
	}
	
	@Transactional
	public void updateActiveAttribute(Long id, Boolean active) {
		Person savedPerson = findPersonById(id);
		savedPerson.setActive(active);
		personRepository.save(savedPerson);
	}
	
	private Person findPersonById(Long id) {
		return personRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
}
