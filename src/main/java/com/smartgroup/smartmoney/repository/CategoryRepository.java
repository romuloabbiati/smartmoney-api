package com.smartgroup.smartmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartgroup.smartmoney.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
