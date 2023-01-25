package com.smartgroup.smartmoney.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
}
