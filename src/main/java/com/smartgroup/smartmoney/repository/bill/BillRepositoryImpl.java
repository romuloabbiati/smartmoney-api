package com.smartgroup.smartmoney.repository.bill;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.ObjectUtils;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.model.Bill_;
import com.smartgroup.smartmoney.repository.filter.BillFilter;

public class BillRepositoryImpl implements BillRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Bill> filter(BillFilter billFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		 CriteriaQuery<Bill> criteria = builder.createQuery(Bill.class);
		 Root<Bill> root = criteria.from(Bill.class);
		
		 // Create restrictions
		 Predicate[] predicates = createRestrictions(billFilter, builder, root);
		 criteria.where(predicates);
		 
		 TypedQuery<Bill> query = manager.createQuery(criteria);
		return query.getResultList();
	}

	private Predicate[] createRestrictions(BillFilter billFilter, CriteriaBuilder builder, 
			Root<Bill> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!ObjectUtils.isEmpty(billFilter.getDescription())) {
			predicates.add(builder.like(
					builder.lower(root.get(Bill_.description)), "%" + billFilter.getDescription().toLowerCase() + "%"));
		}
		
		if(billFilter.getDueDateFrom() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get(Bill_.dueDate), billFilter.getDueDateFrom()));
		}
		
		if(billFilter.getDueDateTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get(Bill_.dueDate), billFilter.getDueDateTo()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
