package com.fstop.infra.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name="TXN_ERROR_CODE")
@Table(name= "MASTER_TXN_ERROR_CODE")
@Getter
@Setter
public class TXN_ERROR_CODE {

	private static final long serialVersionUID = -8167157092248131187L;
	private String ERROR_ID ;
	private String ERR_DESC ;
	private String CDATE ;
	private String UDATE ;
	private String BATCH_ERR_ID;
}
