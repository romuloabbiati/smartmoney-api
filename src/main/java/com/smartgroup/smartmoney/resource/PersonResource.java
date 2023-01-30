package com.smartgroup.smartmoney.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.smartgroup.smartmoney.event.ResourceCreatedEvent;
import com.smartgroup.smartmoney.model.Person;
import com.smartgroup.smartmoney.repository.PersonRepository;
import com.smartgroup.smartmoney.service.PersonService;

@RestController
@RequestMapping(path = "/people")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		return personRepository.findById(id)
			.map(person -> ResponseEntity.ok(person))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Person> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person savedPerson = personRepository.save(person);
		
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedPerson.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
	}
	
	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable Long id) {
		personRepository.deleteById(id);
	}
	
	@PutMapping(path = "/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person) {
		Person savedPerson = personService.update(id, person);
		return ResponseEntity.ok(savedPerson);
	}
	
	@PutMapping(path = "/{id}/active")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void updateActiveAttribute(@PathVariable Long id, @RequestBody Boolean active) {
		personService.updateActiveAttribute(id, active);
	}
	
}
