package com.smartgroup.smartmoney.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.model.Person;
import com.smartgroup.smartmoney.repository.BillRepository;
import com.smartgroup.smartmoney.repository.PersonRepository;

@Service
public class BillService {
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private PersonRepository personRepository;

	@Transactional
	public Bill save(Bill bill) {
		Optional<Person> person = personRepository.findById(bill.getPerson().getId());
		if(!person.isPresent() || person.get().isInactive()) {
			throw new PersonDoesNotExistOrInactiveException();
		}
		return billRepository.save(bill);
	}
	
	public void delete(Long id) {
		billRepository.deleteById(id);
	}
	
}
