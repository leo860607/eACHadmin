package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "EACHTXNCODE")
@Table(name="MASTER_EACH_TXN_CODE")
@Getter
@Setter
public class EACHTXNCODE implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4951358394339688787L;
	private String EACH_TXN_ID;
	private String EACH_TXN_NAME;
	private String BUSINESS_TYPE_ID;
	private String CDATE;
	private String UDATE;
	@Id

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((BUSINESS_TYPE_ID == null) ? 0 : BUSINESS_TYPE_ID.hashCode());
		result = prime * result + ((CDATE == null) ? 0 : CDATE.hashCode());
		result = prime * result
				+ ((EACH_TXN_ID == null) ? 0 : EACH_TXN_ID.hashCode());
		result = prime * result
				+ ((EACH_TXN_NAME == null) ? 0 : EACH_TXN_NAME.hashCode());
		result = prime * result + ((UDATE == null) ? 0 : UDATE.hashCode());
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
		EACHTXNCODE other = (EACHTXNCODE) obj;
		if (BUSINESS_TYPE_ID == null) {
			if (other.BUSINESS_TYPE_ID != null)
				return false;
		} else if (!BUSINESS_TYPE_ID.equals(other.BUSINESS_TYPE_ID))
			return false;
		if (CDATE == null) {
			if (other.CDATE != null)
				return false;
		} else if (!CDATE.equals(other.CDATE))
			return false;
		if (EACH_TXN_ID == null) {
			if (other.EACH_TXN_ID != null)
				return false;
		} else if (!EACH_TXN_ID.equals(other.EACH_TXN_ID))
			return false;
		if (EACH_TXN_NAME == null) {
			if (other.EACH_TXN_NAME != null)
				return false;
		} else if (!EACH_TXN_NAME.equals(other.EACH_TXN_NAME))
			return false;
		if (UDATE == null) {
			if (other.UDATE != null)
				return false;
		} else if (!UDATE.equals(other.UDATE))
			return false;
		return true;
	}
}
