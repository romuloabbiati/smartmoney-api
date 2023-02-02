package com.smartgroup.smartmoney.repository.bill;

import java.util.List;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.filter.BillFilter;

public interface BillRepositoryQuery {
	
	List<Bill> filter(BillFilter billFilter);

}
