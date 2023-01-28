package com.smartgroup.smartmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgroup.smartmoney.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
