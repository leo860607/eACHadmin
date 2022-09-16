package com.fstop.infra.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BANKBRANCHPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8605216767974289486L;
	
	public BANKBRANCHPK() {
	}
	public BANKBRANCHPK(String bGBK_ID, String bRBK_ID) {
		BGBK_ID = bGBK_ID;
		BRBK_ID = bRBK_ID;
	}
	private	String	BGBK_ID	;
	private	String	BRBK_ID ;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BGBK_ID == null) ? 0 : BGBK_ID.hashCode());
		result = prime * result + ((BRBK_ID == null) ? 0 : BRBK_ID.hashCode());
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
		BANKBRANCHPK other = (BANKBRANCHPK) obj;
		if (BGBK_ID == null) {
			if (other.BGBK_ID != null)
				return false;
		} else if (!BGBK_ID.equals(other.BGBK_ID))
			return false;
		if (BRBK_ID == null) {
			if (other.BRBK_ID != null)
				return false;
		} else if (!BRBK_ID.equals(other.BRBK_ID))
			return false;
		return true;
	}
	
	
	
}
