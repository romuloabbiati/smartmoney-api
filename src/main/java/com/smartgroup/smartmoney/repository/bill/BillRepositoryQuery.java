package com.smartgroup.smartmoney.repository.bill;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.filter.BillFilter;

public interface BillRepositoryQuery {
	
	Page<Bill> filter(BillFilter billFilter, Pageable pageable);

}
