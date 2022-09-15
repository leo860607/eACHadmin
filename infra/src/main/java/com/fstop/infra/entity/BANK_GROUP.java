package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "BANKGROUP")
@Table(name = "MASTER_BANK_GROUP")
@Getter
@Setter
public class BANK_GROUP implements Serializable {
	private static final long serialVersionUID = 4098412301586046065L;

	@Transient
	private String OPBK_NAME;
	@Transient
	private String CTBK_NAME;
	@Transient
	private String OP_START_DATE;
	@Transient
	private String CT_START_DATE;
	@Transient
	private String OP_NOTE;
	@Transient
	private String CT_NOTE;
	@Id
	private String BGBK_ID;
	private String BGBK_NAME;
	private String BGBK_ATTR;
	private String CTBK_ACCT;
	private String TCH_ID;
	private String OPBK_ID;
	private String CTBK_ID;
	private String ACTIVE_DATE;
	private String STOP_DATE;
	private String SND_FEE_BRBK_ID;
	private String OUT_FEE_BRBK_ID;
	private String WO_FEE_BRBK_ID;
	private String IN_FEE_BRBK_ID;
	private String EDDA_FLAG;
	private String CDATE;
	private String UDATE;
	private String HR_UPLOAD_MAX_FILE;
	private String FILE_MAX_CNT;
	private String IS_EACH;

}
