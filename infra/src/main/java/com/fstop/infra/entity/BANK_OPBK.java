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

@Entity (name = "BANKOPBK")
@Table(name = "MASTER_BANK_OPBK")
@Getter
@Setter
public class BANK_OPBK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946783387317874612L;
	@EmbeddedId
	private BANK_OPBK_PK id ;
	private String OPBK_ID;
	private String OP_NOTE;
	@Transient
	private String OPBK_NAME;
	@Transient
	private String BGBK_NAME;
	@Transient
	private String BGBK_ID;
	@Transient
	private String START_DATE;
	

	

	
	
	
}
