package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Entity(name = "tw.org.twntch.po.BANK_GROUP_BUSINESS")
@Table(name = "BANK_GROUP_BUSINESS")
@Getter
@Setter
public class BankGroupBusiness implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3032369843542344140L;
	
	private BankGroupBusinessPk id ;
	private	String	CDATE	;
	private	String	UDATE	;
	@EmbeddedId

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
		BankGroupBusiness other = (BankGroupBusiness) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
