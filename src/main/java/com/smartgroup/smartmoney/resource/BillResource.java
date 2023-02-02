package com.smartgroup.smartmoney.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartgroup.smartmoney.event.ResourceCreatedEvent;
import com.smartgroup.smartmoney.exceptionhandler.SmartMoneyExceptionHandler.Error;
import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.BillRepository;
import com.smartgroup.smartmoney.repository.filter.BillFilter;
import com.smartgroup.smartmoney.service.BillService;
import com.smartgroup.smartmoney.service.PersonDoesNotExistOrInactiveException;

@RestController
@RequestMapping(path = "/bills")
public class BillResource {

	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping
	public List<Bill> findAll(BillFilter billFilter) {
		return billRepository.filter(billFilter);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Bill> findById(@PathVariable Long id) {
		Optional<Bill> bill = billRepository.findById(id);
		return bill.isPresent() ? ResponseEntity.ok(bill.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Bill> save(@Valid @RequestBody Bill bill, HttpServletResponse response) {
		Bill savedBill = billService.save(bill);
		publisher.publishEvent(new ResourceCreatedEvent(this, response, savedBill.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBill);
	}
	
	@ExceptionHandler({ PersonDoesNotExistOrInactiveException.class })
	public ResponseEntity<Object> handlePersonDoesNotExistOrInactive(PersonDoesNotExistOrInactiveException ex) {
		String userMessage = messageSource.getMessage("person.does-not-exist-or-inactive", null, LocaleContextHolder.getLocale());
		String devMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, devMessage));
		return ResponseEntity.badRequest().body(errors);
	}
	
}
