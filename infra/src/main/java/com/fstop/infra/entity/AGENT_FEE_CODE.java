package com.fstop.infra.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table(name="AGENT_FEE_CODE")
@Entity(name="MASTER_AGENT_FEE_CODE")
public class AGENT_FEE_CODE {
	@EmbeddedId
	private AGENT_FEE_CODE_PK id;
	@Transient
	private String FEE_ID;
	@Transient
	private String COMPANY_ID;
	@Transient
	private String START_DATE;
	private BigDecimal FEE;
	private String FEE_DESC;
	private String ACTIVE_DESC;
	private String CDATE;
	private String UDATE;
	@Transient
	private String FEE_NAME;
	@Transient
	private String COMPANY_NAME;
	
	@Data
	@AllArgsConstructor
	public class AGENT_FEE_CODE_PK{
		private String FEE_ID;
		private String COMPANY_ID;
		private String START_DATE;
	}

}
