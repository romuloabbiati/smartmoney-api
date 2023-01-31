package com.smartgroup.smartmoney.resource;

import java.util.List;
import java.util.Optional;

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
import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.BillRepository;

@RestController
@RequestMapping(path = "/bills")
public class BillResource {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Bill> findAll() {
		return billRepository.findAll();
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Bill> findById(@PathVariable Long id) {
		Optional<Bill> bill = billRepository.findById(id);
		return bill.isPresent() ? ResponseEntity.ok(bill.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Bill> save(@Valid @RequestBody Bill bill, HttpServletResponse response) {
		Bill savedBill = billRepository.save(bill);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedBill.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBill);
	}
	
}
