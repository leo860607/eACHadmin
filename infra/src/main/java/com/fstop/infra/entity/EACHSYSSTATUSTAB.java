package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
@Entity(name="EACHSYSSTATUSTAB")
@Table(name="MASTER_EACHSYSSTATUSTAB")
@Getter
@Setter
public class EACHSYSSTATUSTAB implements Serializable {

	//eACH系統狀態檔
	private static final long serialVersionUID = -1529163750111026803L;

	@Id
	private	String 	SYSSTATUS;
	private	String 	PREVDATE;
	private String  THISDATE;
	private String  NEXTDATE;
	private String  DATEMODE;
	private String  CLEARINGPHASE;
	
	@Transient
	private String BUSINESS_DATE;
		
	
	
}
