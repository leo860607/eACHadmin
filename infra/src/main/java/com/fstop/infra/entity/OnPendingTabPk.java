package com.fstop.infra.entity;

import java.io.Serializable;

public class OnPendingTabPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2718470974812994864L;
	private String OTXDATE;
	private String OSTAN;
	
	
	public OnPendingTabPk() {
	}
	public OnPendingTabPk(String tXDATE, String sTAN) {
		OTXDATE = tXDATE;
		OSTAN = sTAN;
	}
	public String getOTXDATE() {
		return OTXDATE;
	}
	public void setOTXDATE(String tXDATE) {
		OTXDATE = tXDATE;
	}
	public String getOSTAN() {
		return OSTAN;
	}
	public void setOSTAN(String sTAN) {
		OSTAN = sTAN;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((OSTAN == null) ? 0 : OSTAN.hashCode());
		result = prime * result + ((OTXDATE == null) ? 0 : OTXDATE.hashCode());
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
		OnPendingTabPk other = (OnPendingTabPk) obj;
		if (OSTAN == null) {
			if (other.OSTAN != null)
				return false;
		} else if (!OSTAN.equals(other.OSTAN))
			return false;
		if (OTXDATE == null) {
			if (other.OTXDATE != null)
				return false;
		} else if (!OTXDATE.equals(other.OTXDATE))
			return false;
		return true;
	}
	
	
	
}