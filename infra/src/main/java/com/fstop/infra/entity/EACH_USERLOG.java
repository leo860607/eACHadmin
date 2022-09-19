package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

//<<<<<<< HEAD
@Entity (name = "EACH_USERLOG")
@Table(name = "MASTER_EACH_USERLOG")
@Getter
@Setter
public class EACH_USERLOG implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3291108105128791501L;
	private EACH_USERLOG_PK id;
	private String USERIP;
	private String TXTIME;
	private String UP_FUNC_ID;
	private String FUNC_ID;
	private String FUNC_TYPE;
	private String OPITEM;
	private String BFCHCON;
	private String AFCHCON;
	private String ADEXCODE;
	
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
		EACH_USERLOG other = (EACH_USERLOG) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}