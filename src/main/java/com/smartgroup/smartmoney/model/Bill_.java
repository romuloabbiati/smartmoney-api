package com.smartgroup.smartmoney.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bill.class)
public abstract class Bill_ {

	public static volatile SingularAttribute<Bill, String> notes;
	public static volatile SingularAttribute<Bill, Person> person;
	public static volatile SingularAttribute<Bill, LocalDate> dueDate;
	public static volatile SingularAttribute<Bill, String> description;
	public static volatile SingularAttribute<Bill, Long> id;
	public static volatile SingularAttribute<Bill, LocalDate> paymentDate;
	public static volatile SingularAttribute<Bill, BillType> type;
	public static volatile SingularAttribute<Bill, Category> category;
	public static volatile SingularAttribute<Bill, BigDecimal> value;

	public static final String NOTES = "notes";
	public static final String PERSON = "person";
	public static final String DUE_DATE = "dueDate";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String PAYMENT_DATE = "paymentDate";
	public static final String TYPE = "type";
	public static final String CATEGORY = "category";
	public static final String VALUE = "value";

}

