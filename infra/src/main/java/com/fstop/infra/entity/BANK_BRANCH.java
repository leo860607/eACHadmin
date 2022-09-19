package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity (name = "BANKBRANCH")
@Table(name = "MASTER_BANK_BRANCH")
@Getter
@Setter
public class BANKBRANCH implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4585597083104668065L;
	@EmbeddedId
	private BANKBRANCHPK id ;
	private	String	BRBK_NAME	;
	private	String	TCH_ID	;
	private	String	ACTIVE_DATE	;
	private	String	STOP_DATE	;
	private	String	SYNCSPDATE	;//分行的停用日期是否與總行的停用日期同步
	private	String	CDATE	;
	private	String	UDATE	;
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BANKBRANCH other = (BANKBRANCH) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
