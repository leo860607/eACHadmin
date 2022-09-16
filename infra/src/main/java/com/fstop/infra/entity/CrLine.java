package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "CRLINE")
@Table(name="MASTER_CRLINE")
@Getter
@Setter
public class CrLine implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7042121575474526593L;
	private	String 	BANK_ID	;
//	private	String 	USER_ID	;
	private	String BASIC_CR_LINE	;
	private	String REST_CR_LINE	;
	private	String  CDATE	;
	private	String  UDATE	;
	
	@Transient
	private String BANK_NAME ;
	
	@Id
	@OrderBy("BANK_ID ASC" )

	
	
	@Transient
	public String getBANK_NAME() {
		return BANK_NAME;
	}
	public void setBANK_NAME(String bANK_NAME) {
		BANK_NAME = bANK_NAME;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BANK_ID == null) ? 0 : BANK_ID.hashCode());
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
		CrLine other = (CrLine) obj;
		if (BANK_ID == null) {
			if (other.BANK_ID != null)
				return false;
		} else if (!BANK_ID.equals(other.BANK_ID))
			return false;
		return true;
	}

	

}
