package com.smartgroup.smartmoney.resource;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.smartgroup.smartmoney.model.Person;
import com.smartgroup.smartmoney.repository.PersonRepository;

@RestController
@RequestMapping(path = "/people")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		return personRepository.findById(id)
			.map(person -> ResponseEntity.ok(person))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Person> save(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person savedPerson = personRepository.save(person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedPerson.getId()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(savedPerson);
	}
	
}
