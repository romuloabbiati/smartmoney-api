package com.smartgroup.smartmoney.resource;

import java.net.URI;
import java.util.List;

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

import com.smartgroup.smartmoney.model.Category;
import com.smartgroup.smartmoney.repository.CategoryRepository;

@RestController
@RequestMapping(path = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;
	
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
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
			.buildAndExpand(savedCategory.getId()).toUri();
		
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(savedCategory);
	}
	
}
