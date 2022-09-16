package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity (name = "BANKCTBK")
@Table(name = "MASTER_BANK_CTBK")
@Setter
@Getter
public class BankCtbk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9122816418378095677L;
	@EmbeddedId
	private BankCtbkPk id ;
	private String CTBK_ID;
	private String CT_NOTE;
	
	
	@Transient
	private String CTBK_NAME;
	@Transient
	private String BGBK_NAME;
	@Transient
	private String BGBK_ID;
	@Transient
	private String START_DATE;
	
	
	
}
