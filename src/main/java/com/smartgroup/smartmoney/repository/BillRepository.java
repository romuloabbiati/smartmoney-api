package com.smartgroup.smartmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgroup.smartmoney.model.Bill;
import com.smartgroup.smartmoney.repository.bill.BillRepositoryQuery;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long>, BillRepositoryQuery {

}
