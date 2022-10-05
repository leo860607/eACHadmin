package com.fstop.infra.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name="BILL_TYPE")
@Table(name="MASTER_BILL_TYPE")
@Getter
@Setter
public class BILL_TYPE {

	private static final long serialVersionUID = 1L;
	private	String 	BILL_TYPE_ID	;
	private	String 	BILL_TYPE_NAME	;
}
