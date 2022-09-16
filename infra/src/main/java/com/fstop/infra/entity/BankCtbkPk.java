package com.fstop.infra.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankCtbkPk  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -202774167000794267L;
	private String BGBK_ID;
	private String START_DATE;
	
	public BankCtbkPk() {
	}
	
	public BankCtbkPk(String bGBK_ID, String sTART_DATE) {
		super();
		BGBK_ID = bGBK_ID;
		START_DATE = sTART_DATE;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((BGBK_ID == null) ? 0 : BGBK_ID.hashCode());
		result = prime * result
				+ ((START_DATE == null) ? 0 : START_DATE.hashCode());
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
		BankCtbkPk other = (BankCtbkPk) obj;
		if (BGBK_ID == null) {
			if (other.BGBK_ID != null)
				return false;
		} else if (!BGBK_ID.equals(other.BGBK_ID))
			return false;
		if (START_DATE == null) {
			if (other.START_DATE != null)
				return false;
		} else if (!START_DATE.equals(other.START_DATE))
			return false;
		return true;
	}
	
	
	
}
