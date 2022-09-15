package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
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
public class BANKGROUP implements Serializable {
private static final long serialVersionUID = 4098412301586046065L;
	
	@Transient
	private	String	OPBK_NAME	;
	@Transient
	private	String	CTBK_NAME	;	
	@Transient
	private	String	OP_START_DATE	;
	@Transient
	private	String	CT_START_DATE	;	
	@Transient
	private	String	OP_NOTE	;
	@Transient
	private	String	CT_NOTE	;	
	@Id
	private	String	BGBK_ID	;
	private	String	BGBK_NAME	;
	private	String	BGBK_ATTR	;
	private	String	CTBK_ACCT	;
	private	String	TCH_ID	;
	private	String	OPBK_ID	;
	private	String	CTBK_ID	;
	private	String	ACTIVE_DATE	;
	private	String	STOP_DATE	;
	private	String	SND_FEE_BRBK_ID	;
	private	String	OUT_FEE_BRBK_ID	;
	private	String	WO_FEE_BRBK_ID	;
	private	String	IN_FEE_BRBK_ID	;
	private	String	EDDA_FLAG	;
	private	String	CDATE	;
	private	String	UDATE	;
	private	String	HR_UPLOAD_MAX_FILE	;
	private	String	FILE_MAX_CNT	;
	private	String	IS_EACH	;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BGBK_ID == null) ? 0 : BGBK_ID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BANKGROUP other = (BANKGROUP) obj;
		if (BGBK_ID == null) {
			if (other.BGBK_ID != null)
				return false;
		} else if (!BGBK_ID.equals(other.BGBK_ID))
			return false;
		return true;
	}
}
