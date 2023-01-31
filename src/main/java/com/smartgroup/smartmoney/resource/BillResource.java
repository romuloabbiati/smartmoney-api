package com.smartgroup.smartmoney.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.BillRepository;

@RestController
@RequestMapping(path = "/bills")
public class BillResource {

	@Autowired
	private BillRepository billRepository;
	
	@GetMapping
	public List<Bill> findAll() {
		return billRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Bill> findById(@PathVariable Long id) {
		Optional<Bill> bill = billRepository.findById(id);
		return bill.isPresent() ? ResponseEntity.ok(bill.get()) : ResponseEntity.notFound().build();
	}
	
}
