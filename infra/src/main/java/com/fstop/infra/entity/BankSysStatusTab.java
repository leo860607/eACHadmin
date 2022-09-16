package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "BANKSYSSTATUSTAB")
@Table(name = "MASTER_BANKSYSSTATUSTAB")
@Getter
@Setter
public class BankSysStatusTab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8235099370514183736L;
	@Id
	private String BGBK_ID;
	private String MBSYSSTATUS;
	private String XCHGNCD01;
	private String XCHGNCD02;
	private String XCHGNCD03;
	private String XCHGNCD04;
	private String UNSYNCCNT01;
	private String UNSYNCCNT02;
	private String UNSYNCCNT03;
	private String UNSYNCCNT04;



	
}
