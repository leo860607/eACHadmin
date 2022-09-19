package com.fstop.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity(name="TXN_CODE")
@Table(name="MASTER_TXNCODE")
@Getter
@Setter
public class TXN_CODE implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1850590745698058824L;
	@Id
	@OrderBy("TXN_ID ASC")
	private	String	TXN_ID	;
	private	String	TXN_NAME	;
	private	String	TXN_TYPE	;
	private String 	TXN_CHECK_TYPE;
	private	String	TXN_DESC	;
	private	String	ACTIVE_DATE	;
	private	BigDecimal	MAX_TXN_AMT	;
	private	String	BUSINESS_TYPE_ID	;
	private	String	CDATE	;
	private	String	UDATE	;
	private	String	AGENT_TXN_ID	;
	@Transient
	private String	FEE_ID	;
	@Transient
	private String	START_DATE	;
	
	
}
