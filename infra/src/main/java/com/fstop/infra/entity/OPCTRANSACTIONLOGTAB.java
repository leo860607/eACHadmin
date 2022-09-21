package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity (name = "tw.org.twntch.po.OPCTRANSACTIONLOGTAB")
@Table(name = "OPCTRANSACTIONLOGTAB")
public class OPCTRANSACTIONLOGTAB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1992228716151746583L;
	private OPCTRANSACTIONLOGTAB id;
	private String PK_STAN;
	private String PK_TXDATE;
	private String TXTIME;
	private String PCODE;
	private String RSPCODE;
	private String FEPPROCESSRESULT;
	private String CONCODE;
	private String CONTIME;
	private String FEPTRACENO;
	private String IDFIELD;
	private String DATAFIELD;
	private String INQSTATUS;
	private String WEBTRACENO;
	private String BANKID;
	//@Transient
	private String N_TRACENO;
	private String BANKNAME;
	private String FEP_ERR_DESC;
	private String RSP_ERR_DESC;
	private String PNAME;
	private String TXDATE;
	private String TXDT;
	private String BANK;
	private String RSPRESULTCODE;
	private String CHG_PCODE;
	
	
	private String ERROR1200;
	private String ERROR1210;
	private String ERROR1310;
}
