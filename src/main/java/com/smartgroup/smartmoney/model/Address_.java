package com.smartgroup.smartmoney.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ {

	public static volatile SingularAttribute<Address, String> number;
	public static volatile SingularAttribute<Address, String> road;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, String> neighbourhood;
	public static volatile SingularAttribute<Address, String> postCode;
	public static volatile SingularAttribute<Address, String> place;
	public static volatile SingularAttribute<Address, String> region;

	public static final String NUMBER = "number";
	public static final String ROAD = "road";
	public static final String CITY = "city";
	public static final String NEIGHBOURHOOD = "neighbourhood";
	public static final String POST_CODE = "postCode";
	public static final String PLACE = "place";
	public static final String REGION = "region";

}

