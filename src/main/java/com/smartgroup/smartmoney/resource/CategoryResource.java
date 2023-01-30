package com.smartgroup.smartmoney.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgroup.smartmoney.event.ResourceCreatedEvent;
import com.smartgroup.smartmoney.model.Category;
import com.smartgroup.smartmoney.repository.CategoryRepository;

@RestController
@RequestMapping(path = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
	
//	@GetMapping(path = "/{id}")
//	public ResponseEntity<Category> findById(@PathVariable Long id) {
//		Optional<Category> category = categoryRepository.findById(id);
//		
//		return category.isPresent() ? ResponseEntity.ok(category.get()) : ResponseEntity.notFound().build();
//	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Category> findById(@PathVariable Long id) {
		return categoryRepository.findById(id)
			.map(category -> ResponseEntity.ok(category))
			.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<Category> save(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category savedCategory = categoryRepository.save(category);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedCategory.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
	}
	
}
